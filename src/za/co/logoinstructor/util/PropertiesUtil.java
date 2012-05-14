package za.co.logoinstructor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import za.co.logoinstructor.exception.LOGOInstructorException;

public class PropertiesUtil
{
	private static Properties properties;
	
	// Prevent instantiation
	private PropertiesUtil() throws LOGOInstructorException{}
	
	private static void loadProperties() throws LOGOInstructorException
	{
	    try
		{
			properties = new Properties();
			File f = new File("config.properties");
			InputStream is = new FileInputStream(f);
			properties.load(is);
			is.close();
		}
		catch (FileNotFoundException e)
		{
			throw new LOGOInstructorException("Could not find properties file config.proprties (please check /LOGOInstructor)");
		}
		catch (IOException e)
		{
			throw new LOGOInstructorException("Could not load properties file config.proprties.");
		}		
	}

	public static String getProperty(String key) throws LOGOInstructorException
	{
		if(properties == null)
		{
			loadProperties();
		}
		
		return properties.getProperty(key);
	}
}
