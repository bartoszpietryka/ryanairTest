package com.bart.utils;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bart.exceptions.PropertiesKeeperException;

/**
 * Singleton class that will read property file. By default "test.properties".
 * In normal tests we would usually use many different properties files. i.e
 * with surnames containing national characters. In such case, this class would
 * be much more complex. For example, it would allow to read properties from
 * database or run parameterized tests in batch
 * 
 * @author bart
 *
 */
public class PropertiesKeeper {
	private final static Logger log = Logger.getLogger(PropertiesKeeper.class.getName());
	private Properties prop = null;
	private static PropertiesKeeper instance = null;

	/**
	 * constructor for singleton
	 */
	private PropertiesKeeper() {
		try {
			if (prop == null)
				// read default properties file.
				LoadProperties("test.properties");
		} catch (PropertiesKeeperException e) {
			fail(); // fail jUnit
			System.exit(0); // if not used in jUnit
		}
	};

	/**
	 * Returns PropertiesKeeper instance . When called for the first time,
	 * properties will be loaded from "test.properties"
	 * 
	 * @return Instance of PropertiesKeeper
	 */
	public static PropertiesKeeper getInstance() {
		if (instance == null) {
			instance = new PropertiesKeeper();
		}
		return instance;
	}

	/**
	 * Loads properties form file <code>Resource</code>
	 * 
	 * @param Resource
	 *            Name of file to use. It should be placed in "resources" folder
	 * @throws PropertiesKeeperException
	 */
	public void LoadProperties(String Resource) throws PropertiesKeeperException {

		prop = new Properties();
		try {
			InputStream inStream = PropertiesKeeper.class.getClassLoader().getResourceAsStream(Resource);
			if (inStream == null)
				throw new FileNotFoundException("File not found " + Resource);
			prop.load(inStream);

			// save all properties to log file
			inStream = PropertiesKeeper.class.getClassLoader().getResourceAsStream(Resource);
			StringBuilder builder = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(inStream);
			char[] buffer = new char[1024];
			int length;
			while ((length = reader.read(buffer)) != -1) {
				builder.append(buffer, 0, length);
			}
			log.info("Properties for test");
			log.info(builder.toString());
		} catch (FileNotFoundException e1) {
			// all exceptions will be logged by PageTemplate.java anyway. But
			// it's always better to be on the safe side
			log.error("Property file not found: " + Resource);
			log.error(e1.getMessage(), e1);
			throw new PropertiesKeeperException("Property file not found: " + Resource);
		} catch (IOException e) {
			log.error("IOException when reading property file : " + Resource);
			log.error(e.getMessage(), e);
			throw new PropertiesKeeperException("IOException when reading property file : " + Resource);
		}

	}

	/**
	 * Returns string assigned to <code>property</code> parameter.
	 * 
	 * @param property
	 *            Property name
	 * @return Property value as String. If property does not exist - null
	 * @throws PropertyManagerException
	 */
	public String getProp(String property) throws PropertiesKeeperException {
		if (prop == null)
			throw new PropertiesKeeperException("Property file was not loaded");
		String readedProperty = prop.getProperty(property);
		if (readedProperty == null)
			log.warn("Property with name: " + property + " not found");
		return readedProperty;

	}
	
	/**
	 * Parse string assigned to <code>property</code> parameter to Integer
	 * 
	 * @param property
	 *            Property name
	 * @return Property value as Integer. If property does not exist - null
	 * @throws PropertyManagerException
	 */
	public  Integer getPropAsInt(String property)
			throws PropertiesKeeperException {
		try {
			if (prop == null)
				throw new PropertiesKeeperException(
						"Property file was not loaded");
			String readedProperty = prop.getProperty(property);
			if (readedProperty == null) {
				log.warn("Property with name: " + property + " not found");
				return null;
			}
			return Integer.valueOf(readedProperty);
		} catch (NumberFormatException e) {
			throw new PropertiesKeeperException("Property with name: "
					+ property + "could not be parsed to Integer");
		}
	}
}
