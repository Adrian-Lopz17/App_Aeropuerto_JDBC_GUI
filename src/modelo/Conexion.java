package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

// patrón singleton (aquella clase que solo permite crear una instancia, un unico objeto)
public class Conexion implements Datos_Conexion{
	static private Connection conexion = null;
	
	// Constructor privado con patron singleton
	private Conexion() throws SQLException {
		// Captura en vez de arroja, porque si no arroja constantemente
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Conexión fallida: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
			//System.err.println("Conexión fallida.");
			e.printStackTrace();
		}
		
		// aqui si arrojo
		Conexion.conexion = DriverManager.getConnection(url, usuario, clave);
	}
	
	// hemos establecido conexion? NO: creala SI:devuelvela
	public static Connection getConexion() throws SQLException {
		if(conexion == null)
			new Conexion();
		System.out.println("Conexión establecida");
		return conexion;
	}
	
	// Método cierre conexión
	public static void cierra() throws SQLException {
		if(conexion != null)
			conexion.close();
		conexion = null;
		System.out.println("Se ha cerrado la conexión");
	}
	
}