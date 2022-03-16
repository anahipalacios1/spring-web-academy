/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.models;

/**
 *
 * @author developer
 */
public class CursosHabilitados {
    private static final long serialVersionUID = 1L;
	private int idCursoHabilitado; 
	private int idCurso;
	private int idMateria;
	private int idProfesor;
	
	public CursosHabilitados() {
		super();
	}

	public CursosHabilitados(int idCursoHabilitado, int idCurso, int idMateria, int idProfesor) {
		super();
		this.idCursoHabilitado = idCursoHabilitado;
		this.idCurso = idCurso;
		this.idMateria = idMateria;
		this.idProfesor = idProfesor;
	}

	public int getIdCursoHabilitado() {
		return idCursoHabilitado;
	}

	public void setIdCursoHabilitado(int idCursoHabilitado) {
		this.idCursoHabilitado = idCursoHabilitado;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public int getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CursoHabilitados [idCursoHabilitado=" + idCursoHabilitado + ", idCurso=" + idCurso + ", idMateria="
				+ idMateria + ", idProfesor=" + idProfesor + "]\n";
	} 
	
	
}
