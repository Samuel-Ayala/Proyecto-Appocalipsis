package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.ListarReservasAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.General.LoginRegistroActivity;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class HistorialDePrestamo extends AppCompatActivity {

    Reserva[] reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_de_prestamo);

        //Get con las reservas en estado aceptado o rechasado del usuario en sesion

        //Se llenan las solicutudes en el recycler view
        ListarReservasAdapter listarReservasAdapter = new ListarReservasAdapter(reservas,getApplicationContext());
        RecyclerView rv = findViewById(R.id.rvSolicitudesPrestamos);
        rv.setAdapter(listarReservasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                return true;

            case R.id.solicitudesReservaBar:
                intent = new Intent(getApplicationContext(),SolicitudesDePrestamo.class);
                startActivity(intent);
                return true;

            case R.id.cerrarSesionBar:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), LoginRegistroActivity.class);
                startActivity(intent2);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

