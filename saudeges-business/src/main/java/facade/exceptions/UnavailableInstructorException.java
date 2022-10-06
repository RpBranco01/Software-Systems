package facade.exceptions;

public class UnavailableInstructorException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -8140140401436662654L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public UnavailableInstructorException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public UnavailableInstructorException(String message, Exception e) {
		super(message, e);
	}
}
