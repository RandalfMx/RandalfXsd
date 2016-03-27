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
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.randalf.xsd.exception.XsdException;

import org.apache.log4j.Logger;

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
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		} finally {
			try {
				if (bais != null){
					bais.close();
				}
			} catch (IOException e) {
				log.error(e);
				throw new XsdException(e.getMessage(), e);
			}
		}
		return obj;
	}

	public C read(File fXml) throws XsdException{
		FileInputStream fis = null;
		C obj = null;

		try {
			if (fXml.exists()){
				fis = new FileInputStream(fXml);
				obj = read(fis);
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		} catch (XsdException e) {
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		} finally {
			try {
				if (fis != null){
					fis.close();
				}
			} catch (IOException e) {
				log.error(e);
				throw new XsdException(e.getMessage(), e);
			}
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
			log.debug("read(InputStream "+input+")");
			if (input != null)
			{
				log.debug("Richieste.package: "+persistentClass.getPackage().getName());
				jc = JAXBContext.newInstance(persistentClass.getPackage().getName());

				log.debug("jc.createUnmarshaller");
				u = jc.createUnmarshaller();

				log.debug("u.unmarchal("+input+")");
				tmp = u.unmarshal(input);
				obj = (C) tmp;
			}
		}
		catch (JAXBException e)
		{
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		}
		return obj;
	}

}
