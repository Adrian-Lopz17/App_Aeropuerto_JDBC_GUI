package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import modelo.DAOVuelos;
import vista_menu_Consultas.Vista_Consultas_Vuelos;

public class Controlador_Consultas_Vuelos {
	private Vista_Consultas_Vuelos vista;
	private DAOVuelos dao;

	public Controlador_Consultas_Vuelos() throws SQLException {
		super();
		this.vista = new Vista_Consultas_Vuelos();
		this.dao = new DAOVuelos();
		asociaListeners();
		clear();
		this.vista.setVisible(true);
		this.vista.setLocationRelativeTo(null);
	}

	private void asociaListeners() {
		vista.btnExecuteQuery.addMouseListener(new BtnExecuteQueryMouseListener());
		vista.btnClear.addMouseListener(new BtnClearMouseListener());
		vista.btnAll.addMouseListener(new BtnAllMouseListener());
	}
	
	/*
	 * LISTENERS
	 */

	// Método Listener para ejecutar las consultas introducidas en los JTextField:
	private class BtnExecuteQueryMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			crearTablaCompleta();
		}
	}

	private class BtnAllMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			crearTablaAllVuelos();
		}
	}

	// Método Listener limpiar todos los campos ("clear"):
	private class BtnClearMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			clear();
		}
	}

	// Métodos auxiliares para resetear campos
	private void clear() {
		vista.tFConsultaCodVuelo.setText(null);
		vista.tFConsultaCodVuelo.requestFocus();
		vista.tFConsultaDestino.setText(null);
		vista.tFConsultaOcupados.setText(null);
		vista.tFConsultaFecha.setText(null);
		vista.table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "CODIGO_VUELO", "CODIGO_AEROPUERTO", "DESTINO_VUELO", "FECHA_VUELO", "PRECIO_VUELO",
						"NUMERO_PLAZAS_VUELO", "NUMERO_PASAJEROS_VUELO" }));
		vista.scrollPane.setViewportView(vista.table);
	}

	/*
	 * MÉTODOS PARA CREAR JTABLE
	 */

	private void plantillaTabla(ResultSet resultado) throws SQLException {
		// Vista_Consultas_Tablas tabla = new Vista_Consultas_Tablas();
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
		String codVuelo = vista.tFConsultaCodVuelo.getText().trim().toUpperCase();
		String codAero = vista.tFConsultaOcupados.getText().trim().toUpperCase();
		String destino = vista.tFConsultaDestino.getText().trim().toUpperCase();
		String fecha = vista.tFConsultaFecha.getText().trim();

		try {
			resultado = dao.consultasAnidadasRS(codVuelo, codAero, destino, fecha);
			plantillaTabla(resultado);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void crearTablaAllVuelos() {
		ResultSet resultado;
		try {
			resultado = dao.readRS();
			plantillaTabla(resultado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}