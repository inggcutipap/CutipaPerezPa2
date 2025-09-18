package com.gocp.cutipaperezpa2.entidad;

public class Producto {
    private int _id;
    private String _nombre;
    private int _cantidad;
    private double _precio;
    private int _categoriaId;
    private String _categoriaNombre;

    public Producto(int _id, String _nombre, int _cantidad, double _precio, int _categoriaId, String _categoriaNombre) {

        this._id = _id;
        this._nombre = _nombre;
        this._cantidad = _cantidad;
        this._precio = _precio;
        this._categoriaId = _categoriaId;
        this._categoriaNombre = _categoriaNombre;
    }

    public int get_id() {
        return _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public int get_cantidad() {
        return _cantidad;
    }

    public double get_precio() {
        return _precio;
    }

    public int get_categoriaId() {
        return _categoriaId;
    }

    public String get_categoriaNombre() {
        return _categoriaNombre;
    }
}
