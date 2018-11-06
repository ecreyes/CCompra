package com.example.ecreyes.ccompra.Objetos;

public class Tienda {
    private int id;
    private boolean estado;
    private String nombre;
    private String descripcion;
    private String uri;
    private String email;
    private String categoria;
    private String ubicacion;
    private String alerta;

    public Tienda(){}

    public Tienda(int id, boolean estado, String nombre, String descripcion, String uri, String email,
                  String categoria, String ubicacion, String alerta){
        this.id = id;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.uri = uri;
        this.email = email;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.alerta = alerta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getEmail() {return email;}

    public  void setEmail(String email) {this.email = email;}

    public  void setCategoria(String categoria) {this.categoria = categoria;}

    public String getCategoria() {return  categoria;}

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }
}
