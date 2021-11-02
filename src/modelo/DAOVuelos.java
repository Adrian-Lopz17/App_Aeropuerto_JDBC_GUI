package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import exceptions.ExceptionVuelo;

// CRUD
public class DAOVuelos {
	static Connection conn;

	public DAOVuelos() throws SQLException {
		conn = Conexion.getConexion();
	}

	public boolean insert(DTOVuelo vuelo) {
		int n = 0;
		String INSERT = "INSERT INTO T_VUELOS VALUES (?,?,?,?,?,?,?)";

		try (PreparedStatement sentencia = conn.prepareStatement(INSERT);) {
			sentencia.setString(1, vuelo.getCodigo());
			sentencia.setString(2, vuelo.getCodigoAe());
			sentencia.setString(3, vuelo.getDestino());
			sentencia.setDate(4, (Date) vuelo.getFecha());
			sentencia.setDouble(5, vuelo.getPrecio());
			sentencia.setInt(6, vuelo.getNumPlazas());
			sentencia.setInt(7, vuelo.getNumPasajeros());

			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error de inserción: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
		}
		return n > 0 ? true : false;
	}

	public boolean delete(String key) {
		int n = 0;
		String DELETE = "DELETE FROM T_VUELOS WHERE CODIGO_VUELO=?";

		try (PreparedStatement sentencia = conn.prepareStatement(DELETE);) {
			sentencia.setString(1, key);
			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error al eliminar: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
		}
		return n > 0 ? true : false;
	}

	public boolean update(DTOVuelo vuelo) {
		int n = 0;
		String UPDATE = "UPDATE T_VUELOS SET CODIGO_AEROPUERTO=?, DESTINO_VUELO=?, FECHA_VUELO=?, PRECIO_VUELO=?, NUMERO_PLAZAS_VUELO=?, NUMERO_PASAJEROS_VUELO=? WHERE CODIGO_VUELO=?";
		try (PreparedStatement sentencia = conn.prepareStatement(UPDATE);) {
			sentencia.setString(7, vuelo.getCodigo());
			sentencia.setString(1, vuelo.getCodigoAe());
			sentencia.setString(2, vuelo.getDestino());
			sentencia.setDate(3, (Date) vuelo.getFecha());
			sentencia.setDouble(4, vuelo.getPrecio());
			sentencia.setInt(5, vuelo.getNumPlazas());
			sentencia.setInt(6, vuelo.getNumPasajeros());

			n = sentencia.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, new SQLException("Error de inserción: " + e.getMessage()), "Alert",
					JOptionPane.WARNING_MESSAGE);
			// throw new SQLException("Error de inserción: " + e.getMessage());
		}
		return n > 0 ? true : false;
	}

	// Método para rellenar JComboBox codAeropuertos:
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox llenarComboBox(JComboBox comboBox) throws SQLException, ExceptionVuelo {
		String READALL = "SELECT CODIGO_AEROPUERTO FROM T_AEROPUERTOS";

		try (PreparedStatement sentencia = conn.prepareStatement(READALL); ResultSet rs = sentencia.executeQuery();) {
			while (rs.next())
				comboBox.addItem(rs.getObject("CODIGO_AEROPUERTO"));
		}
		return comboBox;
	}

	// Método para realizar consulta por código de vuelo en VISTA_GESTION_VUELOS:
	public DTOVuelo consultaPorCodVuelo(String key) throws SQLException, ExceptionVuelo {
		String CONSULTA = "SELECT * FROM T_VUELOS WHERE CODIGO_VUELO=?";
		try (PreparedStatement sentencia = conn.prepareStatement(CONSULTA);) {
			sentencia.setString(1, key);
			try (ResultSet rs = sentencia.executeQuery();) {
				if (rs.next())
					return creaVuelo(rs);
			} catch (Exception e) {

			}
		}
		return null;
	}

	// Método para realizar consulta de todos los vuelos en T_VUELOS:
	public List<DTOVuelo> consultaAllVuelos() throws SQLException, ExceptionVuelo {
		String CONSULTA = "SELECT * FROM T_VUELOS ORDER BY FECHA_VUELO ASC";
		List<DTOVuelo> lista = new LinkedList<DTOVuelo>();
		try (PreparedStatement sentencia = conn.prepareStatement(CONSULTA); ResultSet rs = sentencia.executeQuery();) {
			while (rs.next())
				lista.add(creaVuelo(rs));

			return lista;
		}
	}

	// Método constructor a partir de ResulSet para DTOVuelo
	private DTOVuelo creaVuelo(ResultSet rs) throws SQLException, ExceptionVuelo {
		return new DTOVuelo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDouble(5),
				rs.getInt(6), rs.getInt(7));
	}

	// ***********************************************************************
	// LLENAR JTABLE, proporciono informacion a un JTable para llenarse

	public ResultSet readRS() throws SQLException {
		String READALL = "SELECT * FROM T_VUELOS";
		ResultSet rs = null;
		PreparedStatement sentencia = conn.prepareStatement(READALL, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		rs = sentencia.executeQuery();
		return rs;
	}

	public ResultSet readSQLVueloRS(String sql) throws SQLException {
		ResultSet rs = null;
		PreparedStatement sentencia = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		rs = sentencia.executeQuery();
		return rs;
	}

	public ResultSet consultasAnidadasRS(String codVuelo, String codAero, String destino, String fecha)
			throws SQLException {
		String CONSULTA = "SELECT * FROM T_VUELOS";
		ResultSet rs = null;

		Map<String, Object> map = new HashMap<String, Object>();
		if (!codVuelo.equals(""))
			map.put("CODIGO_VUELO=", codVuelo);
		if (!codAero.equals(""))
			map.put("NUMERO_PLAZAS_VUELO = NUMERO_PASAJEROS_VUELO AND CODIGO_AEROPUERTO=", codAero);
		if (!destino.equals(""))
			map.put("DESTINO_VUELO=", destino);
		if (!fecha.equals(""))
			map.put("FECHA_VUELO=", fecha);

		int cont = map.size();

		if (cont == 1) {
			CONSULTA += " WHERE ";
			for (String textoConsulta : map.keySet()) {
				// damos texto de consulta correspondiente
				CONSULTA += textoConsulta;
				// damos valor (codVuelo, destinno, etc...)
				CONSULTA += "\"" + map.get(textoConsulta) + "\"";
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

	// Método que lee una sentencia completo introducida por teclado para T_VUELOS:
	public List<DTOVuelo> readSQLVuelo(String sql) throws SQLException, ExceptionVuelo {
		List<DTOVuelo> lista = null;
		try (PreparedStatement sentencia = conn.prepareStatement(sql); ResultSet rs = sentencia.executeQuery();) {
			lista = new LinkedList<DTOVuelo>();
			while (rs.next())
				lista.add(creaVuelo(rs));
		}
		return lista;
	}

	// Método para hacer consulta de la facturación total de todos los vuelos:
	public List<String> facturacionVuelos() {
		List<String> facturacionCodAero = null;
		String CONSULTA = "SELECT CODIGO_AEROPUERTO, SUM(PRECIO_VUELO*NUMERO_PASAJEROS_VUELO) FROM T_VUELOS GROUP BY CODIGO_AEROPUERTO";
		try (PreparedStatement sentencia = conn.prepareStatement(CONSULTA); ResultSet rs = sentencia.executeQuery();) {
			facturacionCodAero = new LinkedList<String>();
			while (rs.next()) {
				facturacionCodAero.add(
						"Código aeropuerto: " + rs.getString(1) + ", facturación total = " + rs.getDouble(2) + "€");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return facturacionCodAero;
	}
}