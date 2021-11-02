package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DTOAeropuerto implements Serializable {
	private String codAero;
	private String nombre;
	
	public DTOAeropuerto(String codAero, String nombre) {
		this.codAero = codAero;
		this.nombre = nombre;
	}

	// Getters
	public String getCodAero() {
		return codAero;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return String.format("Código aeropuerto: %s, nombre: %s\n", codAero, nombre);
	}
}