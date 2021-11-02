package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.swing.JOptionPane;

import exceptions.ExceptionDestino;
import exceptions.ExceptionNumPasajeros;
import exceptions.ExceptionNumPlazas;
import exceptions.ExceptionVuelo;

@SuppressWarnings("serial")
public class DTOVuelo implements Comparable<DTOVuelo>, Serializable {
	private String codVuelo;
	private String codAero;
	private String destino;
	private java.sql.Date fecha;
	private Double precio;
	private Integer numPlazas; // debe ser positivo
	private Integer numPasajeros; // debe ser positivo y menor de numPlazas

	// Constructor ORIGEN: Java DESTINO: MySQL
	public DTOVuelo(String codigo, String codigoAe, String destino, String fechaCadena, String precio, String numPlazas,
			String numPasajeros) throws ExceptionVuelo {
		this.codVuelo = codigo;
		this.codAero = codigoAe;
		setDestino(destino);
		this.destino = destino;
		setFecha(fechaCadena);
		setPrecio(precio);
		setNumPlazas(numPlazas);
		setNumPasajeros(numPasajeros);
	}

	// Constructor ORIGEN: MySQL DESTINO: Java
	public DTOVuelo(String codigo, String codigoAe, String destino, java.sql.Date fecha, Double precio,
			Integer numPlazas, Integer numPasajeros) throws ExceptionVuelo {
		this.codVuelo = codigo;
		this.codAero = codigoAe;
		this.destino = destino;
		this.fecha = fecha;
		this.precio = precio;
		this.numPlazas = numPlazas;
		this.numPasajeros = numPasajeros;
	}

	// Getters
	public String getDestino() {
		return destino;
	}

	public Double getPrecio() {
		return precio;
	}

	public Integer getNumPlazas() {
		return numPlazas;
	}

	public Integer getNumPasajeros() {
		return numPasajeros;
	}

	public String getCodigo() {
		return codVuelo;
	}

	public String getCodigoAe() {
		return codAero;
	}

	public Date getFecha() {
		return fecha;
	}

	// Setters
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	// Validación fecha:
	private void setFecha(String f) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(f, formatter);
			this.fecha = java.sql.Date.valueOf(localDate);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(null, "Fecha introducida no válida, introduzca fecha de nuevo.",
					"Error de fecha", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Validación destino:
	private void setDestino(String destino) {
		if (destino.length() > 20 || destino.length() < 1) {
			JOptionPane.showMessageDialog(null, new ExceptionDestino(destino).toString(),
					"Error en destino introducido", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Validación precio:
	private void setPrecio(String precio) {
		try {
			this.precio = Double.parseDouble(precio);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + ": Precio de billete introducido no válido.",
					"Error de precio", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Validación número de pasajeros:
	private void setNumPasajeros(String np) throws ExceptionNumPasajeros {

		try {
			this.numPasajeros = Integer.parseInt(np);
			if (!(numPasajeros >= 0 && numPasajeros <= numPlazas)) {
				JOptionPane.showMessageDialog(null, new ExceptionNumPasajeros(numPasajeros).toString(),
						"Error en número de pasajeros", JOptionPane.WARNING_MESSAGE);
				throw new ExceptionNumPasajeros(np);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + ": No se han introducido números enteros.",
					"Error en número de pasajeros.", JOptionPane.WARNING_MESSAGE);
		}

	}

	// Validación número de plazas:
	private void setNumPlazas(String nPl) throws ExceptionNumPlazas {
		try {
			this.numPlazas = Integer.parseInt(nPl);
			if (numPlazas.compareTo(0) < 0) {
				JOptionPane.showMessageDialog(null, new ExceptionNumPlazas(numPlazas).toString(),
						"Error en número de plazas", JOptionPane.WARNING_MESSAGE);
				throw new ExceptionNumPlazas(numPlazas);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + ": No se han introducido números enteros.",
					"Error en número de plazas.", JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codVuelo == null) ? 0 : codVuelo.hashCode());
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof DTOVuelo))
			return false;
		if (obj == this.codVuelo)
			return true;

		DTOVuelo v = (DTOVuelo) obj;
		if (this.codVuelo.equalsIgnoreCase(v.getCodigo()) && this.getFecha().equals(v.getFecha()))
			return true;
		return false;
	}

	/**
	 * Sobreescribimos m�todo compareTo. El orden natural ser� por fecha y a
	 * igualdad de �sta por c�digo
	 */
	@Override
	public int compareTo(DTOVuelo v2) {
		// compareTo para varios criterios, clasificar, no para comparar en igualdad
		int resultado;
		resultado = this.getFecha().compareTo(v2.getFecha());
		if (resultado == 0)
			resultado = this.getCodigo().compareTo(v2.getCodigo());
		return resultado;
	}

	@Override
	public String toString() {
		return String.format("Cód: %s, aerop: %s, destino: %s, fecha: %s, %s€, numPlazas: %s, numPasajeros: %s\n",
				codVuelo, codAero, destino, fecha, precio, numPlazas, numPasajeros);
	}
}