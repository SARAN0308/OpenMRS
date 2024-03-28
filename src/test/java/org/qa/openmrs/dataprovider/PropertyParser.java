package org.qa.openmrs.dataprovider;

import org.apache.log4j.Logger;
import org.qa.openmrs.constants.FilePathConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyParser {
    Properties properties;
    static Logger logger = Logger.getLogger(String.valueOf(PropertyParser.class));


    /**
     * This method is used to read properties in properties files
     * @param locator
     * @return
     */
    public static String readLocators(String locator) {
        return (String) readLocators().getProperty(locator);

    }

    /**
     * This method is used to read the properties
     * @return
     */
    public static Properties readLocators() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get(FilePathConstant.configPath)));
        } catch (IOException e) {
            logger.info("File Not Found");
            logger.info(e.getLocalizedMessage());
        }
        return properties;
    }

    public PropertyParser(String propertyFilePath) {
        properties = new Properties();
        loadProperties(propertyFilePath);
    }

    // This is Private Constructor
    private PropertyParser() {
    }

    /**
     * This method loads properties file.
     *
     * @param propertyFilePath
     */
    private void loadProperties(String propertyFilePath) {
        File propertyFile = new File(propertyFilePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(propertyFile);
            properties.load(fileInputStream);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * This method returns value from the properties file base on given key.
     *
     * @param key
     * @return
     */
    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }
}
