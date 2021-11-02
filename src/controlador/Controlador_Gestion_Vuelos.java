package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import exceptions.ExceptionVuelo;
import modelo.DAOVuelos;
import modelo.DTOVuelo;
import vista_menu_Gestion.Vista_Gestion_Vuelos;

public class Controlador_Gestion_Vuelos {
	private Vista_Gestion_Vuelos vista;
	private DAOVuelos dao;

	public Controlador_Gestion_Vuelos() throws SQLException, ExceptionVuelo {
		super();
		this.vista = new Vista_Gestion_Vuelos();
		this.dao = new DAOVuelos();
		rellenarComboBox();
		asociaListeners();
		clear();
		this.vista.setVisible(true);
		this.vista.setLocationRelativeTo(null);
	}

	private void asociaListeners() {
		vista.btnSearch.addMouseListener(new BtnSearchMouseListener());
		vista.btnInsert.addMouseListener(new BtnInsertMouseListener());
		vista.btnUpdate.addMouseListener(new BtnUpdateMouseListener());
		vista.btnDelete.addMouseListener(new BtnDeleteMouseListener());
		vista.btnExit.addMouseListener(new BtnExitMouseListener());
		vista.btnClear.addMouseListener(new BtnClearMouseListener());
	}

	/*
	 * LISTENERS
	 */
	// Método Listener para buscar:
	private class BtnSearchMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				DTOVuelo vuelo = dao.consultaPorCodVuelo(String.valueOf(vista.tFCodVuelo.getText()));
				if (vuelo != null) {
					vista.tFCodVuelo.setEditable(false);
					recogerDatosVuelo(vuelo);
					activarBotonesInsertUpdate();
					vista.btnUpdate.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Código de vuelo ya existe, modifique los campos que desee.");
				} else {
					if (!vista.tFCodVuelo.getText().equals("")) {
						vista.btnInsert.setEnabled(true);
						activarBotonesInsertUpdate();
						vista.btnDelete.setEnabled(false);
						JOptionPane.showMessageDialog(null, "Código de vuelo no existente.");
					} else {
						vista.tFCodVuelo.requestFocus();
					}
				}
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			}

		}
	}

	// Método Listener Insertar vuelo en T_VUELOS de MySQL:
	private class BtnInsertMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				DTOVuelo vuelo = guardaVuelo();
				dao = new DAOVuelos();

				if (dao.insert(vuelo)) {
					JOptionPane.showMessageDialog(null, "Vuelo insertado correctamente.");
					clear();
					reseteoBotones();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "No es posible crear vuelo.", "Alert", JOptionPane.WARNING_MESSAGE);
				clear();
			}
		}
	}

	// Método para actualizar campos en T_VUELOS de MySQL:
	private class BtnUpdateMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				DTOVuelo vuelo = guardaVuelo();
				dao = new DAOVuelos();

				if (dao.update(vuelo)) {
					JOptionPane.showMessageDialog(null, "Vuelo actualizado correctamente.");
					clear();
					reseteoBotones();
				}
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			}
		}
	}

	// Método para eliminar una key en T_VUELOS de MySQL:
	private class BtnDeleteMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				dao = new DAOVuelos();
				String codVuelo = vista.tFCodVuelo.getText().trim().toUpperCase();

				int n = JOptionPane.showConfirmDialog(null,
						"Se va a eliminar el vuelo \"" + codVuelo.toUpperCase() + "\". ¿Está seguro?", "Confirm delete",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				if (n == 0) {
					if (dao.delete(codVuelo)) {
						JOptionPane.showMessageDialog(null, "Vuelo eliminado correctamente");
						clear();
					} else {
						JOptionPane.showMessageDialog(null,
								"Código de vuelo \"" + codVuelo + "\" no existe en BDD, introduzca de nuevo.", "Alert",
								JOptionPane.WARNING_MESSAGE);
						clear();
					}
				}
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
		}
	}

	/*
	 * MÉTODOS PARA GUARDAR VUELOS, MODIFICAR, ELIMINAR, RESETEAR CAMPOS Y SALIR
	 */
	// Método para guardar todo el texto introducido en los JTextField en variables
	private DTOVuelo guardaVuelo() throws ExceptionVuelo {
		DTOVuelo vuelo = null;

		String codVuelo = vista.tFCodVuelo.getText().trim().toUpperCase();
		String codAeropuerto = (String) vista.comboBoxCodAerop.getSelectedItem();
		String destinoVuelo = vista.tFDestinoVuelo.getText().trim().toUpperCase();
		String fechaVuelo = vista.tFFechaVuelo.getText().trim();
		String precioVuelo = vista.tFPrecio.getText().trim();
		String numPlazas = vista.tFNumPlazas.getText().trim();
		String numPasajeros = vista.tFNumPasajeros.getText();
		try {
			vuelo = new DTOVuelo(codVuelo, codAeropuerto, destinoVuelo, fechaVuelo, precioVuelo, numPlazas,
					numPasajeros);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return vuelo;
	}

	// Método Listener para salir de la app ("exit"):
	private class BtnExitMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			System.exit(0);
		}
	}

	// Método Listener para limpiar todos los campos ("clear"):
	private class BtnClearMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			clear();
		}
	}

	// Métodos auxiliares para resetear campos:
	private void clear() {
		vista.tFCodVuelo.setEnabled(true);
		vista.comboBoxCodAerop.setSelectedIndex(0);
		vista.tFCodVuelo.setText(null);
		vista.tFCodVuelo.requestFocus();
		vista.tFDestinoVuelo.setText(null);
		vista.tFFechaVuelo.setText(null);
		vista.tFPrecio.setText(null);
		vista.tFNumPlazas.setText(null);
		vista.tFNumPasajeros.setText(null);
		reseteoBotones();
	}

	private void reseteoBotones() {
		vista.btnSearch.setEnabled(true);
		vista.btnInsert.setEnabled(false);
		vista.btnUpdate.setEnabled(false);
		vista.btnDelete.setEnabled(false);
		vista.tFCodVuelo.setEditable(true);
		vista.comboBoxCodAerop.setEnabled(false);
		vista.tFDestinoVuelo.setEditable(false);
		vista.tFFechaVuelo.setEditable(false);
		vista.tFPrecio.setEditable(false);
		vista.tFNumPlazas.setEditable(false);
		vista.tFNumPasajeros.setEditable(false);
	}

	private void activarBotonesInsertUpdate() {
		vista.tFCodVuelo.setEnabled(false);
		vista.btnSearch.setEnabled(false);
		vista.btnDelete.setEnabled(true);
		vista.comboBoxCodAerop.setEnabled(true);
		vista.tFDestinoVuelo.setEditable(true);
		vista.tFFechaVuelo.setEditable(true);
		vista.tFPrecio.setEditable(true);
		vista.tFNumPlazas.setEditable(true);
		vista.tFNumPasajeros.setEditable(true);
	}

	private void recogerDatosVuelo(DTOVuelo vuelo) {
		vista.comboBoxCodAerop.setSelectedItem(vuelo.getCodigoAe());
		vista.tFDestinoVuelo.setText(vuelo.getDestino());
		vista.tFFechaVuelo.setText(vuelo.getFecha().toString());
		vista.tFPrecio.setText(vuelo.getPrecio().toString());
		vista.tFNumPlazas.setText(vuelo.getNumPlazas().toString());
		vista.tFNumPasajeros.setText(vuelo.getNumPasajeros().toString());
	}

	@SuppressWarnings("unchecked")
	public void rellenarComboBox() throws SQLException, ExceptionVuelo {
		vista.comboBoxCodAerop = dao.llenarComboBox(vista.comboBoxCodAerop);
	}
}