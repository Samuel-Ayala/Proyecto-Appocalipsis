package pe.edu.pucp.proyecto1_appocalipsis.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.General.LoginRegistroActivity;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.MenuPrincipalUsuario;

public class GestionarDispositivos extends AppCompatActivity {

    private RecyclerView recyclerViewDispo;
    private DispositivosAdapter dispositivosAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_dispositivos);

        ////////////////////////////// IR A AGREGAR DISPOSITIVO /////////////////////////////////////////

        Button buttonIrAAgregarDispositivos = (Button) findViewById(R.id.agregarDispositivo);
        buttonIrAAgregarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AgregarDispositivo.class);
                startActivity(i);
                finish();
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////// LISTAR DISPOSITIVOS ////////////////////////////////

        recyclerViewDispo = findViewById(R.id.recyclerDispositivosUsuarioTI);
        recyclerViewDispo.setLayoutManager(new LinearLayoutManager(this));

        dispositivosAdapter = new DispositivosAdapter(obtenerListaDispositivos(),GestionarDispositivos.this);
        recyclerViewDispo.setAdapter(dispositivosAdapter);
    }

    private List<Dispositivo> obtenerListaDispositivos() {

        final List<Dispositivo> listaDispos = new ArrayList<>();

        DatabaseReference deviceDatabase = FirebaseDatabase.getInstance().getReference();
        deviceDatabase.child("dispositivos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()){

                    Dispositivo d = new Dispositivo();

                    String caract = data.child("caracteristicas").getValue().toString();
                    String urlFoto = data.child("foto").getValue().toString();
                    String incluye = data.child("incluye").getValue().toString();
                    String marca = data.child("marca").getValue().toString();
                    String stock = data.child("stock").getValue().toString();
                    String tipo = data.child("tipo").getValue().toString();

                    d.setCaracteristicas(caract);
                    d.setImagen(urlFoto);
                    d.setIncluye(incluye);
                    d.setMarca(marca);
                    d.setStock(Integer.parseInt(stock));
                    d.setTipo(tipo);

                    listaDispos.add(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listaDispos;
    }
}