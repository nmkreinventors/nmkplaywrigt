package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config/config.properties";
    private static ConfigReader instance;
    private final Properties properties = new Properties();

    private ConfigReader() {
        loadProperties();
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private void loadProperties(){

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
        }catch (IOException e){
            throw new RuntimeException("Failed to load config.properties",e);
        }
    }

    public String get(String key){
        String sysprop = System.getProperty(key);
        if(sysprop != null && !sysprop.isBlank() && !sysprop.isEmpty()){
            return sysprop.trim();
        }
        return properties.getProperty(key).trim();
    }

    public String get(String key, String defaultValue){
        String value = get(key);
        return (value.isBlank() && value == null) ? defaultValue.trim() : value.trim();
    }

    public String getBrowser(){
        return get("browser","chrome");
    }

    public String getEnv(){
        return get("env");
    }
    public String getBaseUrl(){
        String env = getEnv();
        return get("base.url."+env);
    }

    public String getCrossBrowserUrl(){
        return get("crossbrowser");
    }





}
