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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class EditarDispositivo extends AppCompatActivity {

    private Uri rutaDeArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dispositivo);

        final EditText marca, caracteristicas, incluye, stock;
        final Button actualizarDispositivo, cargarFoto, tomarFoto;
        ImageView imagenDispositivo;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference deviceDatabase = FirebaseDatabase.getInstance().getReference().child("dispositivos");
        final Intent intent = getIntent();
        final Dispositivo dispositivo = (Dispositivo) intent.getSerializableExtra("Dispositivo");


        actualizarDispositivo = (Button) findViewById(R.id.actualizarDispositivoAInventario);
        cargarFoto = (Button) findViewById(R.id.cargarFotoEdit);
        tomarFoto = (Button) findViewById(R.id.tomarFotoEdit);
        marca = (EditText) findViewById(R.id.marcaDispositivoEdit);
        caracteristicas = (EditText) findViewById(R.id.caracteristicasDispositivoEdit);
        incluye = (EditText) findViewById(R.id.incluyeDispositivoEdit);
        stock = (EditText) findViewById(R.id.stockDispositivoEdit);
        imagenDispositivo = (ImageView) findViewById(R.id.imagenDeDispositivoAAgregar);

        /////// LLENADO DE DATOS A LA VISTA DE ACTUALIZAR //////////////

        marca.setText(dispositivo.getMarca());
        stock.setText(String.valueOf(dispositivo.getStock()));
        caracteristicas.setText(dispositivo.getCaracteristicas());
        incluye.setText(dispositivo.getIncluye());
        Glide.with(this).load(dispositivo.getFoto()).into(imagenDispositivo);

        //////////////////////////////////////////////////////////////////////////////

        actualizarDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombreCarpetaDispositivo = dispositivo.getTipo() + "-" + marca.getText().toString() + "-" + caracteristicas.getText().toString() + "-" + stock.getText().toString();

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
                            final DatabaseReference currentUserDB = deviceDatabase.child(nombreCarpetaDispositivo);
                            currentUserDB.child("marca").setValue(marca.getText().toString());
                            currentUserDB.child("caracteristicas").setValue(caracteristicas.getText().toString());
                            currentUserDB.child("incluye").setValue(incluye.getText().toString());
                            currentUserDB.child("stock").setValue(stock.getText().toString());
                            currentUserDB.child("foto").setValue(downloadLink.toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Toast.makeText(getApplicationContext(), "Dispositivo actualizado exitosamente", Toast.LENGTH_SHORT).show();
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
                if (EditarDispositivo.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    int permiso = ContextCompat.checkSelfPermission(EditarDispositivo.this, Manifest.permission.CAMERA);
                    int permiso2 = ContextCompat.checkSelfPermission(EditarDispositivo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int permiso3 = ContextCompat.checkSelfPermission(EditarDispositivo.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (permiso == PackageManager.PERMISSION_GRANTED) {
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(EditarDispositivo.this, new String[]{Manifest.permission.CAMERA},3);
                    }

                    if (permiso2 == PackageManager.PERMISSION_GRANTED) {
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(EditarDispositivo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
                    }

                    if (permiso3 == PackageManager.PERMISSION_GRANTED) {
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(EditarDispositivo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 2 && data != null && data.getData() != null){
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                ImageView imagenDispositivo = (ImageView) findViewById(R.id.imagenDeDispositivoAAgregar);
                imagenDispositivo.setImageBitmap(imageBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                assert imageBitmap != null;
                imageBitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
                byte[] imbytes = bos.toByteArray();
                imagenDispositivo.setVisibility(View.VISIBLE);

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error: debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}