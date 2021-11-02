package exceptions;

@SuppressWarnings("serial")
public class ExceptionNumPlazas extends ExceptionVuelo {

	public ExceptionNumPlazas() {
		super("Error genérico número plazas");
	}
	

	public ExceptionNumPlazas(Integer numPasajeros) {
		super("Error, número de plazas negativo: " + numPasajeros);
	}

}