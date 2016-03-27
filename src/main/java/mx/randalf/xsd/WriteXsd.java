/**
 * 
 */
package mx.randalf.xsd;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Marshaller.Listener;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import mx.randalf.xsd.exception.XsdException;

import org.apache.log4j.Logger;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author massi
 *
 */
public class WriteXsd<C> {

	/**
	 * Variabile utilizzata per loggare l'applicazione
	 */
	private static Logger log = Logger.getLogger(WriteXsd.class);

	protected Class<C> persistentClass;
	
	/**
	 * Costruttore
	 */
	@SuppressWarnings("unchecked")
	public WriteXsd() {
		persistentClass = (Class<C>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Metodo utilizzato per scrivere il tracciato Xml su file
	 * 
	 * @param obj Tracciato Xml
	 * @param fXml Nome del File di output
	 * @throws XsdException Eccezione Xsd
	 */
	public void write(C obj, File fXml, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation) throws XsdException{
		FileOutputStream fos = null;
		
		try
		{
			
			if (!fXml.getParentFile().exists()){
				if (!fXml.getParentFile().mkdirs()){
					throw new FileNotFoundException("Riscontrato un problema nella creazione della cartella ["+fXml.getParentFile().getAbsolutePath()+"]");
				}
			}
			fos = new FileOutputStream(fXml);
			write(obj, fos, namespacePrefixMapper, listener, xmlAdapter, schemaLocation);
		} catch (FileNotFoundException e) {
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		}
		finally
		{
			try {
				if (fos != null){
					fos.flush();
					fos.close();
				}
			} catch (IOException e) {
				log.error(e);
				throw new XsdException(e.getMessage(), e);
			}
		}
	}

	public String write(C obj, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation) throws XsdException{
		ByteArrayOutputStream baos = null;
		
		try {
			baos = new ByteArrayOutputStream();
			write(obj, baos, namespacePrefixMapper, listener, xmlAdapter, schemaLocation);
		} catch (XsdException e) {
			throw e;
		} finally {
			try {
				if (baos !=null){
					baos.flush();
				}
			} catch (IOException e) {
				log.error(e);
				throw new XsdException(e.getMessage(), e);
			}
		}
		if (baos != null){
			try {
				return baos.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
				throw new XsdException(e.getMessage(), e);
			}
		} else {
			return null;
		}
	}

	public void write(C obj, OutputStream os, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation) throws XsdException{
		Marshaller m = null;
		JAXBContext jc = null;
		
		try
		{
			log.debug("writeToString()");

			log.debug("Utente.package: "+persistentClass.getPackage().getName());
			jc = JAXBContext.newInstance(persistentClass.getPackage().getName());

			log.debug("jc.createMarshaller();");
			m = jc.createMarshaller();

			log.debug("m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

			if (namespacePrefixMapper != null)
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);

			if (listener != null)
				m.setListener(listener);

			if (xmlAdapter != null)
				m.setAdapter(xmlAdapter);

			if (schemaLocation != null)
				m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);

			log.debug("m.marshal( obj, os );");
			m.marshal( obj, os );
		} catch (JAXBException e) {
			log.error(e);
			throw new XsdException(e.getMessage(), e);
		}
	}

	/**
	 * Questo metodo viene utilizzato per convertire le informazioni disponibili 
	 * in una OutputStream  XML
	 * 
	 * @param datiXml
	 * @return
	 * @throws PropertyException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Exception
	 */
	public  OutputStream writeOutputStream(C datiXml) throws PropertyException, JAXBException, IOException, Exception
	{
		return writeOutputStream(datiXml, null);
	}

	/**
	 * Questo metodo viene utilizzato per convertire le informazioni disponibili 
	 * in una OutputStream  XML
	 * 
	 * @param datiXml
	 * @return
	 * @throws PropertyException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Exception
	 */
	public OutputStream writeOutputStream(C datiXml, NamespacePrefixMapper namespacePrefixMapper) throws PropertyException, 
				JAXBException, IOException, Exception
	{
		Marshaller m = null;
		JAXBContext jc = null;
		ByteArrayOutputStream baos = null;
		
		try {
			log.debug("write()");

			log.debug("Utente.package: "+datiXml.getClass().getPackage().getName());
			jc = JAXBContext.newInstance(datiXml.getClass().getPackage().getName());

			log.debug("jc.createUnmarshaller()");
			m = jc.createMarshaller();
			
			if (namespacePrefixMapper != null)
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);

			log.debug("m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			
			baos = new ByteArrayOutputStream();
			log.debug("m.marshal( utente, fos )");
			m.marshal( datiXml, baos );
		} catch (PropertyException e) {
			throw e;
		} catch (JAXBException e) {
			throw e;
		}
		return baos;
	}

	/**
	 * Questo metodo viene utilizzato per convertire le informazioni disponibili 
	 * in una InputStream XML
	 * 
	 * @param datiXml
	 * @return
	 * @throws PropertyException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Exception
	 */
	public  InputStream writeInputStream(C datiXml) throws PropertyException, JAXBException, IOException, Exception
	{
		return writeInputStream(datiXml, null);
	}

	/**
	 * Questo metodo viene utilizzato per convertire le informazioni disponibili 
	 * in una InputStream XML
	 * 
	 * @param datiXml
	 * @return
	 * @throws PropertyException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Exception
	 */
	public  InputStream writeInputStream(C datiXml, NamespacePrefixMapper namespacePrefixMapper) throws PropertyException, 
			JAXBException, IOException, Exception
	{
		ByteArrayOutputStream baos = null;
		
		baos = (ByteArrayOutputStream) writeOutputStream(datiXml, namespacePrefixMapper);
		log.info(baos.toString());
		return new ByteArrayInputStream(baos.toByteArray());
	}

}
