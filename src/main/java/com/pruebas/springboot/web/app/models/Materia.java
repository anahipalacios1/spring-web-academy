/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.models;

/**
 *
 * @author developer
 */
public class Materia {
    private int id;
    private String asignatura;

    public Materia(int id, String asignatura) {
        this.id = id;
        this.asignatura = asignatura;
    }
    
    public  Materia(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return asignatura;
    }

    public void setDescripcion(String asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", asignatura=" + asignatura + '}';
    }
}
