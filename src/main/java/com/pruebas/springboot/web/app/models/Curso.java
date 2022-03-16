/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.models;

/**
 *
 * @author developer
 */
public class Curso {
    private int id;
    private String Nombre;

    public Curso(int id, String Nombre) {
        this.id = id;
        this.Nombre = Nombre;
    }
    
    public Curso(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", Nombre=" + Nombre + '}';
    }
}
