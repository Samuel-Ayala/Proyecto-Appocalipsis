package pe.edu.pucp.proyecto1_appocalipsis.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.ListarDispositivos;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.MasDetalles;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class DispositivosAdapter extends RecyclerView.Adapter<DispositivosAdapter.ViewHolder> {

    private ArrayList<Dispositivo> dispositivos;
    private Context context;
    private ArrayList<StorageReference> imgRef;


    public DispositivosAdapter(ArrayList<Dispositivo> dispositivos, Context context, ArrayList<StorageReference> imgRef) {
        this.dispositivos = dispositivos;
        this.context = context;
        this.imgRef = imgRef;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dispositivo,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dispositivo dispositivo = dispositivos.get(position);

        holder.txtTipo.setText(dispositivo.getTipo());
        holder.txtMarca.setText(dispositivo.getMarca());

        StorageReference imagen;
        //obtener la imagen
        for (StorageReference sr : imgRef)
        {
            if (sr.getName().equalsIgnoreCase(dispositivo.getId()))
            {
                imagen = sr;
                dispositivo.setImagen(imagen);
                //mostrar la imagen
                Glide.with(context).load(imagen).into(holder.imagen);
                break;
            }
        }

        holder.detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasDetalles.class);
                intent.putExtra("Dispositivo",dispositivo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarca;
        TextView txtTipo;
        Button detalles;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Se llena el View Holder
             txtMarca= itemView.findViewById(R.id.marcaDispositivo);
             txtTipo = itemView.findViewById(R.id.tipoDispositivo);
        }
    }


}
