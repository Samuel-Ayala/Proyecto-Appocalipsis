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

import pe.edu.pucp.proyecto1_appocalipsis.General.LoginRegistroActivity;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.SolicitudesDePrestamo;

public class MenuPrincipalAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_admin);

        ///////////////////////////////////////// Cerrar Sesion //////////////////////////////////////////////
        Button logout;
        logout = findViewById(R.id.cerrarSessionAdmin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });


        //////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////// IR A GESTION DE DISPOSITIVOS /////////////////////////////////////////

        Button buttonGestionarDispositivos = findViewById(R.id.gestionar);
        buttonGestionarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GestionarDispositivos.class);
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// IR A PEDIDOS DE RESERVA /////////////////////////////////////////

        Button pedidosReserva = (Button) findViewById(R.id.reserva);
        pedidosReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SolicitudesReserva.class);
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////


    }
    public void cerrarSesion()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent2 = new Intent(getApplicationContext(), LoginRegistroActivity.class);
        startActivity(intent2);
        finish();
    }
}