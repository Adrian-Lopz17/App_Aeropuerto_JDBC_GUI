package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

// CRUD
public class DAOAeropuertos {
	static Connection conn;

	public DAOAeropuertos() throws SQLException {
		conn = Conexion.getConexion();
	}

	public boolean insert(DTOAeropuerto vuelo) {
		int n = 0;
		String INSERT = "INSERT INTO T_AEROPUERTOS VALUES (?,?)";

		try (PreparedStatement sentencia = conn.prepareStatement(INSERT);) {
			sentencia.setString(1, vuelo.getCodAero());
			sentencia.setString(2, vuelo.getNombre());

			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error de inserción: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
		}
		return n > 0 ? true : false;
	}

	public boolean delete(String key) {
		int n = 0;
		String DELETE = "DELETE FROM T_AEROPUERTOS WHERE CODIGO_AEROPUERTO=?";

		try (PreparedStatement sentencia = conn.prepareStatement(DELETE);) {
			sentencia.setString(1, key);
			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error al eliminar: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
		}
		return n > 0 ? true : false;
	}

	public boolean update(DTOAeropuerto vuelo) {
		int n = 0;
		String UPDATE = "UPDATE T_AEROPUERTOS SET NOMBRE_AEROPUERTO=? WHERE CODIGO_AEROPUERTO=?";
		try (PreparedStatement sentencia = conn.prepareStatement(UPDATE);) {
			sentencia.setString(1, vuelo.getNombre());
			sentencia.setString(2, vuelo.getCodAero());

			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error al modificar: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
			// throw new SQLException("Error de inserción: " + e.getMessage());
		}
		return n > 0 ? true : false;
	}

	// Método para hacer consulta de los aeropuertos por códAeropuerto:
	public DTOAeropuerto consultaPorCodigoAero(String codAero) throws SQLException {
		// Ej: SELECT * FROM T_VUELOS WHERE CODIGO_VUELO="ABC123"
		String CONSULTA = "SELECT * FROM T_AEROPUERTOS WHERE CODIGO_AEROPUERTO = ?";

		try (PreparedStatement sentencia = conn.prepareStatement(CONSULTA);) {
			sentencia.setString(1, codAero);
			try {
				ResultSet rs = sentencia.executeQuery();
				if (rs.next())
					return creaAero(rs);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}

	// Método constructor a partir de ResulSet para DTOAeropuerto
	private DTOAeropuerto creaAero(ResultSet rs) throws SQLException {
		return new DTOAeropuerto(rs.getString(1), rs.getString(2));
	}

	// ***********************************************************************
	// LLENAR JTABLE, proporciono informacion a un JTable para llenarse

	public ResultSet readRS() throws SQLException {
		String READALL = "SELECT * FROM T_AEROPUERTOS";
		ResultSet rs = null;
		PreparedStatement sentencia = conn.prepareStatement(READALL, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		rs = sentencia.executeQuery();
		return rs;
	}

	public ResultSet consultasAnidadasRS(String codAero, String nombre) throws SQLException {
		String CONSULTA = "SELECT * FROM T_AEROPUERTOS";
		ResultSet rs = null;

		Map<String, Object> map = new HashMap<String, Object>();
		if (!codAero.equals(""))
			map.put("CODIGO_AEROPUERTO=", codAero);
		if (!nombre.equals(""))
			map.put("NOMBRE_AEROPUERTO=", nombre);
		int cont = map.size();

		if (cont == 1) {
			CONSULTA += " WHERE ";
			for (String consultaObject : map.keySet()) {
				CONSULTA += consultaObject;
				CONSULTA += "\"" + map.get(consultaObject) + "\"";
			}
		} else if (cont > 1) {
			int contador = cont;
			CONSULTA += " WHERE ";
			for (String consultaObject : map.keySet()) {
				CONSULTA += consultaObject;
				CONSULTA += "\"" + map.get(consultaObject) + "\"";
				contador--;
				if (contador > 0)
					CONSULTA += " AND ";
			}
		}
		PreparedStatement sentencia = conn.prepareStatement(CONSULTA, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rs = sentencia.executeQuery();
		return rs;
	}

	public ResultSet readSQLAeroRS(String sql) throws SQLException {
		ResultSet rs = null;
		PreparedStatement sentencia = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		rs = sentencia.executeQuery();
		return rs;
	}
}