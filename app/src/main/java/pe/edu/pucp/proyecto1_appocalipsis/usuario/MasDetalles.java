package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class MasDetalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_detalles);
        final Intent intent = getIntent();
        final Dispositivo dispositivo = (Dispositivo) intent.getSerializableExtra("Dispositivo");

        //Obtener los elementos
        TextView tipo = findViewById(R.id.tipoDetalles);
        TextView marca = findViewById(R.id.marcaDetalles);
        TextView caracteristicas = findViewById(R.id.caracteristicasDetalles);
        TextView incluye = findViewById(R.id.incluyeDetalles);
        TextView stock = findViewById(R.id.stockDetalles);
        ImageView imagen = findViewById(R.id.imagenDetalles);
        Button reservar = findViewById(R.id.reservarDetalles);

        //Agregar el contenido a los elementos
        tipo.setText(dispositivo.getTipo());
        marca.setText(dispositivo.getMarca());
        stock.setText(String.valueOf(dispositivo.getStock()));
        //imagen.setImageURI((dispositivo.getFoto());
        String aux="";

        /*

        for (String i : dispositivo.getCaracteristicas())
        {
            aux+="- "+ i + "\n";
        }
        caracteristicas.setText(aux);
        aux="";
        for (String i : dispositivo.getIncluye())
        {
            aux+="- "+ i + "\n";
        }
        incluye.setText(aux);

         */

        //Funcionalidad reservar
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MasDetalles.this, ReservaDispositivos.class);
                intent1.putExtra("Disposituvo",dispositivo);
                int reqCode = 1;
                startActivityForResult(intent,reqCode);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== RESULT_OK)
        {
            Toast.makeText(getApplicationContext(),"Reserva exitosa", Toast.LENGTH_LONG);
        }
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