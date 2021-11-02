package exceptions;

@SuppressWarnings("serial")
public class ExceptionVuelo extends Exception{
	
	public ExceptionVuelo() {
		super("Error gen�rico en vuelo");
	}
	
	public ExceptionVuelo(String m) {
		super(m);
	}
}