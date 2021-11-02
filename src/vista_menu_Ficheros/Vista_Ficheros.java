package vista_menu_Ficheros;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import exceptions.ExceptionVuelo;

@SuppressWarnings("serial")
public class Vista_Ficheros extends JDialog {

	public JPanel contentPane;
	public JScrollPane scrollPane;
	public JPanel PanelPrincipal;
	public JTextArea textAreaRead;
	public JLabel lblTitulo;
	public JLabel lblConsultarAeroCod;
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
	public JMenuItem mntmConsultasPersonalizadas;
	public JLabel lblCreaFicheroregistrocajatxt;
	public JButton btnUpdate;
	public JButton btnCreate;
	public JButton btnAbrir;
	public JMenuItem mntmActualizarCrearLeer;
	public JFileChooser fileChooser;
	public FiltroDeTxt filtroTxt;
	public JScrollPane scrollPaneConsulta;
	public JTextArea textAreaConsulta;

	/**
	 * Create the frame.
	 */
	public Vista_Ficheros() {
		setTitle("Aeropuerto");
		setSize(493, 420);
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

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 238, 698, 207);
		getContentPane().add(scrollPane);

		textAreaRead = new JTextArea();
		scrollPane.setViewportView(textAreaRead);

		PanelPrincipal = new JPanel();
		PanelPrincipal.setBounds(0, 0, 720, 243);
		getContentPane().add(PanelPrincipal);
		PanelPrincipal.setLayout(null);

		lblTitulo = new JLabel("Ficheros de texto");
		lblTitulo.setBounds(281, 15, 273, 15);
		PanelPrincipal.add(lblTitulo);

		lblConsultarAeroCod = new JLabel("Actualiza el fichero \"destinos_Vuelos.txt\" a fecha de hoy:");
		lblConsultarAeroCod.setBounds(22, 46, 460, 15);
		PanelPrincipal.add(lblConsultarAeroCod);

		lblCreaFicheroregistrocajatxt = new JLabel(
				"Crea fichero \"facturacion_Aero.txt\" (facturación por aeropuerto):");
		lblCreaFicheroregistrocajatxt.setBounds(22, 83, 488, 15);
		PanelPrincipal.add(lblCreaFicheroregistrocajatxt);

		btnExecuteQuery = new JButton("Ejecutar consulta personalizada");
		btnExecuteQuery.setBounds(12, 206, 297, 25);
		PanelPrincipal.add(btnExecuteQuery);

		btnUpdate = new JButton("Actualizar");
		btnUpdate.setBounds(591, 41, 117, 25);
		PanelPrincipal.add(btnUpdate);

		btnCreate = new JButton("Crear");
		btnCreate.setBounds(591, 78, 117, 25);
		PanelPrincipal.add(btnCreate);

		btnClear = new JButton("Reset");
		btnClear.setBounds(591, 206, 117, 25);
		PanelPrincipal.add(btnClear);

		btnAbrir = new JButton("Abrir fichero");
		btnAbrir.setBounds(430, 206, 149, 25);
		PanelPrincipal.add(btnAbrir);
		
		scrollPaneConsulta = new JScrollPane();
		scrollPaneConsulta.setBounds(12, 110, 696, 84);
		PanelPrincipal.add(scrollPaneConsulta);
		
		textAreaConsulta = new JTextArea();
		scrollPaneConsulta.setViewportView(textAreaConsulta);

		fileChooser = new JFileChooser();
		filtroTxt = new FiltroDeTxt();
	}

	// Clase interna que hereda de FileFilter para filtrar archivos.txt
	public class FiltroDeTxt extends FileFilter {
		public boolean accept(File fichero) {
			if (tieneExtensionTXT(fichero))
				return true;
			else
				return false;
		}

		private boolean tieneExtensionTXT(File file) {
			final String fil = file.getName();
			if (file.isDirectory())
				return true;
			else
				return (fil.endsWith(".txt"));
		}

		public String getDescription() {
			return (".txt");
		}
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