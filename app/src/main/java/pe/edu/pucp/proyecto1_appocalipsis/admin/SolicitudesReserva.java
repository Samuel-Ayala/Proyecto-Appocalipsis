package pe.edu.pucp.proyecto1_appocalipsis.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosUserAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Adapters.ListarReservasAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.ListarDispositivos;

public class SolicitudesReserva extends AppCompatActivity {

    ArrayList<Reserva> reservas =new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_reserva);


        // Obtener la lista de reservas inicialmente
        DatabaseReference referenciaReservas = reference.child("reservas");
        referenciaReservas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Dispositivo dispositivo = ds.getValue(Dispositivo.class);
                    Reserva reserva = ds.getValue(Reserva.class);
                        reservas.add(reserva);
                }

                //Poner los dispositivos en el recycler view
                ListarReservasAdapter listarReservasAdapter = new ListarReservasAdapter(reservas,SolicitudesReserva.this);
                listarEnRV(listarReservasAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Para listar en el RV
    public void listarEnRV(ListarReservasAdapter listarReservasAdapter)
    {
        RecyclerView rv = findViewById(R.id.listaDispositivos);
        rv.setAdapter(listarReservasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(SolicitudesReserva.this));
    }
}