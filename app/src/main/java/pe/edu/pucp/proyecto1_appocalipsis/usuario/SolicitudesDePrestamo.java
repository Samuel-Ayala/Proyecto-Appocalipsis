package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.ListarReservasAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class SolicitudesDePrestamo extends AppCompatActivity {

    Reserva [] reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_de_prestamo);

        //Get con las reservas en estado procesando del usuario en sesion

        //Se llenan las solicutudes en el recycler view
        ListarReservasAdapter listarReservasAdapter = new ListarReservasAdapter(reservas,getApplicationContext());
        RecyclerView rv = findViewById(R.id.rvSolicitudesPrestamos);
        rv.setAdapter(listarReservasAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));




    }
}