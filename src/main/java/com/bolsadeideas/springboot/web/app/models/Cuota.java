package com.bolsadeideas.springboot.web.app.models;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class Cuota {

    private static final long serialVersionUID = 1L;
    @NotNull
    private int idcuota;
    @NotNull
    private Timestamp fecha;
    @NotNull
    private Timestamp fechavencimiento;
    @NotNull
    private int idinscripcion;
    @NotNull
    private int monto;
    @NotNull
    private int pagos;
    

    public Cuota() {
    }


	public Cuota(@NotNull int idcuota, @NotNull Timestamp fecha, @NotNull Timestamp fechavencimiento,
			@NotNull int idinscripcion, @NotNull int monto, @NotNull int pagos) {
		super();
		this.idcuota = idcuota;
		this.fecha = fecha;
		this.fechavencimiento = fechavencimiento;
		this.idinscripcion = idinscripcion;
		this.monto = monto;
		this.pagos = pagos;
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
		return pagos;
	}


	public void setPagos(int pagos) {
		this.pagos = pagos;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Cuota [idcuota=" + idcuota + ", fecha=" + fecha + ", fechavencimiento=" + fechavencimiento
				+ ", idinscripcion=" + idinscripcion + ", monto=" + monto + ", pagos=" + pagos + "]";
	}
}
