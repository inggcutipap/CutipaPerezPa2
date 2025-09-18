package com.gocp.cutipaperezpa2.fragment.archivo;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gocp.cutipaperezpa2.R;
import com.gocp.cutipaperezpa2.activity.MenuPrincipalActivity;
import com.gocp.cutipaperezpa2.dao.CategoriaDAO;
import com.gocp.cutipaperezpa2.dao.ProductosDAO;
import com.gocp.cutipaperezpa2.database.BodegaContract;


import com.gocp.cutipaperezpa2.entidad.Categoria;
import com.gocp.cutipaperezpa2.entidad.Producto;
import com.gocp.cutipaperezpa2.entidad.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductosFragment extends Fragment {

    private EditText etId, etNombre, etCantidad, etPrecio;
    private Button btnGuardar, btnEditarActualizar, btnEliminar;
    private RecyclerView rvProductos;
    private Spinner spCategoria;

    private CategoriaDAO categoriasDAO;
    private List<Categoria> listaCategorias;

    private ProductoAdapter adapter;
    private List<Producto> listaProductos;

    private ProductosDAO productosDAO;

    public ProductosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vistas
        etId = view.findViewById(R.id.etId);
        spCategoria = view.findViewById(R.id.spCategoria);
        etNombre = view.findViewById(R.id.etNombre);
        etCantidad = view.findViewById(R.id.etCantidad);
        etPrecio = view.findViewById(R.id.etPrecio);

        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnEditarActualizar = view.findViewById(R.id.btnEditarActualizar);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        rvProductos = view.findViewById(R.id.rvProductos);

        // DAO
        productosDAO = new ProductosDAO(getContext());
        categoriasDAO = new CategoriaDAO(getContext());

        listaCategorias = categoriasDAO.listar();
        ArrayAdapter<Categoria> adapterCat = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                listaCategorias
        );
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCat);

        // Lista + Adapter
        listaProductos = new ArrayList<>();
        adapter = new ProductoAdapter(listaProductos, producto -> {
            // Selección de item
            etId.setText(String.valueOf(producto.get_id()));
            etId.setVisibility(View.VISIBLE);
            etNombre.setText(producto.get_nombre());
            etCantidad.setText(String.valueOf(producto.get_cantidad()));
            etPrecio.setText(String.valueOf(producto.get_precio()));

            // Mostrar categoría en el spinner
            if (spCategoria != null) {
                int categoriaId = producto.get_categoriaId();
                for (int i = 0; i < spCategoria.getCount(); i++) {
                    if (((Categoria) spCategoria.getItemAtPosition(i)).getId() == categoriaId) {
                        spCategoria.setSelection(i);
                        break;
                    }
                }
            }

            etNombre.setEnabled(false);
            etCantidad.setEnabled(false);
            etPrecio.setEnabled(false);
            spCategoria.setEnabled(false);

            btnGuardar.setText("Cancelar");   // cambiabomos el botón Guardar por  Cancelar
            btnEditarActualizar.setEnabled(true);
            btnEditarActualizar.setText("Editar");
            btnEliminar.setEnabled(true);
        });


        rvProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductos.setAdapter(adapter);




        btnGuardar.setOnClickListener(v -> {
            if (btnGuardar.getText().toString().equals("Cancelar")) {
                limpiarCampos();
            } else {
                guardarProducto();
            }
        });
        btnEditarActualizar.setOnClickListener(v -> {
            if (btnEditarActualizar.getText().toString().equals("Editar")) {

                spCategoria.setEnabled(true);
                etNombre.setEnabled(true);
                etCantidad.setEnabled(true);
                etPrecio.setEnabled(true);

                btnEditarActualizar.setText("Actualizar");
                btnGuardar.setText("Cancelar");
            } else {
                actualizarProducto(); // tu método update
            }
        });
        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Está seguro de que desea eliminar este producto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarProducto();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        cargarProductos();
    }

    private void guardarProducto() {
        Categoria categoriaSeleccionada = (Categoria) spCategoria.getSelectedItem();
        int categoriaId = categoriaSeleccionada.getId();
        
        String nombre = etNombre.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();

        if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double precio = Double.parseDouble(precioStr);

        long id = productosDAO.insertar(nombre, cantidad, precio,categoriaId);

        if (id > 0) {
            Toast.makeText(getContext(), "Producto guardado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();
        } else {
            Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarProducto() {
        if (etId.getText().toString().isEmpty()) return;

        int id = Integer.parseInt(etId.getText().toString());

        Categoria categoriaSeleccionada = (Categoria) spCategoria.getSelectedItem();
        int categoriaId = categoriaSeleccionada.getId();

        String nombre = etNombre.getText().toString().trim();
        int cantidad = Integer.parseInt(etCantidad.getText().toString().trim());
        double precio = Double.parseDouble(etPrecio.getText().toString().trim());

        int filas = productosDAO.actualizar(id, nombre, cantidad, precio,categoriaId);

        if (filas > 0) {
            Toast.makeText(getContext(), "Producto actualizado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();
        } else {
            Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarProducto() {
        if (etId.getText().toString().isEmpty()) return;

        int id = Integer.parseInt(etId.getText().toString());
        int filas = productosDAO.eliminar(id);

        if (filas > 0) {
            Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();
        } else {
            Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarProductos() {
        listaProductos.clear();
        Cursor cursor = productosDAO.listar();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BodegaContract.Producto.ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(BodegaContract.Producto.NOMBRE));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(BodegaContract.Producto.CANTIDAD));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(BodegaContract.Producto.PRECIO));
                int categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(BodegaContract.Producto.CATEGORIA_ID));
                String categoriaNombre = cursor.getString(cursor.getColumnIndexOrThrow("categoriaNombre"));

                listaProductos.add(new Producto(id, nombre, cantidad, precio,categoriaId,categoriaNombre));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    private void limpiarCampos() {
        etId.setText("");
        etId.setVisibility(View.GONE);
        etNombre.setText("");
        etCantidad.setText("");
        etPrecio.setText("");


        if (spCategoria != null) {
            spCategoria.setSelection(0);
        }

        btnGuardar.setText("Guardar");

        btnGuardar.setEnabled(true);
        btnEditarActualizar.setEnabled(false);
        btnEditarActualizar.setText("Editar");
        btnEliminar.setEnabled(false);

        etNombre.setEnabled(true);
        etCantidad.setEnabled(true);
        etPrecio.setEnabled(true);
        spCategoria.setEnabled(true);

        etNombre.requestFocus();

    }
}