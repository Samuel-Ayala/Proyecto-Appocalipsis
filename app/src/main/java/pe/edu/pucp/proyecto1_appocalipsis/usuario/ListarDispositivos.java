package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ListarDispositivos extends AppCompatActivity {

    Dispositivo [] dispositivos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_dispositivos);

        // Obtener la lista de dispositivos

        //Poner los dispositivos en el recycler view
        DispositivosAdapter dispositivosAdapter = new DispositivosAdapter(dispositivos,ListarDispositivos.this);
        RecyclerView rv = findViewById(R.id.listaDispositivos);
        rv.setAdapter(dispositivosAdapter);
        rv.setLayoutManager(new LinearLayoutManager(ListarDispositivos.this));

        //Activar el filtro de tipo
        ImageView lupa = findViewById(R.id.lupa);
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.filtroTipo);
                String filtro = tv.getText().toString();
                DispositivosAdapter dispositivosAdapter = new DispositivosAdapter(dispositivos,ListarDispositivos.this,filtro,"t");
                RecyclerView rv = findViewById(R.id.listaDispositivos);
                rv.setAdapter(dispositivosAdapter);
                rv.setLayoutManager(new LinearLayoutManager(ListarDispositivos.this));
            }
        });


        //Activar el filtro de marca
        ArrayList<String> marcas=new ArrayList<>(); //Se crea el arreglo de las posibles marcas
        marcas.add("Seleccione una marca del dispositivo"); //Situación inicial
        for (Dispositivo i:dispositivos) //Se recorre para var las marcas de todos los dispositivos
        {
            boolean repite=false;
            for (String j : marcas) //Con esto se evita la repeticion de marcas
            {
                if(i.getMarca().equals(j)) repite=true;
            }
            if (!repite) marcas.add(i.getMarca());  //Se asigna solo si es una nueva marca
        }
            //String[] marcaArreglo = (String[]) marcas.toArray();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,marcas);
        Spinner spinner = findViewById(R.id.filtroMarca);
        spinner.setAdapter(adapter); //Se pone en la vista

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getSelectedItem();
                if(item != null) {
                    String marca = item.toString();
                    DispositivosAdapter dispositivosAdapter =
                            new DispositivosAdapter(dispositivos,ListarDispositivos.this,marca,"m");
                    RecyclerView rv = findViewById(R.id.listaDispositivos);
                    rv.setAdapter(dispositivosAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(ListarDispositivos.this));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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