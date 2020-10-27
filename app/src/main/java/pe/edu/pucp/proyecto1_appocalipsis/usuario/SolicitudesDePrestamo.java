package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.ListarSolicitudesAdapter;
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
        ListarSolicitudesAdapter listarSolicitudesAdapter = new ListarSolicitudesAdapter(reservas,getApplicationContext());
        RecyclerView rv = findViewById(R.id.rvSolicitudesPrestamos);
        rv.setAdapter(listarSolicitudesAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));




    }
}