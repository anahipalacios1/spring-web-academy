/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;

import entities.CursosHabilitados;
import utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author developer
 */
public class CursoHabilitadoManager {

    //Constantes
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO cursoshabilitados (idcurso, idmateria, idprofesor) VALUES (?,?,?)";
    private static final String SQL = "SELECT * FROM cursoshabilitados";
    private static final String SQL_DELETE = "DELETE FROM cursoshabilitados WHERE idcursohabilitado=?";
    private static final String SQL_MODIFY = "UPDATE cursoshabilitados SET idcurso=?, idmateria=?, idprofesor=? WHERE idcursohabilitado=?";

    public List<CursosHabilitados> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<CursosHabilitados> listaCursosHabilitados = new ArrayList();
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                CursosHabilitados cursohabilitado = new CursosHabilitados();
                cursohabilitado.setIdCursoHabilitado(resultSet.getInt("idcursohabilitado"));
                cursohabilitado.setIdCurso(resultSet.getInt("idcurso"));
                cursohabilitado.setIdMateria(resultSet.getInt("idmateria"));
                cursohabilitado.setIdProfesor(resultSet.getInt("idprofesor"));

                listaCursosHabilitados.add(cursohabilitado);
            }
            resultSet.close();
            return listaCursosHabilitados;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar materia
    public void add(int idcurso, int idmateria, int idprofesor) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {

            CursoHabilitadoManager cursoHabilitado = new CursoHabilitadoManager();
            preparestatement.setInt(1, idcurso);
            preparestatement.setInt(2, idmateria);
            preparestatement.setInt(3, idprofesor);

            // statement.executeUpdate(sql)– NormalQL State: 08003mente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void delete(int idcursohabilitado) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, idcursohabilitado);
            // statement.executeUpdate(sql)– Normalmente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void modify(int idcursohabilitado, int idcurso, int idmateria, int idprofesor) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)) {

            preparestatement.setInt(1, idcurso);
            preparestatement.setInt(2, idmateria);
            preparestatement.setInt(3, idprofesor);
            preparestatement.setInt(4, idcursohabilitado);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public CursosHabilitados getByid(int idcursohabilitado) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                if (resultSet.getInt("idcursohabilitado") == idcursohabilitado) {
                    CursosHabilitados cursohabilitado = new CursosHabilitados();
                    cursohabilitado.setIdCurso(resultSet.getInt("idcurso"));
                    cursohabilitado.setIdMateria(resultSet.getInt("idmateria"));
                    cursohabilitado.setIdProfesor(resultSet.getInt("idprofesor"));

                    resultSet.close();
                    return cursohabilitado;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    public void seleccionMetodoCursoHabilitados(String OpcionMetodo) {
        CursoHabilitadoManager cursoHabilitadoManager = new CursoHabilitadoManager();
        Scanner lectura2 = new Scanner(System.in);
        switch (OpcionMetodo) {
            case "1": //all listar CursosHabilitados
                List<CursosHabilitados> allcursoshabilitados = cursoHabilitadoManager.getAll();
                System.out.println(allcursoshabilitados.toString());
                break;

            case "2": //Add Materia
                System.out.println("Ingrese el:\nidcurso: \n");
                int idcurso = lectura2.nextInt();
                System.out.println("Ingrese el:\nidmateria: \n");
                int idmateria = lectura2.nextInt();
                System.out.println("Ingrese el:\nidprofesor: \n");
                int idprofesor = lectura2.nextInt();
                cursoHabilitadoManager.add(idcurso, idmateria, idprofesor);
                break;
            case "3": //Eliminar Materia
                System.out.println("Ingrese el ID a eliminar del curso habilitado: \n");
                int idEliminar = lectura2.nextInt();
                cursoHabilitadoManager.delete(idEliminar);
                break;
            case "4":
                System.out.println("Ingrese el id del curso habilitado a modificar");
                int idcursohabilitado = lectura2.nextInt();
                System.out.println("Ingrese el id del curso: \n");
                idcurso = lectura2.nextInt();
                System.out.println("Ingrese id materia: \n");
                idmateria = lectura2.nextInt();
                System.out.println("Ingrese id profesor: \n");
                idprofesor = lectura2.nextInt();
                cursoHabilitadoManager.modify(idcursohabilitado, idcurso, idmateria, idprofesor);
                break;
            case "5":
                System.out.println("Ingrese el ID a buscar: \n");
                int idbuscar = lectura2.nextInt();
                CursosHabilitados cursohabilitado = new CursosHabilitados();
                cursohabilitado = cursoHabilitadoManager.getByid(idbuscar);
                System.out.println(cursohabilitado.toString());
                break;
        }
    }

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        CursoHabilitadoManager cursohabilitado = new CursoHabilitadoManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();

            cursohabilitado.seleccionMetodoCursoHabilitados(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
}
