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
import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.ListarDispositivos;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.MasDetalles;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class DispositivosAdapter extends RecyclerView.Adapter<DispositivosAdapter.ViewHolder> {

    private final List<Dispositivo> listaDispositivos;
    private Context context;
    private ArrayList<StorageReference> imgRef;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarca, txtTipo, txtCaracteristicas, txtIncluye, txtStock;
        Button detalles;
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
        }
    }

    /*public List<Dispositivo> listaDispositivos;

    public DispositivosAdapter(List<Dispositivo> listaDispositivos){
        this.listaDispositivos = listaDispositivos;
    }

     */

    public DispositivosAdapter(List<Dispositivo> listaDispositivos, Context context) {
        this.listaDispositivos = listaDispositivos;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dispositivo,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dispositivo dispositivo = listaDispositivos.get(position);

        holder.txtTipo.setText(dispositivo.getTipo());
        holder.txtMarca.setText(dispositivo.getMarca());
        holder.txtCaracteristicas.setText(dispositivo.getCaracteristicas());
        holder.txtIncluye.setText(dispositivo.getIncluye());
        holder.txtStock.setText(dispositivo.getStock());

        Glide.with(context).load(dispositivo.getImagen()).into(holder.imagenDispositivo);

        /*StorageReference imagen;
        //obtener la imagen
        for (StorageReference sr : imgRef)
        {
            if (sr.getName().equalsIgnoreCase(dispositivo.getTipo()))
            {
                imagen = sr;
                dispositivo.setImagen(imagen);
                //mostrar la imagen
                Glide.with(context).load(imagen).into(holder.imagenDispositivo);
                break;
            }
        }

         */

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
        return listaDispositivos.size();
    }



}
