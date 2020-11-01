package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ReservaDispositivos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_dispositivos);

        //Se obtiene el dispositivo
        Intent intent = getIntent();
        final Dispositivo dispositivo =(Dispositivo) intent.getSerializableExtra("Dispositivo");
        Button reservar = findViewById(R.id.reservarReserva);

        //Se gestiona la reserva
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView motivo = findViewById(R.id.motivoReserva);
                TextView direccion = findViewById(R.id.direccionReserva);
                TextView ubicacion = findViewById(R.id.ubicacionReserva);
                CheckBox correo  = findViewById(R.id.emailReserva);
                Boolean error = false;

                if (motivo.getText()==null)
                {
                    motivo.setError("Se debe indicar un motivo");
                    error = true;
                }
                if (direccion.getText()==null)
                {
                    direccion.setError("Se debe indicar su direccion");
                    error = true;
                }
                if (ubicacion.getText()==null)
                {
                    ubicacion.setError("No se indico su ubicacion");
                    error = true;
                }

                if(!error)
                {
                    Reserva reserva = new Reserva();
                    reserva.setDireccion(direccion.getText().toString());
                    reserva.setDispositivo(dispositivo);
                    reserva.setEnviarCorreo(correo.isChecked());
                    reserva.setMotivo(motivo.getText().toString());
                    reserva.setUbicacion(ubicacion.getText().toString());
                    reserva.setEstado("Procesando");
                    //Agregar la sesion

                    //Hacer el post

                    //regresarlo a mas detalles
                    setResult(RESULT_OK);
                    finish();
                }

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