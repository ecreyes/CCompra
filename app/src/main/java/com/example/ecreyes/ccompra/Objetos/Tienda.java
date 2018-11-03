package com.example.ecreyes.ccompra.Objetos;

public class Tienda {
    private int id;
    private boolean estado;
    private String nombre;
    private String descripcion;
    private String uri;
    private String email;

    public Tienda(){}

    public Tienda(int id,boolean estado,String nombre,String descripcion,String uri, String email){
        this.id = id;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.uri = uri;
        this.email = email;
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
}
