/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebas.springboot.web.app.controller;

import entities.Cuotas;
import utils.ConnectionManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author developer
 */
public class CuotaManager {

    //Constantes
    private static final String SQL_INSERT = "INSERT INTO cuotas (fecha, fechavencimiento, idinscripcion, monto, pagos) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL = "SELECT * FROM cuotas";
    private static final String SQL_DELETE = "DELETE FROM cuotas WHERE idcuota=?";
    private static final String SQL_MODIFY = "UPDATE cuotas SET monto=? WHERE idcuota=?";
    private static final String SQL_PAGO = "UPDATE cuotas SET pagos=? WHERE idcuota=?";
    private static final int MONTO_CUOTA = 120000;
    private static final int Pagos = 0;

    public List<Cuotas> getAllCuota() {

        try ( Connection conn = ConnectionManager.getConnection();  Statement statement = conn.createStatement()) {
            List<Cuotas> listaCuota = new ArrayList();

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Cuotas cuota = new Cuotas();
                cuota.setIdcuota(resultSet.getInt("idcuota"));
                cuota.setMonto(resultSet.getInt("monto"));
                cuota.setFecha(resultSet.getTimestamp("fecha"));
                cuota.setFecha(resultSet.getTimestamp("fechavencimiento"));
                cuota.setIdinscripcion(resultSet.getInt("idinscripcion"));
                cuota.setPagos(resultSet.getInt("pagos"));
                listaCuota.add(cuota);
            }
            resultSet.close();
            return listaCuota;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return Collections.EMPTY_LIST;
    }

    public void generarCuota(int idinscripcion) {

        int cantidadCuotas = 6;
        try ( Connection conn = ConnectionManager.getConnection(); 
                PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT)) {
            Timestamp fechaActual = obtenerFechaYHoraActual();
            Timestamp fechaVencimiento = 
                    obtenerFechaYHoraVencimiento(
                            new java.sql.Date(fechaActual.getTime()));
            CuotaManager generarcuota = new CuotaManager();
            for (int i = 0; i < cantidadCuotas; i++) {
                Integer cuota = i + 1;
                preparestatement.setTimestamp(1,fechaActual);
                preparestatement.setTimestamp(2,fechaVencimiento);
                preparestatement.setInt(3, idinscripcion);
                preparestatement.setInt(4, MONTO_CUOTA);
                preparestatement.setInt(5, cuota.intValue());
                preparestatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    

    public void modify(int idcuota, int monto, byte pago) {
        try ( Connection conn = ConnectionManager.getConnection();  PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)) {
            preparestatement.setInt(1, idcuota);
            preparestatement.setInt(2, monto);
            preparestatement.setInt(3, pago);
            
            preparestatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    
    public void delete(int idcuota) {

        try ( Connection conn = ConnectionManager.getConnection();  PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

            preparestatement.setInt(1, idcuota);

            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    
    public Cuotas getByid(int idCuenta) {

        try ( Connection conn = ConnectionManager.getConnection();  Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {

                if (resultSet.getInt("idcuenta") == idCuenta) {
                    Cuotas cuenta = new Cuotas();
                    cuenta.setIdcuota(resultSet.getInt("idcuota"));;
                    cuenta.setIdinscripcion(resultSet.getInt("idinscripcion"));
                    cuenta.setMonto(resultSet.getInt("monto"));
                    cuenta.setFecha(resultSet.getTimestamp("fecha"));
                    cuenta.setPagos(resultSet.getInt("pagos"));
                    resultSet.close();
                    return cuenta;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    
    public void pagarCuota(int idcuota, int pago) {

        try ( Connection conn = ConnectionManager.getConnection();  PreparedStatement preparestatement = conn.prepareStatement(SQL_PAGO)) {

            preparestatement.setInt(1, pago);
            preparestatement.setInt(2, idcuota);
            preparestatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    
    public Timestamp obtenerFechaYHoraActual() {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        return new java.sql.Timestamp(date.getTime());
    }

    
    public Timestamp obtenerFechaYHoraVencimiento(java.sql.Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        calendario.set(Calendar.DAY_OF_MONTH, 1);
        calendario.add(Calendar.MONTH, +1);
        return new java.sql.Timestamp(date.getTime());
    }
    
    
    public void seleccionMetodoCuenta(String OpcionMetodo) {
		CuotaManager cuotaManager = new CuotaManager();
		Scanner lectura2 = new Scanner(System.in);
		switch (OpcionMetodo) {
		case "1": // all lista Cuenta
			List<Cuotas> allcuota = cuotaManager.getAllCuota();
			System.out.println(allcuota.toString());
			break;
                        
                case "2": //Add Cuota
                System.out.println("Ingrese:\nId Inscripcion: \n");
                int idinscripcion = lectura2.nextInt();
                cuotaManager.generarCuota(idinscripcion);
                        break;
                
		case "3": // Eliminar Cuenta
			System.out.println("Ingrese el ID a eliminar: \n");
			int idEliminar = lectura2.nextInt();
			cuotaManager.delete(idEliminar);
			break;
			
		case "4": // Modificar Cuenta
			System.out.println("Ingrese el ID a modificar: \n");
			int idcuota = lectura2.nextInt();
			System.out.println("Monto: \n");
			int monto = lectura2.nextInt();
                        System.out.println("Pago: \n");
			byte pago = lectura2.nextByte();
			cuotaManager.modify(idcuota, monto, pago);
			break;
			
		case "5": // Buscar Cuenta
			System.out.println("Ingrese el ID a buscar de la cuenta: \n");
			int idbuscar = lectura2.nextInt();
			Cuotas cuenta = new Cuotas();
			cuenta = cuotaManager.getByid(idbuscar);
			System.out.println(cuenta.toString());
			int pagosJ = cuenta.getPagos();
			if(pagosJ==4) {
				System.out.println("Cuenta PAGADA");
				break;
			}
			System.out.println("Pagos pendientes: \n1. Pagar la cuota\n2. Salir");
			String OpPago = lectura2.next();
			switch (OpPago) {
			case "1":
				pagosJ++;
				System.out.println("Pagar la cuenta " + pagosJ + "/4\n" );
				System.out.println("Ingresar el numero de cuotas a pagar sobre /4: " );
				int Opagos = lectura2.nextByte();
				cuotaManager.pagarCuota(idbuscar, Pagos);
				break;
			case "2":
				break;
			}
			break;
		case "6":
			break; 
		}
	}
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        CuotaManager cuota = new CuotaManager();
        int bucle = 1;
        while (0 != bucle) {
            System.out.println("\nIngrese el metodo a utilizar: \n1. Ver lista\n2. Agregar\n3. Eliminar\n4. Modificar\n5. Buscar por id");
            String opcionmetodo = lectura.next();
            
            cuota.seleccionMetodoCuenta(opcionmetodo);
            System.out.println("Si desea terminar ingrese 0 y si desea continuar cualquier numero distinto de 0");
            bucle = lectura.nextInt();
        }
    }
    
}