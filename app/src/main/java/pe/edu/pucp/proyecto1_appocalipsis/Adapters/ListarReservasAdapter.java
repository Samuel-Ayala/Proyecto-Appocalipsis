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

import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.R;
import pe.edu.pucp.proyecto1_appocalipsis.usuario.MasDetalles;

public class ListarReservasAdapter extends RecyclerView.Adapter<ListarReservasAdapter.ViewHolder>{

    private final List<Reserva> listaReservas;
    private Reserva[] reservas;
    private Context context;


    public ListarReservasAdapter(List<Reserva> listaReservas, Context context) {
        this.listaReservas = listaReservas;
        //this.reservas = reservas;
        this.context = context;
    }

    @NonNull
    @Override
    public ListarReservasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reserva,parent,false);
        ListarReservasAdapter.ViewHolder viewHolder = new ListarReservasAdapter.ViewHolder(itemView);
        ViewHolder vHolder = new ViewHolder(itemView);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.reserva=reservas[position];
        //holder.context=context;

        final Reserva reserva = listaReservas.get(position);

        holder.txtEstado.setText(reserva.getEstado());
        holder.txtTipo.setText(reserva.getDispositivo().getTipo());
        holder.txtMarca.setText(reserva.getDispositivo().getMarca());

        //Aqui va la logica de los botones de aceptar o rechazar la reserva

        /*
        holder.aprobar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasDetalles.class);
                intent.putExtra("Dispositivo",dispositivo);
                context.startActivity(intent);
            }
        });

        holder.rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasDetalles.class);
                intent.putExtra("Dispositivo",dispositivo);
                context.startActivity(intent);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        //return reservas.length;
        return listaReservas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtDireccion, txtEstado, txtMotivo,txtTipo, txtMarca;

        Button aprobar, rechazar;

        Reserva reserva;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView tipo = itemView.findViewById(R.id.tipoReserva);
            TextView marca = itemView.findViewById(R.id.marcaReserva);
            TextView estado = itemView.findViewById(R.id.estadoReserva);


            txtDireccion= itemView.findViewById(R.id.direccionReserva);
            txtEstado = itemView.findViewById(R.id.estadoReserva);
            txtMotivo = itemView.findViewById(R.id.motivoReserva);
            txtTipo = itemView.findViewById(R.id.tipoReserva);
            txtMarca = itemView.findViewById(R.id.marcaReserva);


            //tipo.setText(reserva.getDispositivo().getTipo());
            //marca.setText(reserva.getDispositivo().getMarca());
            //estado.setText(reserva.getEstado());

        }
    }
}
