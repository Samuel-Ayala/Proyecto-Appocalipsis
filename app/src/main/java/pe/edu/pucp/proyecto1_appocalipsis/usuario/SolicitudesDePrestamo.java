package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.ListarReservasAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Usuario;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.admin.SolicitudesReserva;

public class SolicitudesDePrestamo extends AppCompatActivity {

    //Reserva [] reservas;
    ArrayList<Reserva> reservas =new ArrayList<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Usuario sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_de_prestamo);

        //Get con las reservas en estado PROCESANDO del usuario en sesion

        //se obitene la sesion
        reference.child("usuarios").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sesion = dataSnapshot.getValue(Usuario.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        // Obtener la lista de reservas inicialmente
        DatabaseReference referenciaReservas = reference.child("reservas");
        referenciaReservas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Reserva reserva = ds.getValue(Reserva.class);

                    Log.d("RESERVAS","el valor de reserva.getUsuario() es : "+ reserva.getUsuario());
                    Log.d("RESERVAS","el valor de currentUser.getUid() es : "+ currentUser.getUid());
                    Log.d("RESERVAS","========================");
                    Log.d("RESERVAS","el valor de reserva.getEstado() es : "+ reserva.getEstado());
                    if(((reserva.getUsuario()).equals(currentUser.getUid()))&&((reserva.getEstado()).equals("Procesando"))){
                        reservas.add(reserva);
                    }
                }

                //Poner los dispositivos en el recycler view
                ListarReservasAdapter listarReservasAdapter = new ListarReservasAdapter(reservas, SolicitudesDePrestamo.this);
                listarEnRV(listarReservasAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /*

        //Se llenan las solicutudes en el recycler view
        ListarReservasAdapter listarReservasAdapter = new ListarReservasAdapter(reservas, getApplicationContext());
        RecyclerView rv = findViewById(R.id.rvSolicitudesPrestamos);
        rv.setAdapter(listarReservasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

         */
    }

    //Para listar en el RV
    public void listarEnRV(ListarReservasAdapter listarReservasAdapter)
    {
        RecyclerView rv = findViewById(R.id.rvSolicitudesPrestamos);
        rv.setAdapter(listarReservasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(SolicitudesDePrestamo.this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.usuario_app_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.listarDispositvosBar:
                intent = new Intent(getApplicationContext(),ListarDispositivos.class);
                startActivity(intent);
                return true;

            case R.id.historialReservasBar:
                intent = new Intent(getApplicationContext(),HistorialDePrestamo.class);
                startActivity(intent);
                return true;

            case R.id.solicitudesReservaBar:
                intent = new Intent(getApplicationContext(),SolicitudesDePrestamo.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}