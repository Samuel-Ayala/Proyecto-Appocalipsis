package pe.edu.pucp.proyecto1_appocalipsis.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class AgregarDispositivo extends AppCompatActivity {

    private Uri rutaDeArchivo;
    private byte[] imbytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_dispositivo);

        final EditText marca, caracteristicas, incluye, stock;
        final Button agregarDispositivo, cargarFoto, tomarFoto;
        final Spinner tipo;
        ImageView imagenDispositivo;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child("dispositivos");

        agregarDispositivo = (Button) findViewById(R.id.agregarDispositivoAInventario);
        cargarFoto = (Button) findViewById(R.id.cargarFoto);
        tomarFoto = (Button) findViewById(R.id.tomarFoto);
        marca = (EditText) findViewById(R.id.marcaDispositivo);
        tipo = (Spinner) findViewById(R.id.spinnerTipoDispositivo);
        caracteristicas = (EditText) findViewById(R.id.caracteristicasDispositivo);
        incluye = (EditText) findViewById(R.id.incluyeDispositivo);
        stock = (EditText) findViewById(R.id.stockDispositivo);
        imagenDispositivo = (ImageView) findViewById(R.id.imagenDeDispositivoAAgregar);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        final String dateString = sdf.format(date);

        agregarDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCarpetaDispositivo = tipo.getSelectedItem().toString() + "-" + marca.getText().toString() + "-" + caracteristicas.getText().toString() + "-" + stock.getText().toString();

                if (rutaDeArchivo != null){
                    StorageReference stReference = FirebaseStorage.getInstance().getReference();
                    final StorageReference fotoRef = stReference.child("fotos").child(nombreCarpetaDispositivo);
                    fotoRef.putFile(rutaDeArchivo).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw new Exception();
                            }
                            return fotoRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri downloadLink = task.getResult();
                            final DatabaseReference currentUserDB = userDatabase.child(nombreCarpetaDispositivo);

                            Dispositivo d = new Dispositivo();
                            d.setTipo(tipo.getSelectedItem().toString());
                            d.setStock(Integer.parseInt(stock.getText().toString()));
                            d.setMarca(marca.getText().toString());
                            d.setIncluye(incluye.getText().toString());
                            d.setImagen(downloadLink.toString());
                            d.setCaracteristicas(caracteristicas.getText().toString());
                            d.setFoto(tipo.getSelectedItem().toString() + "-" + marca.getText().toString() + "-" + caracteristicas.getText().toString() + "-" + stock.getText().toString());
                            currentUserDB.setValue(d);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Toast.makeText(getApplicationContext(), "Dispositivo agregado al inventario", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        cargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Seleccione una imagen"),1);
            }
        });

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AgregarDispositivo.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    int permiso = ContextCompat.checkSelfPermission(AgregarDispositivo.this, Manifest.permission.CAMERA);
                    if (permiso == PackageManager.PERMISSION_GRANTED) {
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(AgregarDispositivo.this, new String[]{Manifest.permission.CAMERA},3);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Error: este dispositivo no tiene camara", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tomarFoto() {
        Intent tomarFotex = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(tomarFotex,2);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Error: no es posible tomar una foto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null && data.getData() != null){
            rutaDeArchivo = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),rutaDeArchivo);
                ImageView imagenDispositivo = (ImageView) findViewById(R.id.imagenDeDispositivoAAgregar);
                imagenDispositivo.setImageBitmap(bitmap);
                imbytes=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 2){
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                ImageView imagenDispositivo = (ImageView) findViewById(R.id.imagenDeDispositivoAAgregar);
                imagenDispositivo.setImageBitmap(imageBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                assert imageBitmap != null;
                imageBitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
                imbytes = bos.toByteArray();
                rutaDeArchivo=null;
                imagenDispositivo.setVisibility(View.VISIBLE);

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error: debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}