// src/main/java/config/Configuration.java
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration properties file.");
        }
    }

    // Method to retrieve property values by key
    public static String get(String key) {
        return properties.getProperty(key);
    }

    // Method to retrieve property values as integer, with default value
    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Integer.parseInt(value) : defaultValue;
    }

    // Method to retrieve property values as boolean, with default value
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }
}
