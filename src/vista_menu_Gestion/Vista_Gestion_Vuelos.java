package vista_menu_Gestion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exceptions.ExceptionVuelo;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

@SuppressWarnings("serial")
public class Vista_Gestion_Vuelos extends JFrame {

	public JPanel contentPane;
	public JPanel panelBtn;
	public JButton btnInsert;
	public JButton btnUpdate;
	public JPanel panelTítulo;
	public JLabel lblMenu;
	public JPanel panelPrincipal;
	public JLabel lblCod_Vuelo;
	public JLabel lblCod_Aeropuerto;
	public JLabel lblDestinoDelVuelo;
	public JTextField tFCodVuelo;
	public JTextField tFDestinoVuelo;
	public JTextField tFFechaVuelo;
	public JTextField tFPrecio;
	public JTextField tFNumPlazas;
	public JTextField tFNumPasajeros;
	public JLabel lblFechaDelVuelo;
	public JLabel lblPrecio;
	public JLabel lblNmeroDePlazas;
	public JLabel lblNmeroDePasajeros;
	public JButton btnExit;
	public JComboBox<String> comboBoxCodAerop;
	public JButton btnClear;
	public JMenuBar menuBarra;
	public JMenu mnConsultas;
	public JMenuItem mntmConsultasVuelos;
	public JMenuItem mntmConsultasAeropuertos;
	public JMenuItem mntmConsultasPersonalizadas;
	public JMenu mnFicheros;
	public JMenuItem mntmActualizarCrearLeer;
	public JButton btnSearch;
	public JButton btnDelete;
	public JMenu mnGestion;
	public JMenuItem mntmGestionAeropuertos;
	public JMenuItem mntmGestionVuelos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new controlador.Controlador_Gestion_Vuelos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vista_Gestion_Vuelos() {
		setTitle("Aeropuerto");
		setSize(493, 420);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 450);

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

		panelBtn = new JPanel();
		panelBtn.setBounds(5, 284, 703, 113);
		contentPane.add(panelBtn);
		panelBtn.setLayout(null);

		btnInsert = new JButton("Insertar");
		btnInsert.setBounds(165, 21, 105, 25);
		panelBtn.add(btnInsert);

		btnUpdate = new JButton("Modificar");
		btnUpdate.setBounds(285, 21, 105, 25);
		panelBtn.add(btnUpdate);

		btnExit = new JButton("Salir");
		btnExit.setBackground(new Color(240, 128, 128));
		btnExit.setBounds(543, 21, 100, 25);
		panelBtn.add(btnExit);

		btnSearch = new JButton("Buscar");
		btnSearch.setBounds(40, 21, 105, 25);
		panelBtn.add(btnSearch);

		btnDelete = new JButton("Eliminar");
		btnDelete.setBounds(405, 21, 105, 25);
		panelBtn.add(btnDelete);

		panelTítulo = new JPanel();
		panelTítulo.setBounds(5, 5, 703, 25);
		contentPane.add(panelTítulo);

		lblMenu = new JLabel("Gestión de vuelos en MySQL");
		panelTítulo.add(lblMenu);

		panelPrincipal = new JPanel();
		panelPrincipal.setBounds(5, 30, 703, 250);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);

		lblCod_Vuelo = new JLabel("Código vuelo:");
		lblCod_Vuelo.setBounds(41, 12, 107, 15);
		panelPrincipal.add(lblCod_Vuelo);

		lblCod_Aeropuerto = new JLabel("Código aeropuerto:");
		lblCod_Aeropuerto.setBounds(41, 42, 150, 15);
		panelPrincipal.add(lblCod_Aeropuerto);

		lblDestinoDelVuelo = new JLabel("Destino del vuelo:");
		lblDestinoDelVuelo.setBounds(41, 72, 150, 15);
		panelPrincipal.add(lblDestinoDelVuelo);

		tFCodVuelo = new JTextField();
		tFCodVuelo.setBounds(346, 12, 297, 19);
		panelPrincipal.add(tFCodVuelo);
		tFCodVuelo.setColumns(10);

		tFDestinoVuelo = new JTextField();
		tFDestinoVuelo.setBounds(346, 72, 297, 19);
		panelPrincipal.add(tFDestinoVuelo);
		tFDestinoVuelo.setColumns(10);

		tFFechaVuelo = new JTextField();
		tFFechaVuelo.setBounds(346, 102, 297, 19);
		panelPrincipal.add(tFFechaVuelo);
		tFFechaVuelo.setColumns(10);

		tFPrecio = new JTextField();
		tFPrecio.setBounds(346, 132, 297, 19);
		panelPrincipal.add(tFPrecio);
		tFPrecio.setColumns(10);

		tFNumPlazas = new JTextField();
		tFNumPlazas.setBounds(346, 162, 297, 19);
		panelPrincipal.add(tFNumPlazas);
		tFNumPlazas.setColumns(10);

		tFNumPasajeros = new JTextField();
		tFNumPasajeros.setBounds(346, 192, 297, 19);
		panelPrincipal.add(tFNumPasajeros);
		tFNumPasajeros.setColumns(10);

		lblFechaDelVuelo = new JLabel("Fecha del vuelo (yyyy-MM-dd):");
		lblFechaDelVuelo.setBounds(41, 102, 222, 15);
		panelPrincipal.add(lblFechaDelVuelo);

		lblPrecio = new JLabel("Precio / €");
		lblPrecio.setBounds(41, 132, 70, 15);
		panelPrincipal.add(lblPrecio);

		lblNmeroDePlazas = new JLabel("Número de plazas:");
		lblNmeroDePlazas.setBounds(41, 162, 150, 15);
		panelPrincipal.add(lblNmeroDePlazas);

		lblNmeroDePasajeros = new JLabel("Número de pasajeros:");
		lblNmeroDePasajeros.setBounds(41, 192, 208, 15);
		panelPrincipal.add(lblNmeroDePasajeros);

		comboBoxCodAerop = new JComboBox<String>();
		comboBoxCodAerop.setBounds(346, 37, 300, 24);
		panelPrincipal.add(comboBoxCodAerop);

		btnClear = new JButton("Reset");
		btnClear.setBounds(543, 223, 100, 25);
		panelPrincipal.add(btnClear);
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