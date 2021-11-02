package vista_menu_Consultas;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.ExceptionVuelo;

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

@SuppressWarnings("serial")
public class Vista_Consultas_Aeropuertos extends JDialog {

	public JPanel contentPane;
	public JPanel PanelPrincipal;
	public JLabel lblConsultarTodos;
	public JLabel lblConsultarCod;
	public JLabel lblConsultarNombre;
	public JTextField tFConsultaCodAero;
	public JTextField tFConsultaNombre;
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
	public Vista_Consultas_Aeropuertos() {
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

		lblConsultarTodos = new JLabel("Consultas a MySQL sobre \"T_AEROPUERTOS\"");
		lblConsultarTodos.setBounds(215, 15, 312, 15);
		PanelPrincipal.add(lblConsultarTodos);

		lblConsultarCod = new JLabel("Consultar aeropuerto por código:");
		lblConsultarCod.setBounds(12, 44, 292, 15);
		PanelPrincipal.add(lblConsultarCod);

		lblConsultarNombre = new JLabel("Consultar aeropuerto por nombre: ");
		lblConsultarNombre.setBounds(12, 69, 257, 15);
		PanelPrincipal.add(lblConsultarNombre);

		tFConsultaCodAero = new JTextField();
		tFConsultaCodAero.setBounds(362, 42, 334, 19);
		PanelPrincipal.add(tFConsultaCodAero);
		tFConsultaCodAero.setColumns(10);

		tFConsultaNombre = new JTextField();
		tFConsultaNombre.setText("");
		tFConsultaNombre.setBounds(362, 67, 334, 19);
		PanelPrincipal.add(tFConsultaNombre);
		tFConsultaNombre.setColumns(10);

		btnExecuteQuery = new JButton("Consultar");
		btnExecuteQuery.setBounds(264, 163, 153, 25);
		PanelPrincipal.add(btnExecuteQuery);

		btnClear = new JButton("Reset");
		btnClear.setBounds(579, 98, 117, 25);
		PanelPrincipal.add(btnClear);

		btnAll = new JButton("Mostrar todos");
		btnAll.setBounds(0, 163, 141, 25);
		PanelPrincipal.add(btnAll);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 212, 696, 233);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CODIGO_AEROPUERTO", "NOMBRE_AEROPUERTO"
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
}