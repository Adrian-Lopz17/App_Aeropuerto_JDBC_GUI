package exceptions;

@SuppressWarnings("serial")
public class ExceptionDestino extends ExceptionVuelo {

	public ExceptionDestino() {
		super("Error genérico en destino");
	}
	
	public ExceptionDestino(String d) {
		super("Error, destino introducido no válido: " + d);
	}

}