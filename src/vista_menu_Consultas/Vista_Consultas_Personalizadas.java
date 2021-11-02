package vista_menu_Consultas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.DAOVuelos;

import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exceptions.ExceptionVuelo;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Vista_Consultas_Personalizadas extends JDialog {

	public DAOVuelos dao;
	public JPanel contentPane;
	public JTable table;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel lblIntroduceTuConsulta;
	public JButton btnConsultar;
	public JButton btnClear;
	public JMenuBar menuBarra;
	public JMenu mnConsultas;
	public JMenu mnFicheros;
	public JMenu mnGestion;
	public JMenuItem mntmGestionAeropuertos;
	public JMenuItem mntmGestionVuelos;
	public JMenuItem mntmConsultasVuelos;
	public JMenuItem mntmConsultasAeropuertos;
	public JMenuItem mntmConsultasPersonalizadas;
	public JMenuItem mntmActualizarCrearLeer;
	public JScrollPane scrollPaneConsulta;
	public JTextArea textAreaConsulta;

	/**
	 * Create the frame.
	 */
	public Vista_Consultas_Personalizadas() {
		setTitle("Aeropuerto");
		setSize(493, 420);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 510);

		menuBarra = new JMenuBar();
		setJMenuBar(menuBarra);

		mnGestion = new JMenu("Gestión");
		menuBarra.add(mnGestion);

		mntmGestionVuelos = new JMenuItem("Vuelos");
		mntmGestionVuelos.addMouseListener(new MntmGestionVuelosMouseListener());
		mnGestion.add(mntmGestionVuelos);

		mntmGestionAeropuertos = new JMenuItem("Aeropuertos");
		mntmGestionAeropuertos.addMouseListener(new MntmGestionAeropuertosMouseListener());
		mnGestion.add(mntmGestionAeropuertos);

		mnConsultas = new JMenu("Consultas");
		menuBarra.add(mnConsultas);

		mntmConsultasVuelos = new JMenuItem("Consultas vuelos");
		mntmConsultasVuelos.addMouseListener(new MntmConsultasVuelosMouseListener());
		mnConsultas.add(mntmConsultasVuelos);

		mntmConsultasAeropuertos = new JMenuItem("Consultas aeropuertos");
		mntmConsultasAeropuertos.addMouseListener(new MntmConsultasAeropuertosMouseListener());
		mnConsultas.add(mntmConsultasAeropuertos);

		mntmConsultasPersonalizadas = new JMenuItem("Consultas personalizadas");
		mntmConsultasPersonalizadas.addMouseListener(new MntmConsultasPersonalizadasMouseListener());
		mnConsultas.add(mntmConsultasPersonalizadas);

		mnFicheros = new JMenu("Ficheros");
		menuBarra.add(mnFicheros);

		mntmActualizarCrearLeer = new JMenuItem("Actualizar, crear, leer ficheros");
		mntmActualizarCrearLeer.addMouseListener(new MntmActualizarCrearLeerMouseListener());
		mnFicheros.add(mntmActualizarCrearLeer);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 166, 696, 279);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "CODIGO_VUELO", "CODIGO_AEROPUERTO",
				"DESTINO_VUELO", "FECHA_VUELO", "PRECIO_VUELO", "NUMERO_PLAZAS_VUELO", "NUMERO_PASAJEROS_VUELO" }));
		scrollPane.setViewportView(table);

		panel = new JPanel();
		panel.setBounds(12, 0, 696, 154);
		contentPane.add(panel);
		panel.setLayout(null);

		lblIntroduceTuConsulta = new JLabel("Introduce tu consulta personalizada:");
		lblIntroduceTuConsulta.setBounds(221, 12, 317, 15);
		panel.add(lblIntroduceTuConsulta);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(301, 129, 105, 25);
		panel.add(btnConsultar);

		btnClear = new JButton("Reset");
		btnClear.setBounds(591, 129, 105, 25);
		panel.add(btnClear);
		
		scrollPaneConsulta = new JScrollPane();
		scrollPaneConsulta.setBounds(0, 39, 696, 83);
		panel.add(scrollPaneConsulta);
		
		textAreaConsulta = new JTextArea();
		scrollPaneConsulta.setViewportView(textAreaConsulta);

	}

	// Métodos Listener para pulsar y abrir otro menú:
	private class MntmGestionVuelosMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_Gestion_Vuelos();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ExceptionVuelo e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmGestionAeropuertosMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_Gestion_Aeropuertos();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmConsultasVuelosMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_Consultas_Vuelos();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmConsultasAeropuertosMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_Consultas_Aeropuertos();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmActualizarCrearLeerMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_Ficheros();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmConsultasPersonalizadasMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				new controlador.Controlador_ConsultasPersonalizadas();
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}