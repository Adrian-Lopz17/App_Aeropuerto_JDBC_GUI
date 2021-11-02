package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import exceptions.ExceptionVuelo;
import modelo.DAOVuelos;
import modelo.DTOVuelo;
import vista_menu_Ficheros.Vista_Ficheros;

public class Controlador_Ficheros {
	private Vista_Ficheros vista;
	private DAOVuelos dao;

	public Controlador_Ficheros() throws SQLException {
		super();
		this.vista = new Vista_Ficheros();
		this.dao = new DAOVuelos();
		asociaListeners();
		clear();
		this.vista.setVisible(true);
		this.vista.setLocationRelativeTo(null);
	}

	private void asociaListeners() {
		vista.btnExecuteQuery.addMouseListener(new BtnExecuteQueryMouseListener());
		vista.btnUpdate.addMouseListener(new BtnUpdateMouseListener());
		vista.btnCreate.addMouseListener(new BtnCreate_1MouseListener());
		vista.btnAbrir.addMouseListener(new BtnAbrirMouseListener());
		vista.btnClear.addMouseListener(new BtnClearMouseListener());
	}

	/*
	 * LISTENERS
	 */
	// Método Listener para ejecutar las consultas introducidas en los JTextField:
	private class BtnExecuteQueryMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				if (!vista.textAreaConsulta.getText().equals(""))
					querySQL();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
		}
	}

	private class BtnAbrirMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			vista.fileChooser.setFileFilter(vista.filtroTxt);
			int seleccion = vista.fileChooser.showOpenDialog(vista.textAreaRead);

			if (seleccion == JFileChooser.APPROVE_OPTION) {
				clear();
				File fichero = vista.fileChooser.getSelectedFile();
				leerFicheros(fichero);
			}
		}
	}

	private class BtnUpdateMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				actualizarFicheroDestinos();
			} catch (Exception e2) {
				System.err.println(e2.getMessage());
			}

		}
	}

	private class BtnCreate_1MouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			crearFicheroFacturacionAero();
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
	 * MÉTODOS PARA EJECUTAR CONSULTAS, ACTUALIZAR/CREAR FICHEROS, RESETEAR CAMPOS
	 */

	private void querySQL() throws SQLException {
		try {
			dao = new DAOVuelos();
			String sql = vista.textAreaConsulta.getText().trim().toUpperCase();

			if (!sql.equals("") && !sql
					.equals("Introduce una consulta personalizada en \"consultas_personales.txt\"".toUpperCase())) {
				List<DTOVuelo> vuelos = dao.readSQLVuelo(sql);
				if (vuelos.size() > 0) {
					File fichero = new File("consultas_personales.txt");
					ficheroConsultasPersonalizadas(fichero, vuelos, sql);

				} else {
					JOptionPane.showMessageDialog(null, "No se han encontrado resultados con tu consulta.", "Alert",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se han encontrado resultados con tu consulta.", "Alert",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	private void ficheroConsultasPersonalizadas(File fichero, List<DTOVuelo> vuelos, String sql) {
		try (FileWriter fw = new FileWriter(fichero, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(fw);) {
			pintarFechasFicheros(pw);
			pw.println("Consulta introducida: " + sql);
			for (DTOVuelo vuelo : vuelos) {
				String linea = vuelo.toString();
				pw.print(linea);
			}
			JOptionPane.showMessageDialog(null, "Fichero " + fichero.getName() + " actualizado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void actualizarFicheroDestinos() throws SQLException {
		String sql = "SELECT * FROM T_VUELOS";
		try {
			dao = new DAOVuelos();
			List<DTOVuelo> vuelos = dao.readSQLVuelo(sql);
			if (vuelos.size() > 0) {
				File fichero = new File("destinos_Vuelos.txt");
				Set<String> conjunto = new HashSet<String>();
				for (DTOVuelo vuelo : vuelos) {
					conjunto.add(vuelo.getDestino());
				}

				try (FileWriter fw = new FileWriter(fichero, true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(fw);) {
					pintarFechasFicheros(pw);
					int i = 1;
					for (String destino : conjunto) {
						String linea = destino;
						pw.println(i + ". " + linea);
						i++;
					}
					JOptionPane.showMessageDialog(null, "Fichero " + fichero.getName() + " actualizado.");
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}

			}
		} catch (ExceptionVuelo e) {
			JOptionPane.showMessageDialog(null, "No existe fichero.", "Alert", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void crearFicheroFacturacionAero() {
		File fichero = new File("facturacion_Aero.txt");
		List<String> facturacionCodAero = dao.facturacionVuelos();
		if (facturacionCodAero != null) {
			try (FileWriter fw = new FileWriter(fichero); PrintWriter pw = new PrintWriter(fw);) {
				for (String linea : facturacionCodAero) {
					pw.printf("%s\n", linea);
				}

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No existe el fichero introducido.", "Alert",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "No existen vuelos para calcular facturación.", "Alert",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void leerFicheros(File fichero) {
		try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr);) {
			String linea = br.readLine();
			while (linea != null) {
				vista.textAreaRead.append(linea + "\n");
				linea = br.readLine();
			}
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	private void pintarFechasFicheros(PrintWriter pw) {
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		int hora = fecha.get(Calendar.HOUR_OF_DAY);
		int minuto = fecha.get(Calendar.MINUTE);
		int segundo = fecha.get(Calendar.SECOND);
		pw.printf("\n%s-%s-%s, %s:%s:%s\n", dia, mes, anio, hora, minuto, segundo);
	}

	// Método para resetear campos
	private void clear() {
		vista.textAreaConsulta.setText("");
		vista.textAreaRead.setText("");
		vista.textAreaRead.setEditable(false);
	}

}