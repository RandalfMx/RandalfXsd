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

import org.apache.log4j.Logger;

import mx.randalf.xsd.exception.XsdException;

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
		write(obj, fXml, namespacePrefixMapper, listener, xmlAdapter, schemaLocation, true);

	}
	public void write(C obj, File fXml, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation, Boolean jaxbFormattedOutput) throws XsdException{
		FileOutputStream fos = null;
		
		try
		{
			
			if (!fXml.getParentFile().exists()){
				if (!fXml.getParentFile().mkdirs()){
					throw new FileNotFoundException("Riscontrato un problema nella creazione della cartella ["+fXml.getParentFile().getAbsolutePath()+"]");
				}
			}
			fos = new FileOutputStream(fXml);
			write(obj, fos, namespacePrefixMapper, listener, xmlAdapter, schemaLocation, jaxbFormattedOutput);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(),e);
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
				log.error(e.getMessage(), e);
				throw new XsdException(e.getMessage(), e);
			}
		}
	}

	public String write(C obj, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation) throws XsdException{
		return write(obj, namespacePrefixMapper, listener, xmlAdapter, schemaLocation, true);
	}

	public String write(C obj, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation, Boolean jaxbFormattedOutput) throws XsdException{
		ByteArrayOutputStream baos = null;
		
		try {
			baos = new ByteArrayOutputStream();
			write(obj, baos, namespacePrefixMapper, listener, xmlAdapter, schemaLocation, jaxbFormattedOutput);
		} catch (XsdException e) {
			throw e;
		} finally {
			try {
				if (baos !=null){
					baos.flush();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new XsdException(e.getMessage(), e);
			}
		}
		if (baos != null){
			try {
				return baos.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
				throw new XsdException(e.getMessage(), e);
			}
		} else {
			return null;
		}
	}

	public void write(C obj, OutputStream os, NamespacePrefixMapper namespacePrefixMapper,
			Listener listener, XmlAdapter<Object, Object> xmlAdapter, String schemaLocation, Boolean jaxbFormattedOutput) throws XsdException{
		Marshaller m = null;
		JAXBContext jc = null;
		
		try
		{
			log.debug("\n"+"writeToString()");

			jc = initJAXBContext();

			log.debug("\n"+"jc.createMarshaller();");
			m = jc.createMarshaller();

			log.debug("\n"+"m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, "+jaxbFormattedOutput+");");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, jaxbFormattedOutput);

			if (namespacePrefixMapper != null)
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);

			if (listener != null)
				m.setListener(listener);

			if (xmlAdapter != null)
				m.setAdapter(xmlAdapter);

			if (schemaLocation != null)
				m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);

			log.debug("\n"+"m.marshal( obj, os );");
			m.marshal( obj, os );
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
			throw new XsdException(e.getMessage(), e);
		}
	}

	protected JAXBContext initJAXBContext() throws JAXBException{
		JAXBContext jc = null;

		log.debug("\n"+"Utente.package: "+persistentClass.getPackage().getName());
		jc = JAXBContext.newInstance(persistentClass.getPackage().getName());

		return jc;
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
	public  OutputStream writeOutputStream(C datiXml, Boolean jaxbFormattedOutput) throws PropertyException, JAXBException, IOException, Exception
	{
		return writeOutputStream(datiXml, null, jaxbFormattedOutput);
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
	public OutputStream writeOutputStream(C datiXml, NamespacePrefixMapper namespacePrefixMapper, Boolean jaxbFormattedOutput) throws PropertyException, 
				JAXBException, IOException, Exception
	{
		Marshaller m = null;
		JAXBContext jc = null;
		ByteArrayOutputStream baos = null;
		
		try {
			log.debug("\n"+"write()");

			log.debug("\n"+"Utente.package: "+datiXml.getClass().getPackage().getName());
			jc = JAXBContext.newInstance(datiXml.getClass().getPackage().getName());

			log.debug("\n"+"jc.createUnmarshaller()");
			m = jc.createMarshaller();
			
			if (namespacePrefixMapper != null)
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);

			log.debug("\n"+"m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, "+jaxbFormattedOutput+");");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, jaxbFormattedOutput);
			
			baos = new ByteArrayOutputStream();
			log.debug("\n"+"m.marshal( utente, fos )");
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
	public  InputStream writeInputStream(C datiXml, Boolean jaxbFormattedOutput) throws PropertyException, JAXBException, IOException, Exception
	{
		return writeInputStream(datiXml, null, jaxbFormattedOutput);
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
	public  InputStream writeInputStream(C datiXml, NamespacePrefixMapper namespacePrefixMapper, Boolean jaxbFormattedOutput) throws PropertyException, 
			JAXBException, IOException, Exception
	{
		ByteArrayOutputStream baos = null;
		
		baos = (ByteArrayOutputStream) writeOutputStream(datiXml, namespacePrefixMapper, jaxbFormattedOutput);
		log.info("\n"+baos.toString());
		return new ByteArrayInputStream(baos.toByteArray());
	}

}
