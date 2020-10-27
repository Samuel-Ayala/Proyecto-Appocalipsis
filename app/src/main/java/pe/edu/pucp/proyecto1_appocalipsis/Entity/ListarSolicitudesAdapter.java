package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ListarSolicitudesAdapter extends RecyclerView.Adapter<ListarSolicitudesAdapter.ViewHolder>{

    private Reserva[] reservas;
    private Context context;

    public ListarSolicitudesAdapter(Reserva[] reservas, Context context) {
        this.reservas = reservas;
        this.context = context;
    }

    @NonNull
    @Override
    public ListarSolicitudesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reserva,parent,false);
        ListarSolicitudesAdapter.ViewHolder viewHolder = new ListarSolicitudesAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListarSolicitudesAdapter.ViewHolder holder, int position) {
        holder.reserva=reservas[position];
        holder.context=context;

    }

    @Override
    public int getItemCount() {
        return reservas.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        Reserva reserva;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView tipo = itemView.findViewById(R.id.tipoReserva);
            TextView marca = itemView.findViewById(R.id.marcaReserva);
            TextView estado = itemView.findViewById(R.id.estadoReserva);

            tipo.setText(reserva.getDispositivo().getTipo());
            marca.setText(reserva.getDispositivo().getMarca());
            estado.setText(reserva.getEstado());

        }
    }
}
