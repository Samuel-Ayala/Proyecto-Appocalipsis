package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.edu.pucp.proyecto1_appocalipsis.R;

public class MenuPrincipalUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_usuario);

        Button listarDispositivos = findViewById(R.id.listaDispositivosMenu);
        Button solicitudesReserva = findViewById(R.id.solicitudesDePrestamoMenu);
        Button historialReserva = findViewById(R.id.historialDePrestamoMenu);
        Button cerrarSesion = findViewById(R.id.CerrarSesionMenu);

        listarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListarDispositivos.class);
                startActivity(intent);
            }
        });

        historialReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HistorialDePrestamo.class);
                startActivity(intent);
            }
        });

        solicitudesReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SolicitudesDePrestamo.class);
                startActivity(intent);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer lo que se tenga que hacer para cerrar la sesion
            }
        });

    }
}