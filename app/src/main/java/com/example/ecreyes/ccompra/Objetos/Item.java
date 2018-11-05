package com.example.ecreyes.ccompra.Objetos;

public class Item {

    private int id_t;
    private String producto;
    private String uri_p;

    public Item(int id_t, String producto, String uri_p) {
        this.id_t = id_t;
        this.producto = producto;
        this.uri_p = uri_p;
    }
    public int getId_t() {
        return id_t;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUri_p() {
        return uri_p;
    }

    public void setUri_p(String uri_p) {
        this.uri_p = uri_p;
    }

}
