package pe.edu.pucp.proyecto1_appocalipsis.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Reserva;
import pe.edu.pucp.proyecto1_appocalipsis.R;

public class ListarReservasAdapterAdmin extends RecyclerView.Adapter<ListarReservasAdapterAdmin.ViewHolder>{

    private final List<Reserva> listaReservas;
    //private Reserva[] reservas;
    private Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public ListarReservasAdapterAdmin(List<Reserva> listaReservas, Context context) {
        this.listaReservas = listaReservas;
        //this.reservas = reservas;
        this.context = context;
    }

    @NonNull
    @Override
    public ListarReservasAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reserva_admin,parent,false);
        ListarReservasAdapterAdmin.ViewHolder viewHolder = new ListarReservasAdapterAdmin.ViewHolder(itemView);
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


        holder.aprobar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                reserva.setEstado("aceptado");
                int a = reserva.getDispositivo().getStock();
                reserva.getDispositivo().setStock(a-1);
                //Toast.makeText("Se acepto el dispositivo", "",Toast.LENGTH_SHORT).show();
                String newRef = reserva.getId();
                reference.child("reservas").child(newRef).setValue(reserva);
            }
        });

        holder.rechazar.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v) {
                    reserva.setEstado("rechazado");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Justificacion del rechazo");
                    builder.setMessage("Ingrese la justificacion") ;
                    final EditText justificacion = new EditText(context);
                    builder.setView(justificacion);
                    builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reserva.setJustificacion(justificacion.getText().toString());
                            String newRef = reserva.getId();
                            reference.child("reservas").child(newRef).setValue(reserva);
                        }
                    });
                    builder.show();

                }
        });

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

            aprobar = itemView.findViewById(R.id.aprobar);
            rechazar = itemView.findViewById(R.id.rechazar);

            txtDireccion= itemView.findViewById(R.id.direccionReserva);
            txtEstado = itemView.findViewById(R.id.estadoReserva);
            txtMotivo = itemView.findViewById(R.id.motivoReserva);
            txtTipo = itemView.findViewById(R.id.tipoReserva);
            txtMarca = itemView.findViewById(R.id.marcaReserva);


        }
    }
}
