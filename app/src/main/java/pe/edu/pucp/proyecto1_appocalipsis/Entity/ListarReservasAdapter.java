package pe.edu.pucp.proyecto1_appocalipsis.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ListarReservasAdapter extends RecyclerView.Adapter<ListarReservasAdapter.ViewHolder>{

    private Reserva[] reservas;
    private Context context;

    public ListarReservasAdapter(Reserva[] reservas, Context context) {
        this.reservas = reservas;
        this.context = context;
    }

    @NonNull
    @Override
    public ListarReservasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reserva,parent,false);
        ListarReservasAdapter.ViewHolder viewHolder = new ListarReservasAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListarReservasAdapter.ViewHolder holder, int position) {
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
