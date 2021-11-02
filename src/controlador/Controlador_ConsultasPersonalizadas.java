package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import modelo.DAOVuelos;
import vista_menu_Consultas.Vista_Consultas_Personalizadas;

public class Controlador_ConsultasPersonalizadas {
	private Vista_Consultas_Personalizadas vista;
	private DAOVuelos daoVuelo;

	public Controlador_ConsultasPersonalizadas() throws SQLException {
		super();
		this.vista = new Vista_Consultas_Personalizadas();
		this.daoVuelo = new DAOVuelos();
		asociaListeners();
		clear();
		this.vista.setVisible(true);
		this.vista.setLocationRelativeTo(null);
	}

	private void asociaListeners() {
		vista.btnConsultar.addMouseListener(new BtnEjecutarMouseListener());
		vista.btnClear.addMouseListener(new BtnClearMouseListener());
	}

	/*
	 * LISTENERS
	 */

	// Método Listener para ejecutar las consultas introducidas en los JTextField:
	private class BtnEjecutarMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			String consulta = vista.textAreaConsulta.getText().trim().toUpperCase();
			crearTablaReadSQLVuelo(consulta);
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
	 * MÉTODOS PARA RESETEAR CAMPOS, PLANTILLA Y MÉTODO PARA CREAR TABLAS
	 */

	// Métodos auxiliares para resetear campos
	private void clear() {
		vista.textAreaConsulta.setText(null);
		vista.textAreaConsulta.requestFocus();
		vista.table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "CODIGO_VUELO", "CODIGO_AEROPUERTO", "DESTINO_VUELO", "FECHA_VUELO", "PRECIO_VUELO",
						"NUMERO_PLAZAS_VUELO", "NUMERO_PASAJEROS_VUELO" }));
		vista.scrollPane.setViewportView(vista.table);
	}

	// MÉTODOS PARA CREAR JTABLE
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

	private void crearTablaReadSQLVuelo(String SQL) {
		ResultSet resultado;
		try {
			resultado = daoVuelo.readSQLVueloRS(SQL);
			plantillaTabla(resultado);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			;
		}
	}
}