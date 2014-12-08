/**
 * Project Codename BlackBird
 * Package com.blackbird.rmi.phoenix
 * Author Aakash
 */
package com.blackbird.rmi.phoenix;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aakash
 *
 */
public class PhoenixServerConfigurationManager {

	private static Map<String,String> ConfigurationMap = null;
	
	static public void init()
	{
		
		Properties PhoenixProperties = new Properties();
		
		try {
			PhoenixProperties.load(PhoenixServerConfigurationManager.class.getResourceAsStream("Phoenix.properties"));
			ConfigurationMap = new HashMap<String, String>();
			Enumeration<String> keySet = (Enumeration<String>) PhoenixProperties.propertyNames();
			
			while(keySet.hasMoreElements())
			{		
				String key = keySet.nextElement();
				ConfigurationMap.put(key, PhoenixProperties.getProperty(key));
			}
			
		} catch (IOException e) {
			ConfigurationMap = null;
		}
	
		
	}
	
	
	public static String getConfiguration(PhoenixServerConfigurationConstants ConfigurationName)
	{
		if(null == ConfigurationMap )
		{
			
			PhoenixServerLogger.error("Configuration Map is unitialized. Restart Server.");
			PhoenixServerLogger.log("ConfigurationMap is null");
			return null;
		}
		
		if(ConfigurationMap.size()==0)
		{
			PhoenixServerLogger.log("ConfigurationMap is empty");
			return "";
		}
		
		String configuration = ConfigurationMap.get(ConfigurationName.toString()); 
		
		
		return configuration;
	}
}
