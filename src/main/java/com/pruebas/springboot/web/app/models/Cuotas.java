/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.models;

import java.sql.Timestamp;
import java.sql.Timestamp;

/**
 *
 * @author developer
 */
public class Cuotas {
    private static final long serialVersionUID = 1L;
    private int idcuota;
    private Timestamp fecha;
    private Timestamp fechavencimiento;
    private int idinscripcion;
    private int monto;
    private int Pagos; 

    public Cuotas(int idcuota, Timestamp fecha, Timestamp fechavencimiento, int idinscripcion, int monto, int Pagos) {
        this.idcuota = idcuota;
        this.fecha = fecha;
        this.fechavencimiento = fechavencimiento;
        this.idinscripcion = idinscripcion;
        this.monto = monto;
        this.Pagos = Pagos;
    }

    public Cuotas() {
        super();
    }
    
    public int getIdcuota() {
        return idcuota;
    }

    public void setIdcuota(int idcuota) {
        this.idcuota = idcuota;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Timestamp fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public int getIdinscripcion() {
        return idinscripcion;
    }

    public void setIdinscripcion(int idinscripcion) {
        this.idinscripcion = idinscripcion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getPagos() {
        return Pagos;
    }

    public void setPagos(int Pagos) {
        this.Pagos = Pagos;
    }

    @Override
    public String toString() {
        return "Cuotas{" + "idcuota=" + idcuota + ", fecha=" + fecha + ", fechavencimiento=" + fechavencimiento + ", idinscripcion=" + idinscripcion + ", monto=" + monto + ", Pagos=" + Pagos + '}';
    }
}