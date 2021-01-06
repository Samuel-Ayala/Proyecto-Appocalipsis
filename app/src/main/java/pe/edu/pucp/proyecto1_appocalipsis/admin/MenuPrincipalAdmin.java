package pe.edu.pucp.proyecto1_appocalipsis.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pe.edu.pucp.proyecto1_appocalipsis.R;

public class MenuPrincipalAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_admin);

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

        ///////////////////////////////////////// Cerrar Sesion //////////////////////////////////////////////
        Button logout;
        logout = (Button) findViewById(R.id.cerrarSessionAdmin);
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

        ////////////////////////////// IR A GESTION DE DISPOSITIVOS /////////////////////////////////////////

        Button buttonGestionarDispositivos = (Button) findViewById(R.id.gestionar);
        buttonGestionarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GestionarDispositivos.class);
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}