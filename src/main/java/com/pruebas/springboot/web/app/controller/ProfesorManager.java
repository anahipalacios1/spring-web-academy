/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;

import utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import entities.Profesor;

/**
 *
 * @author developer
 */
public class ProfesorManager {
    //Constantes
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO profesor (nombre, apellido) VALUES (?,?)";
    private static final String SQL = "SELECT * FROM profesor";
    private static final String SQL_DELETE = "DELETE FROM profesor WHERE id=?";
    private static final String SQL_MODIFY = "UPDATE profesor SET nombre=?, apellido=? WHERE id=?";

	public List<Profesor> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<Profesor> listaProfesor = new ArrayList();
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                Profesor profesor = new Profesor();
                profesor.setId(resultSet.getInt("id"));
                profesor.setApellido(resultSet.getString("apellido"));
                profesor.setNombre(resultSet.getString("nombre"));
                
                listaProfesor.add(profesor);
            }
            resultSet.close();
            return listaProfesor;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar profesor
    public void add(String nombre, String apellido) {

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {
            
            ProfesorManager profesorManager = new ProfesorManager(); 
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
    
    public void modify(int idProfesor, String nombre, String apellido){
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){
            
            preparestatement.setString(1, nombre);
            preparestatement.setString(2, apellido);
            preparestatement.setInt(3, idProfesor); 
            
            preparestatement.executeUpdate(); 
            
            
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Profesor getByid(int id) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                if (resultSet.getInt("id")==id){
                    Profesor profesor = new Profesor();
                    profesor.setId(resultSet.getInt("id"));
                    profesor.setNombre(resultSet.getString("nombre"));
                    profesor.setApellido(resultSet.getString("apellido"));
                    resultSet.close();
                    return profesor; 
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null; 
    }

    public void seleccionMetodoProfesor(String OpcionMetodo) {
        ProfesorManager profesorManager = new ProfesorManager();
        Scanner lectura2 = new Scanner(System.in);
        switch (OpcionMetodo) {
            case "1": //all listar Profesor
                List<Profesor> allprofesor = profesorManager.getAll();
                System.out.println(allprofesor.toString());
                break;

            case "2": //Add Profesor
                System.out.println("Ingrese el:\nNombre: \n");
                String nombreA = lectura2.next();
                System.out.println("Apellido: \n");
                String apellidoA = lectura2.next();
                profesorManager.add(nombreA, apellidoA);
                
                break;
            case "3": //Eliminar Profesor
                System.out.println("Ingrese el ID a eliminar: \n");
                int idEliminar = lectura2.nextInt();
                profesorManager.delete(idEliminar);
                break;
            case "4":
                System.out.println("Ingrese el ID a modificar: \n");
                int idModify = lectura2.nextInt();
                System.out.println("Nombre: \n");
                String nombreA4 = lectura2.next();
                System.out.println("Apellido: \n");
                String apellidoA4 = lectura2.next();
                profesorManager.modify(idModify, nombreA4, apellidoA4);
                break;
            case "5":
                System.out.println("Ingrese el ID a buscar: \n");
                int idbuscar = lectura2.nextInt();
                Profesor profe = new Profesor();
                profe = profesorManager.getByid(idbuscar);
                System.out.println(profe.toString());
                break;
        }
    }
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ProfesorManager profesor = new ProfesorManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();
            
            profesor.seleccionMetodoProfesor(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
}
