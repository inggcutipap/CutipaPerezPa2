package com.gocp.cutipaperezpa2.entidad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gocp.cutipaperezpa2.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> lista;
    private OnItemClickListener listener;

    // Interfaz para capturar clics
    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }

    public ProductoAdapter(List<Producto> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto p = lista.get(position);
        holder.tvNombre.setText(p.get_nombre());
        holder.tvCantidad.setText("Cantidad: " + p.get_cantidad());
        holder.tvPrecio.setText("Precio: " + p.get_precio());
        holder.tvCategoria.setText("Categoría : " + p.get_categoriaNombre());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCantidad, tvPrecio, tvCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCategoria= itemView.findViewById(R.id.tvCategoria);
        }
    }
}

