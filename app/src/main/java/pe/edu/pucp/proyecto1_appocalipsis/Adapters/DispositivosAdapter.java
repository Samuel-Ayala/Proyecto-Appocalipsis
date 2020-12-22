package pe.edu.pucp.proyecto1_appocalipsis.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Dispositivo;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.MasDetalles;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class DispositivosAdapter extends RecyclerView.Adapter<DispositivosAdapter.ViewHolder> {

    private Dispositivo[] dispositivos;
    private Context context;
    private String filtro = null;
    private String tipoFiltro = null;

    public DispositivosAdapter(Dispositivo[] dispositivos, Context context) {
        this.dispositivos = dispositivos;
        this.context = context;
    }
    public DispositivosAdapter(Dispositivo[] dispositivos, Context context,String filtro,String tipo) {
        this.dispositivos = dispositivos;
        this.context = context;
        this.filtro=filtro;
        this.tipoFiltro =tipo;
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
        holder.dispositivo=dispositivo;
        holder.filtro=filtro;
        holder.tipo=tipoFiltro;
        holder.contexto=context;

    }

    @Override
    public int getItemCount() {
        return dispositivos.length;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        Dispositivo dispositivo;
        Context contexto;
        String filtro;
        String tipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //El filtrado
            if(tipo.equalsIgnoreCase("m"))
            {
                if(!filtro.contains(dispositivo.getMarca())) {
                    itemView.setVisibility(View.GONE);
                }
            }
            if(tipo.equalsIgnoreCase("t"))
            {
                if(!filtro.contains(dispositivo.getTipo())) {
                    itemView.setVisibility(View.GONE);
                }
            }

            //Se llena el View Holder
            TextView txtMarca= itemView.findViewById(R.id.marcaDispositivo);
            TextView txtTipo = itemView.findViewById(R.id.tipoDispositivo);
            txtMarca.setText(dispositivo.getMarca());
            txtTipo.setText(dispositivo.getTipo());
            //Llenar la imagen

            Button detalles = itemView.findViewById(R.id.detalles);
            detalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contexto, MasDetalles.class);
                    intent.putExtra("Dispositivo",dispositivo);
                    contexto.startActivity(intent);
                }
            });

        }
    }


}
