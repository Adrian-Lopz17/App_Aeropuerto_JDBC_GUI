package modelo;

public interface Datos_Conexion {
	String bd = "BD_AEROPUERTO";
	String usuario = "root";
	String clave = "";
	String url = "jdbc:mariadb://localhost/" + bd;
	String driver = "org.mariadb.jdbc.Driver";

	String url1 = "jdbc:mysql://localhost/" + bd;
	String driver1 = "com.mysql.jdbc.Driver";
}