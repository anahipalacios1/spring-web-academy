package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.models.Cuota;
import com.bolsadeideas.springboot.web.app.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.sql.Timestamp;

public class CuotaManager {

	private static final String SQL_INSERT = "INSERT INTO cuota (fecha, fechavencimiento, idinscripcion, monto, pagos) VALUES (?,?,?,?,?)";
	private static final String SQL = "SELECT * FROM cuota";
	private static final String SQL_DELETE = "DELETE FROM cuota WHERE idcuota=?";
	private static final String SQL_MODIFY = "UPDATE cuota SET monto=?, pagos=? WHERE idcuota=?";
	private static final int Monto = 120000;
	private static final int Pagos = 0; // Pagos llega hasta 4 cuotas por semestre.

	public List<Cuota> getAllCuota() {

		try (Connection conn = ConnectionManager.getConnection(); Statement statement = conn.createStatement()) {
			List<Cuota> lista = new ArrayList<>();

			ResultSet resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				Cuota cuota = new Cuota();
				cuota.setIdcuota(resultSet.getInt("idcuota"));
				cuota.setFecha(resultSet.getTimestamp("fecha"));
				cuota.setFechavencimiento(resultSet.getTimestamp("fechavencimiento"));
				cuota.setIdinscripcion(resultSet.getInt("idinscripcion"));
				cuota.setMonto(resultSet.getInt("monto"));
				cuota.setPagos(resultSet.getInt("pagos"));
				lista.add(cuota);
			}
			resultSet.close();
			return lista;
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}

		return Collections.EMPTY_LIST;
	}

	public Cuota add(int idinscripcion) throws SQLException {
		int cantidadCuotas = 3;
		int affectedRows = 0;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement preparestatement = conn.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS)) {
			Timestamp fecha = new Timestamp(System.currentTimeMillis());
			fecha = obtenerFechaYHoraActual();
			Timestamp fechavencimiento = new Timestamp(System.currentTimeMillis());
			fechavencimiento.setMonth(fecha.getMonth()+1);
			CuotaManager generarcuota = new CuotaManager();
			for (int i = 0; i < cantidadCuotas; i++) {
				Integer pago = i + 1;
				preparestatement.setTimestamp(1, fecha);
				preparestatement.setTimestamp(2, fechavencimiento);
				preparestatement.setInt(3, idinscripcion);
				preparestatement.setInt(4, Monto);
				preparestatement.setInt(5, pago.intValue());
				affectedRows = preparestatement.executeUpdate()+affectedRows;
			}
			Cuota cuotas = new Cuota();


			if (affectedRows < 3) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparestatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					cuotas.setFecha(fecha);
					cuotas.setFechavencimiento(fechavencimiento);
					cuotas.setIdinscripcion(idinscripcion);
					cuotas.setMonto(Monto);
					cuotas.setPagos(Pagos);

					cuotas.setIdcuota(generatedKeys.getInt(1));
					return cuotas;
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
		}
	}

	public Timestamp obtenerFechaYHoraActual() {
		Timestamp date = new Timestamp(System.currentTimeMillis());
        long timeInMilliSeconds = date.getTime();
        java.sql.Timestamp date1 = new java.sql.Timestamp(timeInMilliSeconds);
        return date1;
        /*java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		return new java.sql.Timestamp(date.getTime());*/
	}

	// Si no se puede eliminar retorna flase, en caso contrario true si se pudo y
	// tambien si no se encuentra en la base de datos
	public boolean delete(int idCursoHabilitado) {

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement preparestatement = conn.prepareStatement(SQL_DELETE)) {

			preparestatement.setInt(1, idCursoHabilitado);

			preparestatement.executeUpdate();

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			return false;
		}
		return true;
	}

	public void modify(int idcuota, int monto, int pagos) {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement preparestatement = conn.prepareStatement(SQL_MODIFY)) {

			preparestatement.setInt(1, monto);
			preparestatement.setInt(3, pagos);
			preparestatement.setInt(4, idcuota);

			preparestatement.executeUpdate();

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
	}

	public Cuota getByid(int idcuota) {

		try (Connection conn = ConnectionManager.getConnection(); Statement statement = conn.createStatement()) {

			ResultSet resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {

				if (resultSet.getInt("idcuota") == idcuota) {
					Cuota cuota = new Cuota();
					cuota.setIdcuota(resultSet.getInt("idcuota"));
					cuota.setFecha(resultSet.getTimestamp("fecha"));
					cuota.setFechavencimiento(resultSet.getTimestamp("fechavencimiento"));
					cuota.setIdinscripcion(resultSet.getInt("idinscripcion"));
					cuota.setMonto(resultSet.getInt("monto"));
					cuota.setPagos(resultSet.getInt("pagos"));

					resultSet.close();
					return cuota;
				}

			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		return null;
	}

}
