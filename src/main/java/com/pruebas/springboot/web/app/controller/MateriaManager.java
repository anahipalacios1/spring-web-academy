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
import entities.Materia;

/**
 *
 * @author developer
 */
public class MateriaManager {
    //Constantes
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO materia (asignatura) VALUES (?)";
    private static final String SQL = "SELECT * FROM materia";
    private static final String SQL_DELETE = "DELETE FROM materia WHERE id=?";
    private static final String SQL_MODIFY = "UPDATE materia SET asignatura=? WHERE id=?";

	public List<Materia> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<Materia> listaMateria = new ArrayList();
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setId(resultSet.getInt("id"));
                materia.setDescripcion(resultSet.getString("asignatura"));
                
                listaMateria.add(materia);
            }
            resultSet.close();
            return listaMateria;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar materia
    public void add(String asignatura) {

        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {
            
            MateriaManager materiaManager = new MateriaManager(); 
            preparestatement.setString(1,asignatura);
            
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
    
    public void modify(int idMateria, String asignatura){
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)){
            
            preparestatement.setString(1, asignatura);
            preparestatement.setInt(2, idMateria); 
            
            preparestatement.executeUpdate(); 
            
            
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Materia getByid(int id) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                if (resultSet.getInt("id")==id){
                    Materia materia = new Materia();
                    materia.setId(resultSet.getInt("id"));
                    materia.setDescripcion(resultSet.getString("asignatura"));
                    resultSet.close();
                    return materia; 
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null; 
    }

    public void seleccionMetodoMateria(String OpcionMetodo) {
        MateriaManager materiaManager = new MateriaManager();
        Scanner lectura2 = new Scanner(System.in);
        switch (OpcionMetodo) {
            case "1": //all listar Materia
                List<Materia> allmateria = materiaManager.getAll();
                System.out.println(allmateria.toString());
                break;

            case "2": //Add Materia
                System.out.println("Ingrese el:\nAsignatura: \n");
                String descripcion = lectura2.next();
                materiaManager.add(descripcion);
                break;
            case "3": //Eliminar Materia
                System.out.println("Ingrese el ID a eliminar: \n");
                int idEliminar = lectura2.nextInt();
                materiaManager.delete(idEliminar);
                break;
            case "4":
                System.out.println("Ingrese el ID a modificar: \n");
                int idModify = lectura2.nextInt();
                System.out.println("asignatura: \n");
                String asignaturaA4 = lectura2.next();
                materiaManager.modify(idModify, asignaturaA4);
                break;
            case "5":
                System.out.println("Ingrese el ID a buscar: \n");
                int idbuscar = lectura2.nextInt();
                Materia profe = new Materia();
                profe = materiaManager.getByid(idbuscar);
                System.out.println(profe.toString());
                break;
        }
    }
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        MateriaManager materia = new MateriaManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();
            
            materia.seleccionMetodoMateria(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
}
