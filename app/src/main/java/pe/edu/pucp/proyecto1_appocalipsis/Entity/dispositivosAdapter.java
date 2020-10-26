package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.edu.pucp.proyecto1_appocalipsis.R;

public class dispositivosAdapter extends RecyclerView.Adapter<dispositivosAdapter.ViewHolder> {

    private Dispositivo[] dispositivos;
    private Context context;

    public dispositivosAdapter(Dispositivo[] dispositivos, Context context) {
        this.dispositivos = dispositivos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dispositivo,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dispositivo dispositivo = dispositivos[position];
        holder.txtTipo.setText(dispositivo.getTipo());
        holder.txtMarca.setText(dispositivo.getMarca());
        //holder.imagen.setImageResource(dispositivo.getFoto()); //Ver lo de la imagen

    }

    @Override
    public int getItemCount() {
        return dispositivos.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTipo;
        TextView txtMarca;
        ImageView imagen;
        Button detalles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
