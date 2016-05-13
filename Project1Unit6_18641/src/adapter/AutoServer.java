package adapter;

import java.awt.List;
import java.util.Properties;

import model.Automobile;

/**
 * @author-Rui Wang rw1
 */
public interface AutoServer {
    public Automobile acceptPropertiesFromClient(Properties properties);
    public String listAutomobileNames();
    public Automobile getAutoMobileByName(String name);
}
