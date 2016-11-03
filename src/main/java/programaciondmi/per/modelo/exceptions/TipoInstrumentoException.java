package programaciondmi.per.modelo.exceptions;

/**
 * Este excepción se lanza cuando se intenta crear un instrumentoy no se pasan los
 * parámetros adecuados.
 * @author damanzano
 *
 */
public class TipoInstrumentoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TipoInstrumentoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoInstrumentoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public TipoInstrumentoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TipoInstrumentoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TipoInstrumentoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
