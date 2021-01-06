package pe.edu.pucp.proyecto1_appocalipsis.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Adapters.DispositivosITAdapter;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class GestionarDispositivos extends AppCompatActivity {

    private DispositivosITAdapter dispositivosITAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_dispositivos);

        listarDispositivos();
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
    }

    private void listarDispositivos() {

        final List<Dispositivo> listaDispos = new ArrayList<>();

        DatabaseReference deviceDatabase = FirebaseDatabase.getInstance().getReference();
        deviceDatabase.child("dispositivos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        Dispositivo d = data.getValue(Dispositivo.class);
                        listaDispos.add(d);
                    }
                    dispositivosITAdapter = new DispositivosITAdapter(listaDispos,GestionarDispositivos.this);
                    listarEnRV(dispositivosITAdapter);
                }else {
                    Toast.makeText(getApplicationContext(), "No hay dispositivos en el inventario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarEnRV(DispositivosITAdapter dispositivosITAdapter) {
        RecyclerView rv = findViewById(R.id.recyclerDispositivosUsuarioTI);
        rv.setAdapter(dispositivosITAdapter);
        rv.setLayoutManager(new LinearLayoutManager(GestionarDispositivos.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            listarDispositivos();
        }
    }
}