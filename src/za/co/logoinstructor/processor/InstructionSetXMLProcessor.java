package za.co.logoinstructor.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import za.co.logoinstructor.exception.LOGOInstructorException;
import za.co.logoinstructor.jaxb.InstructionSet;
import za.co.logoinstructor.util.PropertiesUtil;

/**
 * Unmashall the instruction set and do schema validation.
 * 
 * @author Romeo Dickason
 * @version 1.0
 */
public class InstructionSetXMLProcessor
{
	private String instructionSetXMLFilename;
	private String instructionSetXSDFilename;

	public InstructionSetXMLProcessor(String instructionSetXMLFilename) throws LOGOInstructorException
	{
		this.instructionSetXMLFilename = instructionSetXMLFilename;
		this.instructionSetXSDFilename = PropertiesUtil.getProperty("xsdFilename");
	}

	/**
	 * Unmarshall the instruction set with schema validation.
	 * 
	 * @return jaxb instructionSet obj
	 * @throws LOGOInstructorException
	 */
	public InstructionSet unmarshallInstructionSet() throws LOGOInstructorException
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(InstructionSet.class);

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(instructionSetXSDFilename)));
			InstructionSet instructionSet = (InstructionSet) unmarshaller.unmarshal(new FileInputStream(instructionSetXMLFilename));
			
			// Instruction set xml unmarshalled and schema validation passed
			return instructionSet;
			
		}
		catch (FileNotFoundException e)
		{
			throw new LOGOInstructorException("InstructionSetXMLProcessor.unmarshallInstructionSet() - FileNotFoundException", e);
		}
		catch (JAXBException e)
		{
			throw new LOGOInstructorException("InstructionSetXMLProcessor.unmarshallInstructionSet() - JAXBException", e);
		}
		catch (SAXException e)
		{
			throw new LOGOInstructorException("InstructionSetXMLProcessor.unmarshallInstructionSet() - SAXException", e);
		}
	}
}
