/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;

import com.pruebas.springboot.web.app.utils.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.bolsadeideas.springboot.web.app.models.Curso;
import com.pruebas.springboot.web.app.models.*;
/**
 *
 * @author developer
 */
public class CursoManager {
    //Constantes
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO curso (nombre) VALUES (?)";
    private static final String SQL = "SELECT * FROM curso";
    private static final String SQL_DELETE = "DELETE FROM curso WHERE id=?";
    private static final String SQL_MODIFY = "UPDATE curso SET nombre=? WHERE id=?";

	public List<Curso> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<Curso> listaCurso = new ArrayList();
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setId(resultSet.getInt("id"));
                curso.setNombre(resultSet.getString("nombre"));
                
                listaCurso.add(curso);
            }
            resultSet.close();
            return listaCurso;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar curso
    public Curso add(String descripcion) throws SQLException {

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT,
            		Statement.RETURN_GENERATED_KEYS)) {
            

            Curso curso = new Curso();

            int affectedRows = preparestatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparestatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    curso.setDescripcion(descripcion);


                    curso.setIdcurso(generatedKeys.getInt(1));
                    return curso;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw e;
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
    
    public void modify(int idCurso, String nombre){
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){
            
            preparestatement.setString(1, nombre);
            preparestatement.setInt(2, idCurso); 
            
            preparestatement.executeUpdate(); 
            
            
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Curso getByid(int id) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                if (resultSet.getInt("id")==id){
                    Curso curso = new Curso();
                    curso.setId(resultSet.getInt("id"));
                    curso.setNombre(resultSet.getString("nombre"));
                    resultSet.close();
                    return curso; 
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null; 
    }

    public void seleccionMetodoCurso(String OpcionMetodo) {
        CursoManager cursoManager = new CursoManager();
        Scanner lectura2 = new Scanner(System.in);
        switch (OpcionMetodo) {
            case "1": //all listar Curso
                List<Curso> allcurso = cursoManager.getAll();
                System.out.println(allcurso.toString());
                break;

            case "2": //Add Curso
                System.out.println("Ingrese:\nCurso: \n");
                String nombre = lectura2.next();
                cursoManager.add(nombre);
                break;
            case "3": //Eliminar Curso
                System.out.println("Ingrese el ID a eliminar: \n");
                int idEliminar = lectura2.nextInt();
                cursoManager.delete(idEliminar);
                break;
            case "4":
                System.out.println("Ingrese el ID a modificar: \n");
                int idModify = lectura2.nextInt();
                System.out.println("nombre: \n");
                String nombreA4 = lectura2.next();
                cursoManager.modify(idModify, nombreA4);
                break;
            case "5":
                System.out.println("Ingrese el ID a buscar: \n");
                int idbuscar = lectura2.nextInt();
                Curso curso = new Curso();
                curso = cursoManager.getByid(idbuscar);
                System.out.println(curso.toString());
                break;
        }
    }
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        CursoManager curso = new CursoManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();
            
            curso.seleccionMetodoCurso(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
}