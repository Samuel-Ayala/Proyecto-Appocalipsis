package pe.edu.pucp.proyecto1_appocalipsis.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;
import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.admin.EditarDispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.admin.GestionarDispositivos;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.ListarDispositivos;

public class DispositivosITAdapter extends RecyclerView.Adapter<DispositivosITAdapter.ViewHolder> {

    private final List<Dispositivo> listaDispositivos;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarca, txtTipo, txtCaracteristicas, txtIncluye, txtStock;
        Button editarDispositivo, eliminarDispositivo;
        ImageView imagenDispositivo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Se llena el View Holder
            imagenDispositivo = itemView.findViewById(R.id.imgDispositivo);
            txtMarca= itemView.findViewById(R.id.marcaEnLista);
            txtTipo = itemView.findViewById(R.id.tipoEnLista);
            txtCaracteristicas = itemView.findViewById(R.id.caracteristicaEnLista);
            txtIncluye = itemView.findViewById(R.id.incluyeEnLista);
            txtStock = itemView.findViewById(R.id.stockEnLista);
            editarDispositivo = itemView.findViewById(R.id.editarDispositivo);
            eliminarDispositivo = itemView.findViewById(R.id.eliminarDispositivo);
        }
    }

    public DispositivosITAdapter(List<Dispositivo> listaDispositivos, Context context) {
        this.listaDispositivos = listaDispositivos;
        //Log.d("INFO APP IT ADAPTER",listaDispositivos.get(0).getMarca());
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_dispositivo,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dispositivo dispositivo = listaDispositivos.get(position);

        holder.txtTipo.setText("Tipo: " + dispositivo.getTipo());
        holder.txtMarca.setText("Marca: " + dispositivo.getMarca());
        holder.txtCaracteristicas.setText("Caracteristicas: " + dispositivo.getCaracteristicas());
        holder.txtIncluye.setText("Incluye: " + dispositivo.getIncluye());
        holder.txtStock.setText("Stock: " + dispositivo.getStock());

        Glide.with(context).load(dispositivo.getImagen()).into(holder.imagenDispositivo);

        holder.editarDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarDispositivo.class);
                intent.putExtra("Dispositivo",dispositivo);
                context.startActivity(intent);
            }
        });

        holder.eliminarDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// ELIMINAMOS REFERENCIA DE REALTIME DATABASE
                DatabaseReference deviceDatabase = FirebaseDatabase.getInstance().getReference().child("dispositivos");
                String nombreCarpetaDispositivo = dispositivo.getFoto();
                Log.d("FOTOOOOOO",dispositivo.getFoto());
                deviceDatabase.child(nombreCarpetaDispositivo).removeValue();

                /// ELIMINAMOS REFERENCIA DE STORAGE
                //StorageReference stReference = FirebaseStorage.getInstance().getReference().child("fotos");
                //stReference.child(nombreCarpetaDispositivo).delete();
                Intent intent = new Intent(context, GestionarDispositivos.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDispositivos.size();
    }
}
