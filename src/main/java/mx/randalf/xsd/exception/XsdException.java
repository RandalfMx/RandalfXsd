/**
 * 
 */
package mx.randalf.xsd.exception;

/**
 * Classe utilizzata per gestire gli Errore relativi alla gestione degli XSD nelal Teca
 * @author massi
 *
 */
public class XsdException extends Exception {

	/**
	 * Codice serial Version UID
	 */
	private static final long serialVersionUID = -1728888461163616298L;

	/**
	 * Costruttore
	 * 
	 * @param message Messaggio di errore
	 */
	public XsdException(String message) {
		super(message);
	}

	/**
	 * Costruttore
	 * 
	 * @param message Messaggio di errore
	 * @param cause Causa dell'errore
	 */
	public XsdException(String message, Throwable cause) {
		super(message, cause);
	}

}
