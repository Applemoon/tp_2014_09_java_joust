package services;

import utils.ReadXMLFileSAX;

import java.util.HashMap;
import java.util.Map;

public class ResourceFactory {
    private final Map<String, Object> resources = new HashMap<>();
    private static ResourceFactory resourceFactory;

    public final static String serverSettingsFilename = "src/xml/server_settings.xml";
    public final static String dbSettingsFilename     = "src/xml/db_settings.xml";
    public final static String gameSettingsFilename   = "src/xml/game_settings.xml";

    public static ResourceFactory instance(){
        if (resourceFactory == null){
            resourceFactory = new ResourceFactory();
        }
        return resourceFactory;
    }

    private ResourceFactory() {}

    public void setResource(String filename) {
        Object resource = ReadXMLFileSAX.readXML(filename);
        resources.put(filename, resource);
    }

    public Object getResource(String filename) {
        return resources.get(filename);
    }
}
