package pe.edu.pucp.proyecto1_appocalipsis.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

       ///////////////////////Obtencion de datos de Database y guardado de datos/////////////////////////////////

        Bundle parametros = this.getIntent().getExtras();
        String email = parametros.getString("email");
        String rol = parametros.getString(("rol"));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String uid = user.getUid();
            Log.d("uid",uid);
        }else {
            Log.d("Estado del usuario:","no logueado");

        }
        SharedPreferences.Editor pref = getSharedPreferences("Datos", Context.MODE_PRIVATE).edit();
        pref.putString("email",email);
        pref.putString("rol", rol);
        pref.apply();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        ///////////////////////////////////////// Cerrar Sesion //////////////////////////////////////////////
        Button logout;
        logout = (Button) findViewById(R.id.CerrarSesionMenu);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////////  Borrado de datos  ///////////////
                SharedPreferences.Editor pref = getSharedPreferences("Datos", Context.MODE_PRIVATE).edit();
                pref.clear();
                pref.apply();
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////

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