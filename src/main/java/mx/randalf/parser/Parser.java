/*
 * Created on 16-dic-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.randalf.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Vector;


import mx.randalf.interfacException.exception.PubblicaException;
import mx.randalf.parser.exception.ParserException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * Questa classe viene utilizzata eseguire il parsing di un file XML e la
 * relativa validazione
 * 
 * @author randazzo
 * 
 */
public class Parser extends DOMParser {

	private static Logger log = LogManager.getLogger(Parser.class);

	/**
	 * Questo metodo viene invocato quando questa classe viene utilizzata come
	 * applicazione
	 * 
	 * @param args
	 * @throws SAXParseException
	 */
	public static void main(String[] args) throws SAXParseException {
		/*
		 * System.setProperty("http.proxyHost", "siaproxy.siav.net");
		 * System.setProperty("http.proxyPort", "8080");
		 * System.setProperty("http.proxyUser", "SIAV\\MRandazzo");
		 * System.setProperty("http.proxyPassword", "G@l@ss1@02");
		 * System.setProperty("http.noProxyHosts", "");
		 */
		File f = null;
		File[] f1 = null;
		if (args.length > 0) {
			f = new File(args[0]);
			if (f.exists()) {
				if (f.isFile()) {
					checkFile(f);
				} else {
					f1 = f.listFiles();
					for (int y = 0; y < f1.length; y++) {
						if ((y % 100) == 0)
							log.info("\n"+"File: " + y + "/" + f1.length);
						f = f1[y];
						if (f.isFile()) {
							checkFile(f);
						}
					}
					log.info("\n"+"File Analisi: " + f1.length);
				}
			}
		} else
			System.out.println("no args");
	}

	public static boolean checkFile(File f) throws SAXParseException {
		ParserException pe = new ParserException();
		Parser parser = new Parser();
		SAXParseException saxPe = null;
		boolean esito = false;
		try {
			if (f.getName().toUpperCase().endsWith(".XML")) {
				log.debug("\n"+"Elaboro il File: " + f.getPath());
				parser.eseguiParser(f.getPath(), pe);
				if (pe.getNumErr() > 0) {
					log.error("\n"+"Elaboro il File: " + f.getPath());
					log.error("\n"+"Num Err.: " + pe.getNumErr());
					Vector<SAXParseException> err = pe.getMsgErr();
					for (int x = 0; x < err.size(); x++) {
						saxPe = (SAXParseException) err.get(x);
						log.debug("\n"+"Line: " + saxPe.getLineNumber() + " Col: "
								+ saxPe.getColumnNumber() + " Msg: "
								+ saxPe.getMessage());
					}
				} else {
					esito = true;
					log.debug("\n"+" OK");
				}
			}
		} catch (PubblicaException e) {
			log.error(e.getMessage(), e);
		}
		return esito;
	}

	/**
	 * Costruttore
	 */
	public Parser() {
		super();
		// System.setProperty("http.proxyHost", "siaproxy.siav.net");
		// System.setProperty("http.proxyPort", "8080");
		// System.setProperty("http.noProxyHosts",
		// prop.getProperty("Proxy.noProxy","localhost"));

	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 * @throws SAXParseException
	 */
	public Parser(String fileXML, ParserException errors)
			throws PubblicaException, SAXParseException {
		super();
		eseguiParser(fileXML, errors, true);
	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 * @throws SAXParseException
	 */
	public Parser(String fileXML, ParserException errors, boolean validazione)
			throws PubblicaException, SAXParseException {
		super();
		eseguiParser(fileXML, errors, validazione);
	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public Parser(Reader fileXML, ParserException errors)
			throws PubblicaException {
		super();
		eseguiParser(fileXML, errors, true);
	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public Parser(InputStream fileXML, ParserException errors)
			throws PubblicaException {
		super();
		eseguiParser(fileXML, errors, true);
	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public Parser(Reader fileXML, ParserException errors, boolean validazione)
			throws PubblicaException {
		super();
		eseguiParser(fileXML, errors, validazione);
	}

	/**
	 * Costruttore modificato in modo che prende come imput il file XML da
	 * verificare e modificare
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public Parser(InputStream fileXML, ParserException errors,
			boolean validazione) throws PubblicaException {
		super();
		eseguiParser(fileXML, errors, validazione);
	}

	/**
	 * Questo metodo viene utilizzato per eseguire i controlli sul file XML
	 * erendere disponibili i diversi oggetti del file stesso
	 * 
	 * @param fileXML
	 * @param errors
	 * @throws SAXParseException
	 */
	public void eseguiParser(String fileXML, ParserException errors)
			throws PubblicaException, SAXParseException {
		eseguiParser(fileXML, errors, true);
	}

	/**
	 * Questo metodo viene utilizzato per eseguire i controlli sul file XML
	 * erendere disponibili i diversi oggetti del file stesso
	 * 
	 * @param fileXML
	 * @param errors
	 * @throws SAXParseException
	 */
	public void eseguiParser(String fileXML, ParserException errors,
			boolean validazione) throws PubblicaException, SAXParseException {
		try {
			if (validazione) {

				this.setFeature(
						"http://apache.org/xml/features/validation/schema",
						true);
				this.setFeature("http://xml.org/sax/features/validation", true);
				/*
				 * this .setProperty(
				 * "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation"
				 * , "memory.xsd");
				 */
			}
			this.setErrorHandler(errors);
			this.parse(fileXML);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (SAXParseException e) {
			throw e;
		} catch (org.xml.sax.SAXException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Questo metodo viene utilizzato per eseguire i controlli sul file XML
	 * erendere disponibili i diversi oggetti del file stesso
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public void eseguiParser(Reader fileXML, ParserException errors,
			boolean validazione) throws PubblicaException {
		eseguiParser(new InputSource(fileXML), errors, validazione);
	}

	/**
	 * Questo metodo viene utilizzato per eseguire i controlli sul file XML
	 * erendere disponibili i diversi oggetti del file stesso
	 * 
	 * @param fileXML
	 * @param errors
	 */
	public void eseguiParser(InputStream fileXML, ParserException errors,
			boolean validazione) throws PubblicaException {
		eseguiParser(new InputSource(fileXML), errors, validazione);
	}

	/**
	 * Questo metodo viene utilizzato per eseguire i controlli su uno stream XML
	 * erendere disponibili i diversi oggetti del file stesso
	 * 
	 * @param testoXML
	 * @param errors
	 */
	public void eseguiParser(InputSource testoXML, ParserException errors,
			boolean validazione) throws PubblicaException {
		try {
			if (validazione) {
				this.setFeature("http://xml.org/sax/features/validation", true);

				this.setProperty(
						"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
						"memory.xsd");
			}
			this.setErrorHandler(errors);
			this.parse(testoXML);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (org.xml.sax.SAXException e) {
			log.error(e.getMessage(), e);
			throw new PubblicaException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
