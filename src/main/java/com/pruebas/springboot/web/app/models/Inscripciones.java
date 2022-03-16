/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.models;

/**
 *
 * @author developer
 */
public class Inscripciones {
        private static final long serialVersionUID = 1L;
	private int idInscripcion; 
	private int idCursoHabilitado;
	private int idAlumno;
	
	public Inscripciones() {
		
	}

	public Inscripciones(int idInscripcion, int idCursoHabilitado, int idAlumno) {
		super();
		this.idInscripcion = idInscripcion;
		this.idCursoHabilitado = idCursoHabilitado;
		this.idAlumno = idAlumno;
	}

	public int getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(int idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public int getIdCursoHabilitado() {
		return idCursoHabilitado;
	}

	public void setIdCursoHabilitado(int idCursoHabilitado) {
		this.idCursoHabilitado = idCursoHabilitado;
	}

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Inscripcion [idInscripcion=" + idInscripcion + ", idCursoHabilitado=" + idCursoHabilitado
				+ ", idAlumno=" + idAlumno + "]\n";
	} 
}
