package exceptions;

@SuppressWarnings("serial")
public class ExceptionNumPasajeros extends ExceptionVuelo {

	public ExceptionNumPasajeros() {
		super("Error genérico número pasajeros");
	}
	

	public ExceptionNumPasajeros(Integer numPasajeros) {
		super("Error en número de pasajeros: " + numPasajeros);
	}
	
	public ExceptionNumPasajeros(String s) {
		super(s);
	}

}