
package com.pruebas.springboot.web.app.models;

import java.io.Serializable;


/**
 *
 * @author developer
 */
//Serializar la clase pq el proceso de convertir un objeto en una secuencia de bytes para almacenarlo o transmitirlo a la memoria
//ej o a una base de datos, o a un json
public class Alumno implements Serializable {
    private int id;
    private static final long serialVersionUID =1L;
    private String nombre;
    private String apellido;
    
    public Alumno(){
        super();
    }

    public Alumno(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Alumno{" + 
                        "id_alumno=" + id + 
                        ", nombre=" + nombre + 
                        ", apellido=" + apellido + 
                        '}';
    }
}
