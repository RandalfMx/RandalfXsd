/**
 * 
 */
package mx.randalf.xsd;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import mx.randalf.xsd.exception.XsdException;


/**
 * @author massi
 *
 */
public class ReadXsd<C> extends WriteXsd<C> {

	/**
	 * Variabile utilizzata per loggare l'applicazione
	 */
	private static Logger log = Logger.getLogger(ReadXsd.class);

	/**
	 * 
	 */
	public ReadXsd() {
	}

	public C read(String fXml) throws XsdException{
		ByteArrayInputStream bais = null;
		C obj = null;

		try {
			if (fXml != null){
				bais = new ByteArrayInputStream(fXml.getBytes());
				obj = read(bais);
			}
		} catch (XsdException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		} finally {
			try {
				if (bais != null){
					bais.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new XsdException(e.getMessage(), e);
			}
		}
		return obj;
	}

	public C read(File fXml) throws XsdException{
		return read(fXml, "UTF-8");
	}

	public C read(File fXml, String encoding) throws XsdException{
		FileInputStream fis = null;
		Reader reader = null;
		C obj = null;

		try {
			if (fXml.exists()){
				fis = new FileInputStream(fXml);
				reader = new InputStreamReader(fis, encoding);
				obj = read(reader);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		} catch (XsdException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		} finally {
			try {
				if (reader != null){
					reader.close();
				}
				if (fis != null){
					fis.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new XsdException(e.getMessage(), e);
			}
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private C read(Reader reader) throws XsdException {
		JAXBContext jc = null;
		Unmarshaller u = null;
		Object tmp = null;
		C obj = null;

		try
		{
			log.debug("\n"+"read(InputStream "+reader+")");
			if (reader != null)
			{
				jc = initJAXBContext();

				log.debug("\n"+"jc.createUnmarshaller");
				u = jc.createUnmarshaller();

				log.debug("\n"+"u.unmarchal("+reader+")");
				tmp = u.unmarshal(reader);
				obj = (C) tmp;
			}
		}
		catch (JAXBException e)
		{
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new XsdException(e.getMessage(), e);
		}
		return obj;
	}

	public C read(OutputStream output) throws XsdException{
		InputStream input = null;
		input = new ByteArrayInputStream(output.toString().getBytes());
		return read(input);
	}

	@SuppressWarnings("unchecked")
	public C read(InputStream input) throws XsdException{
		JAXBContext jc = null;
		Unmarshaller u = null;
		Object tmp = null;
		C obj = null;

		try
		{
			log.debug("\n"+"read(InputStream "+input+")");
			if (input != null)
			{
				jc = initJAXBContext();

				log.debug("\n"+"jc.createUnmarshaller");
				u = jc.createUnmarshaller();

				log.debug("\n"+"u.unmarchal("+input+")");
				tmp = u.unmarshal(input);
				obj = (C) tmp;
			}
		}
		catch (JAXBException e)
		{
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new XsdException(e.getMessage(), e);
		}
		return obj;
	}

	public boolean check(String fXml) throws XsdException{
		boolean ris = false;
		C obj = null;
		
		try {
			obj = read(fXml);
			if (obj != null){
				ris = true;
			}
		} catch (XsdException e) {
			throw e;
		}
		return ris;
	}

	public boolean check(File fXml) throws XsdException{
		boolean ris = false;
		C obj = null;
		
		try {
			obj = read(fXml);
			if (obj != null){
				ris = true;
			}
		} catch (XsdException e) {
			throw e;
		}
		return ris;
	}

	public boolean check(OutputStream fXml) throws XsdException{
		boolean ris = false;
		C obj = null;
		
		try {
			obj = read(fXml);
			if (obj != null){
				ris = true;
			}
		} catch (XsdException e) {
			throw e;
		}
		return ris;
	}

	public boolean check(InputStream fXml) throws XsdException{
		boolean ris = false;
		C obj = null;
		
		try {
			obj = read(fXml);
			if (obj != null){
				ris = true;
			}
		} catch (XsdException e) {
			throw e;
		}
		return ris;
	}
}
