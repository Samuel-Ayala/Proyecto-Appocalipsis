package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Usuario;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ReservaDispositivos extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Usuario sesion;
    ArrayList<Double> ubicacion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_dispositivos);


        //se obitene la sesion
        reference.child("usuarios").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sesion = dataSnapshot.getValue(Usuario.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        //Se obtiene el dispositivo
        Intent intent = getIntent();
        final Dispositivo dispositivo =(Dispositivo) intent.getSerializableExtra("Dispositivo");
        Button reservar = findViewById(R.id.reservarReserva);

        //Obtencion de ubicacion
        final Button obtenerUbicacion = findViewById(R.id.configurarUbicacion);

        obtenerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerInfoDeUbicacion();
            }
        });

        //Se gestiona la reserva
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView motivo = findViewById(R.id.motivoReserva);
                TextView direccion = findViewById(R.id.direccionReserva);
                CheckBox correo  = findViewById(R.id.emailReserva);
                boolean error = false;


                if (motivo.getText().toString().equalsIgnoreCase(""))
                {
                    motivo.setError("Se debe indicar un motivo");
                    error = true;
                }
                if (direccion.getText().toString().equalsIgnoreCase(""))
                {
                    direccion.setError("Se debe indicar su direccion");
                    error = true;
                }
                if(ubicacion==null)
                {
                    obtenerUbicacion.setError("Se debe obtener la ubicacion para proceder con la reserva");
                    error=true;
                }

                if(!error)
                {
                    Reserva reserva = new Reserva();
                    reserva.setDireccion(direccion.getText().toString());
                    reserva.setDispositivo(dispositivo);
                    reserva.setEnviarCorreo(correo.isChecked());
                    reserva.setMotivo(motivo.getText().toString());
                    reserva.setEstado("Procesando");
                    //Agregar la sesion
                    reserva.setUsuario(sesion);
                    //añadir la ubicacion
                    reserva.setUbicacion(ubicacion);
                    //Hacer el post
                    reference.child("reservas").push().setValue(reserva);
                    //regresarlo a la lista
                    setResult(RESULT_OK);
                    
                    finish();
                }
            }
        });
    }

    public void obtenerInfoDeUbicacion() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient locationProviderClient =
                    LocationServices.getFusedLocationProviderClient(getApplicationContext());
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    ubicacion = new ArrayList<>();
                    ubicacion.add(location.getLatitude());
                    ubicacion.add(location.getAltitude());
                    ubicacion.add(location.getLongitude());
                    Button boton = findViewById(R.id.configurarUbicacion);
                    boton.setText(R.string.actualizar_ubicacion);
                    Toast.makeText(getApplicationContext(),"se ha obtenido una nueva ubicacion",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("porque?", "onFailure: " + e.getMessage());
                }
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerInfoDeUbicacion();
            } else {
                Toast.makeText(getApplicationContext(),"no se tienen permisos",Toast.LENGTH_SHORT).show();
            }
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