package pe.edu.pucp.proyecto1_appocalipsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;

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

        //Funcionalidad reservar
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MasDetalles.this, ReservaDispositivos.class);
                intent1.putExtra("Disposituvo",dispositivo);
                startActivity(intent1);
            }
        });

    }
}