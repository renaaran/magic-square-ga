package cefet.rba.config;

/**
 *
 * @author Renato Barros Arantes
 */
public interface ReadProperty {
    public String getProperty(String name);
    public int getPropertyAsInt(String name);
    public boolean getPropertyAsBoolean(String name);
    public double getPropertyAsDouble(String name);   
}
