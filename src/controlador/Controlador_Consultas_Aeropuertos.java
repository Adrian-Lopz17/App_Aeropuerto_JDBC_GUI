package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import modelo.DAOAeropuertos;
import vista_menu_Consultas.Vista_Consultas_Aeropuertos;

public class Controlador_Consultas_Aeropuertos {
	private Vista_Consultas_Aeropuertos vista;
	private DAOAeropuertos dao;

	public Controlador_Consultas_Aeropuertos() throws SQLException {
		super();
		this.vista = new Vista_Consultas_Aeropuertos();
		this.dao = new DAOAeropuertos();
		asociaListeners();
		clear();
		this.vista.setVisible(true);
		this.vista.setLocationRelativeTo(null);
	}

	private void asociaListeners() {
		vista.btnExecuteQuery.addMouseListener(new BtnExecuteQueryMouseListener());
		vista.btnClear.addMouseListener(new BtnClearMouseListener());
		vista.btnAll.addMouseListener(new BtnAllAeropuertosMouseListener());
	}

	/*
	 * LISTENERS
	 */

	// Método Listener para ejecutar consulta "Mostrar todas las filas de T_VUELOS":
	private class BtnAllAeropuertosMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			crearTablaAllAeros();
		}
	}

	// Método Listener para ejecutar las consultas:
	private class BtnExecuteQueryMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			crearTablaCompleta();
		}
	}

	// Método Listener limpiar todos los campos ("clear"):
	private class BtnClearMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			clear();
		}
	}

	/*
	 * MÉTODOS PARA CREAR JTABLES, RESETEAR CAMPOS...
	 */
	
	// Métodos auxiliares para resetear campos
	private void clear() {
		vista.tFConsultaCodAero.setText(null);
		vista.tFConsultaCodAero.requestFocus();
		vista.tFConsultaNombre.setText(null);
		vista.table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "CODIGO_AEROPUERTO", "NOMBRE_AEROPUERTO" }));
		vista.scrollPane.setViewportView(vista.table);
	}

	// MÉTODOS PARA CREAR JTABLE
	private void plantillaTabla(ResultSet resultado) throws SQLException {

		ResultSetMetaData meta;

		meta = resultado.getMetaData();
		String[] columnas = new String[meta.getColumnCount()];
		for (int i = 0; i < columnas.length; i++) {
			columnas[i] = meta.getColumnName(i + 1);
		}

		DefaultTableModel model = new DefaultTableModel(columnas, 0);
		while (resultado.next()) {
			Object[] laFila = new Object[meta.getColumnCount()];
			for (int i = 0; i < laFila.length; i++) {
				laFila[i] = resultado.getString(i + 1);
			}
			model.addRow(laFila);
		}
		vista.table.setModel(model);
		vista.setVisible(true);
		vista.setLocationRelativeTo(null);
	}

	private void crearTablaCompleta() {
		ResultSet resultado;
		String codAero = vista.tFConsultaCodAero.getText().trim().toUpperCase();
		String codNombre = vista.tFConsultaNombre.getText().trim().toUpperCase();

		try {
			resultado = dao.consultasAnidadasRS(codAero, codNombre);
			plantillaTabla(resultado);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void crearTablaAllAeros() {
		ResultSet resultado;
		try {
			resultado = dao.readRS();
			plantillaTabla(resultado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}