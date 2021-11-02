package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.DAOAeropuertos;
import modelo.DTOAeropuerto;
import vista_menu_Gestion.Vista_Gestion_Aeropuertos;

public class Controlador_Gestion_Aeropuertos {
	private Vista_Gestion_Aeropuertos vista;
	private DAOAeropuertos dao;

	public Controlador_Gestion_Aeropuertos() throws SQLException {
		super();
		this.vista = new Vista_Gestion_Aeropuertos();
		this.dao = new DAOAeropuertos();
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
				DTOAeropuerto aero = dao.consultaPorCodigoAero(String.valueOf(vista.tFCodAero.getText()));
				if (aero != null) {
					vista.tFCodAero.setEditable(false);
					recogerDatosVuelo(aero);
					activarBotonesInsertUpdate();
					vista.btnUpdate.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Código de vuelo ya existe, modifique los campos que desee.");

				} else {
					if (!vista.tFCodAero.getText().equals("")) {
						vista.btnInsert.setEnabled(true);
						activarBotonesInsertUpdate();
						vista.btnDelete.setEnabled(false);
						JOptionPane.showMessageDialog(null, "Código de vuelo no existente.");
					} else {
						vista.tFCodAero.requestFocus();
					}
				}
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			}

		}
	}

	// Método Listener Insertar aero en T_AEROPUERTOS de MySQL:
	private class BtnInsertMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				DTOAeropuerto aero = guardaAero();
				dao = new DAOAeropuertos();

				if (dao.insert(aero)) {
					JOptionPane.showMessageDialog(null, "Aeropuerto insertado correctamente.");
					clear();
					reseteoBotones();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "No es posible crear aeropuerto.", "Alert",
						JOptionPane.WARNING_MESSAGE);
				clear();
			}
		}
	}

	// Método para actualizar campos en T_AEROPUERTOS de MySQL:
	private class BtnUpdateMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				DTOAeropuerto aero = guardaAero();
				dao = new DAOAeropuertos();

				if (dao.update(aero)) {
					System.out.println("HOLA");
					JOptionPane.showMessageDialog(null, "Aeropuerto actualizado correctamente.");
					clear();
					reseteoBotones();
				}
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			}
		}
	}

	// Método para eliminar una key en T_AEROPUERTOS de MySQL:
	private class BtnDeleteMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				dao = new DAOAeropuertos();
				String codAero = vista.tFCodAero.getText().trim().toUpperCase();

				int n = JOptionPane.showConfirmDialog(null,
						"Se va a eliminar el aeropuerto \"" + codAero.toUpperCase() + "\". ¿Está seguro?",
						"Confirm delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				if (n == 0) {
					if (dao.delete(codAero)) {
						JOptionPane.showMessageDialog(null, "Aeropuerto eliminado correctamente");
						clear();
					} else {
						JOptionPane.showMessageDialog(null,
								"Código de aeropuerto \"" + codAero + "\" no existe en BDD, introduzca de nuevo.",
								"Alert", JOptionPane.WARNING_MESSAGE);
						clear();
					}
				}
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
		}
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

	/*
	 * MÉTODOS PARA GUARDAR AEROPUERTOS,RESETEAR CAMPOS...
	 */
	// Método para guardar todo el texto introducido en los JTextField en variables
	private DTOAeropuerto guardaAero() {
		DTOAeropuerto aero = null;

		String codAero = vista.tFCodAero.getText().trim().toUpperCase();
		String nombreAero = vista.tFNombreAero.getText().trim().toUpperCase();

		try {
			aero = new DTOAeropuerto(codAero, nombreAero);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return aero;
	}

	// Métodos auxiliares para resetear campos:
	private void clear() {
		vista.tFCodAero.setEnabled(true);
		vista.tFCodAero.setText(null);
		vista.tFCodAero.requestFocus();
		vista.tFNombreAero.setText(null);
		reseteoBotones();
	}

	private void reseteoBotones() {
		vista.btnSearch.setEnabled(true);
		vista.btnInsert.setEnabled(false);
		vista.btnUpdate.setEnabled(false);
		vista.btnDelete.setEnabled(false);
		vista.tFCodAero.setEditable(true);
		vista.tFNombreAero.setEditable(false);

	}

	private void activarBotonesInsertUpdate() {
		vista.tFCodAero.setEnabled(false);
		vista.btnSearch.setEnabled(false);
		vista.btnDelete.setEnabled(true);
		vista.tFNombreAero.setEditable(true);
	}

	private void recogerDatosVuelo(DTOAeropuerto aero) {
		vista.tFCodAero.setText(aero.getCodAero());
		vista.tFNombreAero.setText(aero.getNombre());
	}
}