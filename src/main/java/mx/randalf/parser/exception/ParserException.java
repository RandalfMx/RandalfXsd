/*
 * Created on 16-dic-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.randalf.parser.exception;

import java.util.Vector;

import mx.randalf.interfacException.exception.PubblicaException;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 
 * @author randazzo
 * 
 */
public class ParserException implements ErrorHandler {

	/**
	 * Questa variabile viene utilizzata per loggare l'applicazione
	 */
	private static Logger log = Logger.getLogger(ParserException.class);

	/**
	 * Questa variabile viene utilizzata per indicare il numero di Errori
	 * individuati
	 */
	private int numErr = 0;

	/**
	 * Questa variabile viene utilizzata per indicare il numero di Warning
	 * individuati
	 */
	private int numWar = 0;

	/**
	 * In questa variabile viene indicato il tipo di Errore associato al
	 * messaggio
	 */
	private Vector<String> tipoErr;

	/**
	 * In questa variabile viene indicato il messaggio di errore
	 */
	private Vector<SAXParseException> msgErr;

	/**
	 * 
	 */
	public ParserException() {
		super();
		tipoErr = new Vector<String>();
		msgErr = new Vector<SAXParseException>();
	}

	/**
	 * 
	 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
	 */
	public void warning(SAXParseException ex) throws SAXException {
		numWar++;
		log.error("Warning: " + ex.getMessage());
		handleError(ex, PubblicaException.WARNING);
	}

	/**
	 * 
	 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException ex) throws SAXException {
		numErr++;
		log.error("Error: " + ex.getMessage());
		handleError(ex, PubblicaException.ERROR);
	}

	/**
	 * 
	 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	public void fatalError(SAXParseException ex) throws SAXException {
		numErr++;
		log.error("Fatal Error: " + ex.getMessage());
		handleError(ex, PubblicaException.FATAL_ERROR);
	}

	/**
	 * 
	 * @param ex
	 * @param type
	 */
	private void handleError(SAXParseException ex, String type) {
		tipoErr.add(type);
		msgErr.add(ex);
	}

	/**
	 * Questo metodo viene utilizzato per leggere la lista dei tipi di errori
	 * riscontrati
	 * 
	 * 
	 */
	public Vector<String> getTipoErr() {
		return tipoErr;
	}

	/**
	 * Questo metodo viene indicato per leggere la lista dei messaggi
	 * riscontrati
	 * 
	 * 
	 */
	public Vector<SAXParseException> getMsgErr() {
		return msgErr;
	}

//	public void writeErrorParse(Document doc, Element ele) {
//		ConvertException lista = null;
//		ConvertToUTF8 convert = null;
//
//		lista = new ConvertException();
//		convert = new ConvertToUTF8(lista, "");
//		writeErrorParse(doc, ele, convert);
//	}

//	/**
//	 * Questo metodo viene utilizzato per scrivere tutti i possibili errori
//	 * generati durante la verifica
//	 * 
//	 */
//	public void writeErrorParse(Document doc, Element ele, ConvertToUTF8 convert) {
//		int numErr = 0;
//
//		log.debug("Inizio writeErrorPage");
//		numErr = tipoErr.size();
//
//		for (int x = 0; x < numErr; x++) {
//
//			log.debug("Errore: " + x + "/" + numErr);
//			SAXParseException msgError = (SAXParseException) msgErr.get(x);
//
//			addErrorRow(convert, ele, doc, PubblicaException.PARSER_ERROR,
//					(String) tipoErr.get(x), msgError.getLineNumber(),
//					msgError.getColumnNumber(), msgError.getLocalizedMessage());
//		}
//		log.debug("Fine writeErrorPage");
//	}
//
//	private void addErrorRow(ConvertToUTF8 convert, Element pubblica,
//			Document doc, int id, String tipo, int riga, int colonna,
//			String messaggio) {
//		Element errore = null;
//		Element eTipo = null;
//		Element eRiga = null;
//		Element eColonna = null;
//		Element eMessaggio = null;
//
//		errore = doc.createElement("Errore");
//		errore.setAttribute("Id", convert.convert(Integer.toString(id)));
//
//		eTipo = doc.createElement("tipo");
//		eTipo.appendChild(doc.createTextNode(convert.convert(tipo)));
//		errore.appendChild(eTipo);
//
//		if (riga > 0) {
//			eRiga = doc.createElement("riga");
//			eRiga.appendChild(doc.createTextNode(convert.convert(Integer
//					.toString(riga))));
//			errore.appendChild(eRiga);
//		}
//		if (colonna > 0) {
//			eColonna = doc.createElement("colonna");
//			eColonna.appendChild(doc.createTextNode(convert.convert(Integer
//					.toString(colonna))));
//			errore.appendChild(eColonna);
//		}
//
//		eMessaggio = doc.createElement("messaggio");
//		eMessaggio.appendChild(doc.createTextNode(convert.convert(messaggio)));
//		errore.appendChild(eMessaggio);
//		pubblica.appendChild(errore);
//	}
//
//	public void writeError(Document doc, Element ele, PubblicaException pExc) {
//		ConvertException lista = null;
//		ConvertToUTF8 convert = null;
//
//		lista = new ConvertException();
//		convert = new ConvertToUTF8(lista, "");
//		writeError(doc, ele, pExc, convert);
//	}
//
//	/**
//	 * Questo metodo viene utilizzato per scrivere tutti i possibili errori
//	 * 
//	 */
//	public void writeError(Document doc, Element ele, PubblicaException pExc,
//			ConvertToUTF8 convert) {
//		log.debug("Inizio writeError");
//		addErrorRow(convert, ele, doc, pExc.getId(), pExc.getTipo(), 0, 0,
//				pExc.getMessage());
//		log.debug("Fine writeError");
//
//	}

	/**
	 * Questo metodo viene utilizzato per leggere il numero di Errori trovati
	 * 
	 */
	public int getNumErr() {
		return numErr;
	}

	/**
	 * Questo metodo viene utilizzato per leggere il numero di Warning trovati
	 * 
	 */
	public int getNumWar() {
		return numWar;
	}

}
