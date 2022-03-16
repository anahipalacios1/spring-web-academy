/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;
import utils.ConnectionManager;
import entities.Alumno;
import java.beans.Statement;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
//import javax.validation.OverridesAttribute.List;
/**
 *
 * @author developer
 */
public class AlumnoManager {
    //Constantes 
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO alumnos (nombre, apellido) VALUES (?,?)";
    private static final String SQL = "SELECT * FROM alumnos";
    private static final String SQL_DELETE = "DELETE FROM alumnos WHERE id=?";
    private static final String SQL_MODIFY = "UPDATE alumnos SET nombre=?, apellido=? WHERE id=?";

        //clase generica
        /*PreparedStatement nos permite definir una sentencia SQL base, que nos sirve para 
        modificar/insertar/buscar uno o varios registros con sólo 
        cambiar los valores de los parámetros que especifiquemos.*/
	public List<Alumno> getAll() {
        
        // manera en que se conecta la base de datos mediante una excepcion 
        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<Alumno> listaAlumnos = new ArrayList();
            //se crea el objeto tipo lista de alumnos
            /*statement.execute(sql)– Normalmente para DDL como CREATE o DROP
            - statement.executeQuery(sql)– Ejecute la consulta SELECT y devuelva unResultSet*/
            ResultSet resultSet = statement.executeQuery(SQL);
            //Una tabla de datos que representa un conjunto de resultados de una base de datos, que generalmente se genera al ejecutar una declaración que consulta la base de datos.
            while (resultSet.next()) { //mientras resulset sea siguiente se agrega el alumno a una lista
                Alumno alumno = new Alumno();
                alumno.setId(resultSet.getInt("id"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumno.setNombre(resultSet.getString("nombre"));
                
                listaAlumnos.add(alumno);
            }
            resultSet.close();
            return listaAlumnos;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar alumno
    public void add(String nombre, String apellido) {

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {
            
            AlumnoManager alumnoManager = new AlumnoManager(); 
            preparestatement.setString(1,nombre);
            preparestatement.setString(2, apellido);
            
            // statement.executeUpdate(sql)– NormalQL State: 08003mente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void delete(int id) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, id);
            // statement.executeUpdate(sql)– Normalmente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }
    
    public void modify(int idAlumno, String nombre, String apellido){
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){
            
            preparestatement.setString(1, nombre);
            preparestatement.setString(2, apellido);
            preparestatement.setInt(3, idAlumno); 
            
            preparestatement.executeUpdate(); 
            
            
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Alumno getByid(int id) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                if (resultSet.getInt("id")==id){
                    Alumno alumno = new Alumno();
                    alumno.setId(resultSet.getInt("id"));
                    alumno.setNombre(resultSet.getString("nombre"));
                    alumno.setApellido(resultSet.getString("apellido"));
                    resultSet.close();
                    return alumno; 
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null; 
    }
}

