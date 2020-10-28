package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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