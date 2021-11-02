package vista_menu_Consultas;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.ExceptionVuelo;
import modelo.DAOVuelos;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Vista_Consultas_Vuelos extends JDialog {

	public DAOVuelos dao;
	public JPanel contentPane;
	public JPanel PanelPrincipal;
	public JLabel lblConsultarTodos;
	public JLabel lblConsultarVuelosCod;
	public JLabel lblConsultarVuelosCon;
	public JLabel lblConsultarVuelosLlenos;
	public JTextField tFConsultaCodVuelo;
	public JTextField tFConsultaDestino;
	public JTextField tFConsultaOcupados;
	public JLabel lblConsultarVueloPor;
	public JTextField tFConsultaFecha;
	public JButton btnExecuteQuery;
	public JButton btnClear;
	public JMenuBar menuBarra;
	public JMenu mnConsultas;
	public JMenu mnFicheros;
	public JMenu mnGestion;
	public JMenuItem mntmGestionAeropuertos;
	public JMenuItem mntmGestionVuelos;
	public JMenuItem mntmConsultasVuelos;
	public JMenuItem mntmConsultasAeropuertos;
	public JMenuItem mntmActualizarCrearLeer;
	public JButton btnAll;
	public JTable table;
	public JScrollPane scrollPane;
	public JMenuItem mntmConsultasPersonalizadas;

	/**
	 * Create the frame.
	 */
	public Vista_Consultas_Vuelos() {
		setTitle("Aeropuerto");
		setSize(720, 510);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 510);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		PanelPrincipal = new JPanel();
		PanelPrincipal.setBounds(12, 0, 696, 200);
		getContentPane().add(PanelPrincipal);
		PanelPrincipal.setLayout(null);

		lblConsultarTodos = new JLabel("Consultas a MySQL sobre \"T_VUELOS\"");
		lblConsultarTodos.setBounds(215, 15, 312, 15);
		PanelPrincipal.add(lblConsultarTodos);
		
				btnExecuteQuery = new JButton("Consultar");
				btnExecuteQuery.setBounds(275, 175, 153, 25);
				PanelPrincipal.add(btnExecuteQuery);
		
				btnClear = new JButton("Reset");
				btnClear.setBounds(591, 147, 105, 25);
				PanelPrincipal.add(btnClear);
				btnClear.addActionListener(new BtnClearActionListener());
		
				btnAll = new JButton("Mostrar todos");
				btnAll.setBounds(0, 175, 144, 25);
				PanelPrincipal.add(btnAll);

		lblConsultarVuelosCod = new JLabel("Consultar vuelo por código:");
		lblConsultarVuelosCod.setBounds(12, 44, 292, 15);
		PanelPrincipal.add(lblConsultarVuelosCod);

		lblConsultarVuelosCon = new JLabel("Consultar vuelos por destino: ");
		lblConsultarVuelosCon.setBounds(12, 69, 257, 15);
		PanelPrincipal.add(lblConsultarVuelosCon);

		lblConsultarVuelosLlenos = new JLabel("Consultar vuelos llenos por codAeropuerto:");
		lblConsultarVuelosLlenos.setBounds(12, 98, 332, 15);
		PanelPrincipal.add(lblConsultarVuelosLlenos);

		tFConsultaCodVuelo = new JTextField();
		tFConsultaCodVuelo.setBounds(362, 42, 334, 19);
		PanelPrincipal.add(tFConsultaCodVuelo);
		tFConsultaCodVuelo.setColumns(10);

		tFConsultaDestino = new JTextField();
		tFConsultaDestino.setText("");
		tFConsultaDestino.setBounds(362, 67, 334, 19);
		PanelPrincipal.add(tFConsultaDestino);
		tFConsultaDestino.setColumns(10);

		tFConsultaOcupados = new JTextField();
		tFConsultaOcupados.setBounds(362, 96, 334, 19);
		PanelPrincipal.add(tFConsultaOcupados);
		tFConsultaOcupados.setColumns(10);

		lblConsultarVueloPor = new JLabel("Consultar vuelos por fecha (yyyy-MM-dd):");
		lblConsultarVueloPor.setBounds(12, 123, 312, 15);
		PanelPrincipal.add(lblConsultarVueloPor);

		tFConsultaFecha = new JTextField();
		tFConsultaFecha.setBounds(362, 121, 334, 19);
		PanelPrincipal.add(tFConsultaFecha);
		tFConsultaFecha.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 212, 696, 233);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CODIGO_VUELO", "CODIGO_AEROPUERTO", "DESTINO_VUELO", "FECHA_VUELO", "PRECIO_VUELO", "NUMERO_PLAZAS_VUELO", "NUMERO_PASAJEROS_VUELO"
			}
		));
		scrollPane.setViewportView(table);

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
	private class BtnClearActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
		}
	}
}