package pe.edu.pucp.proyecto1_appocalipsis.usuario;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosITAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosUserAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ListarDispositivos extends AppCompatActivity {

    ArrayList<Dispositivo> dispositivos=new ArrayList<>();
    private ArrayList<StorageReference> imgRefs=new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    StorageReference storage = FirebaseStorage.getInstance().getReference().child("imagenes");
    String filtroTipo;
    String filtroMarca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_dispositivos);

        // Obtener la lista de dispositivos inicialmente
        DatabaseReference referenciaDispositivos = reference.child("dispositivos");
        referenciaDispositivos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Dispositivo dispositivo = ds.getValue(Dispositivo.class);
                    if (dispositivo.getStock() > 0) {
                        dispositivos.add(dispositivo);
                        //Se hace referencia a las imagenes pormedio de la llave del dispositivo
                        imgRefs.add(storage.child(ds.getKey()));
                    }
                }

                //Poner los dispositivos en el recycler view
                DispositivosUserAdapter dispositivosUserAdapter = new DispositivosUserAdapter(dispositivos, ListarDispositivos.this);
                listarEnRV(dispositivosUserAdapter);

                //Se configura el filtro de marcas
                configurarFiltroMarcas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Activar el filtro de tipo
        ImageView lupa = findViewById(R.id.lupa);
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.filtroTipo);
                filtroTipo = tv.getText().toString();
                listarEnRV(filtrado());
            }
        });


    }

    public void configurarFiltroMarcas()
    {
        //Activar el filtro de marca
        ArrayList<String> marcas = new ArrayList<>(); //Se crea el arreglo de las posibles marcas
        marcas.add("Seleccione una marca del dispositivo"); //Situación inicial
        for (Dispositivo i : dispositivos) //Se recorre para var las marcas de todos los dispositivos
        {
            boolean repite = false;
            for (String j : marcas) //Con esto se evita la repeticion de marcas
            {
                if (i.getMarca().equals(j)) repite = true;
            }
            if (!repite) marcas.add(i.getMarca());  //Se asigna solo si es una nueva marca
        }

        //Se crea y agrega el adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(ListarDispositivos.this, android.R.layout.simple_spinner_dropdown_item, marcas);
        Spinner spinner = findViewById(R.id.filtroMarca);
        spinner.setAdapter(adapter);

        //se activa el listener de selección
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getSelectedItem();
                if (item != null) {
                    if (position==0) filtroMarca=null;
                    else filtroMarca = item.toString();
                    listarEnRV(filtrado());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //para realizar el filtrado
    public DispositivosUserAdapter filtrado()
    {
        ArrayList<Dispositivo> listaFiltrada = (ArrayList<Dispositivo>) dispositivos.clone();

        if(filtroMarca != null)
        {
            for (Dispositivo i : dispositivos) if (!i.getMarca().equalsIgnoreCase(filtroMarca)) listaFiltrada.remove(i);
        }

        if(filtroTipo != null)
        {
            for (Dispositivo i : dispositivos) if (!i.getTipo().contains(filtroTipo)) listaFiltrada.remove(i);
        }
        //caso no se encuentren resultados
        if (listaFiltrada.size()==0) findViewById(R.id.ceroResults).setVisibility(View.VISIBLE);
        else findViewById(R.id.ceroResults).setVisibility(View.GONE);

        DispositivosUserAdapter dispositivosUserAdapter = new DispositivosUserAdapter(listaFiltrada,ListarDispositivos.this);
        return dispositivosUserAdapter;
    }

    //Para listar en el RV
    public void listarEnRV(DispositivosUserAdapter dispositivosUserAdapter)
    {
        RecyclerView rv = findViewById(R.id.listaDispositivos);
        rv.setAdapter(dispositivosUserAdapter);
        rv.setLayoutManager(new LinearLayoutManager(ListarDispositivos.this));
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