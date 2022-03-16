/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;

import entities.Inscripciones;
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
public class InscripcionManager {
    //Constantes
    private static final long serialVersionUID = 1L;
    private static final String SQL_INSERT = "INSERT INTO inscripciones (idcursohabilitado, idalumno) VALUES (?,?)";
    private static final String SQL = "SELECT * FROM inscripciones";
    private static final String SQL_DELETE = "DELETE FROM inscripciones WHERE idinscripcion=?";
    private static final String SQL_MODIFY = "UPDATE inscripciones SET idcursohabilitado=?, idalumno=? WHERE idinscripcion=?";

    public List<Inscripciones> getAll() {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {
            List<Inscripciones> listaInscripciones = new ArrayList();
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Inscripciones inscripcion = new Inscripciones();
                inscripcion.setIdInscripcion(resultSet.getInt("idinscripcion"));
                inscripcion.setIdCursoHabilitado(resultSet.getInt("idcursohabilitado"));
                inscripcion.setIdAlumno(resultSet.getInt("idalumno"));

                listaInscripciones.add(inscripcion);
            }
            resultSet.close();
            return listaInscripciones;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    //metodo para agregar materia
    public void add(int idcursohabilitado, int idalumno) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {

            InscripcionManager inscripcion = new InscripcionManager();
            preparestatement.setInt(1, idcursohabilitado);
            preparestatement.setInt(2, idalumno);

            // statement.executeUpdate(sql)– NormalQL State: 08003mente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void delete(int idinscripcion) {

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, idinscripcion);
            // statement.executeUpdate(sql)– Normalmente para DML como INSERTAR, ACTUALIZAR, ELIMINAR
            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public void modify(int idcursohabilitado, int idalumno, int idinscripcion) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)) {

            preparestatement.setInt(1, idcursohabilitado);
            preparestatement.setInt(2, idalumno);
            preparestatement.setInt(3, idinscripcion);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public Inscripciones getByid(int idinscripcion) {

        try (Connection conn = ConnectionManager.getConnection();
                java.sql.Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                if (resultSet.getInt("idinscripcion") == idinscripcion) {
                    Inscripciones inscripcion = new Inscripciones();
                    inscripcion.setIdCursoHabilitado(resultSet.getInt("idcursohabilitado"));
                    inscripcion.setIdAlumno(resultSet.getInt("idalumno"));

                    resultSet.close();
                    return inscripcion;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    public void seleccionMetodoInscripcion(String OpcionMetodo) {
        InscripcionManager inscripcionManager = new InscripcionManager();
        Scanner lectura2 = new Scanner(System.in);
        switch (OpcionMetodo) {
            case "1": //all listar CursosHabilitados
                List<Inscripciones> allinscripciones = inscripcionManager.getAll();
                System.out.println(allinscripciones.toString());
                break;

            case "2": //Add Materia
                System.out.println("Ingrese el:\nid curso habilitado: \n");
                int idcursohabilitado = lectura2.nextInt();
                System.out.println("Ingrese el:\nidalumno: \n");
                int idalumno = lectura2.nextInt();
                inscripcionManager.add(idcursohabilitado, idalumno);
                break;
            case "3": //Eliminar Materia
                System.out.println("Ingrese el ID a eliminar de la inscripcion: \n");
                int idEliminar = lectura2.nextInt();
                inscripcionManager.delete(idEliminar);
                break;
            case "4":
                System.out.println("Ingrese el id de la inscripcion");
                int idinscripcion = lectura2.nextInt();
                System.out.println("Ingrese el id del curso habilitado: \n");
                idcursohabilitado = lectura2.nextInt();
                System.out.println("Ingrese id alumno: \n");
                idalumno = lectura2.nextInt();
                inscripcionManager.modify(idinscripcion, idcursohabilitado, idalumno);
                break;
            case "5":
                System.out.println("Ingrese el ID a buscar: \n");
                int idbuscar = lectura2.nextInt();
                Inscripciones inscripcion = new Inscripciones();
                inscripcion = inscripcionManager.getByid(idbuscar);
                System.out.println(inscripcion.toString());
                break;
        }
    }

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        InscripcionManager inscripcion = new InscripcionManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();

            inscripcion.seleccionMetodoInscripcion(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
}
