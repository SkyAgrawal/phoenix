/**
 * Project Codename BlackBird
 * Package com.blackbird.rmi.phoenix
 * Author Aakash
 */
package com.blackbird.rmi.phoenix;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aakash
 *
 */
public class PhoenixServiceLoader {

	private static Map<String, String> serviceMap = new HashMap<String, String>();
	private static boolean dirtyMap = true;

	/**
	 * 
	 */
	public static void init() {
		
		if (PhoenixServiceLoader.dirtyMap) {
			PhoenixServerLogger.log("serviceMap is dirty.");
			if (!loadMapfromConfig()) {
				PhoenixServerLogger
						.error("Failed to load services into serviceMap");
				PhoenixServiceLoader.dirtyMap = true;
			} else {
				PhoenixServerLogger.log("sericeMap successfully loaded");
				PhoenixServiceLoader.dirtyMap = false;

			}
		} else {

			PhoenixServerLogger.log("sericeMap is not dirty.");

		}

		PhoenixServerLogger.log("Number of configured services "
				+ PhoenixServiceLoader.serviceMap.size());

	}

	private static boolean loadMapfromConfig() {
		boolean loadingIsSuccessfull = false;
		InputStream input = null;
		try {

			Properties serviceProperties = new Properties();

			input = new FileInputStream(new File(PhoenixServerConfigurationManager.getConfiguration(PhoenixServerConfigurationConstants.PHOENIX_SERVICE_CONFIG_FILE_PATH)));
			serviceProperties.load(input);
			PhoenixServiceLoader.serviceMap.clear();
			for (Object key : serviceProperties.keySet()) {
				String serviceName = (String) key;
				String serviceClass = serviceProperties
						.getProperty(serviceName);
				if ((!serviceName.isEmpty()) && (!serviceClass.isEmpty())) {
					PhoenixServerLogger.log("Adding service " + serviceName
							+ " to service map");
					PhoenixServiceLoader.serviceMap.put(serviceName,
							serviceClass);
				}

			}

			loadingIsSuccessfull = true;

		} catch (IOException ex) {
			PhoenixServerLogger
					.error("Got Exception when loading configuration. Clearing serviceMap");
			PhoenixServerLogger.log(ex.getMessage());
			ex.printStackTrace();
			PhoenixServiceLoader.serviceMap.clear();
			PhoenixServiceLoader.setDirtyMap();
		} finally {
			if (input != null) {
				try {
					input.close();
					// return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return loadingIsSuccessfull;
	}

	/**
	 * 
	 */
	public static void setDirtyMap() {
		PhoenixServiceLoader.dirtyMap = true;
	}

	public static boolean refresh() {
		PhoenixServerLogger.log("Refreshing Service Map from refresh method");
		if (PhoenixServiceLoader.dirtyMap) {
			if(PhoenixServiceLoader.loadMapfromConfig())
			{
				
				PhoenixServerLogger.log("Refreshing Service Map Successfull");
				PhoenixServiceLoader.dirtyMap = false;
				PhoenixServerLogger.log("Service Map is no longer dirty");
				return true;
				
			}
			else
			{
				
				PhoenixServerLogger.error("Refreshing Service Map Failed");
				PhoenixServiceLoader.setDirtyMap();
				return false;
			}
		}
		else
		{
			
			PhoenixServerLogger.log("Not doing anything for Refresh as map is not dirty");
			return true;
			 
		}
		
	}

	public static Class<?> getService(String serviceName) {

		if (serviceName.isEmpty()) {
			PhoenixServerLogger.log("Service Name is empty");
			return null;
		}

		PhoenixServerLogger.log("Service Name is " + serviceName);

		if (PhoenixServiceLoader.serviceMap.size() == 0) {
			PhoenixServerLogger.log("Service Map is empty. No services found");
			return null;
		}

		if (PhoenixServiceLoader.serviceMap.containsKey(serviceName)) {
			String className = PhoenixServiceLoader.serviceMap.get(serviceName);
			PhoenixServerLogger.log("Service "+serviceName+" Found");
			if (className.isEmpty()) {
				PhoenixServerLogger
						.error("Class name for service was not found. Service is not properly configured");
				return null;
			}

			PhoenixServerLogger.log("Locating " + className);
			Class<?> serviceClass = null;
			try {
				serviceClass = PhoenixServiceLoader.class.getClassLoader()
						.loadClass(className);
				PhoenixServerLogger.log("Succesfully found " + className);
				return serviceClass;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				PhoenixServerLogger
						.error("Class Not found Exception. Failed to locate service "+className);
				return null;
			}
		} else {
			PhoenixServerLogger.log("Service "+serviceName+" Not Found. Trying Again");
			PhoenixServerLogger.log("Marking Service Map as dirty");
			PhoenixServiceLoader.setDirtyMap();
			PhoenixServerLogger.log("Refreshing Service Map");
			PhoenixServiceLoader.refresh();	
			if(PhoenixServiceLoader.serviceMap.containsKey(serviceName))
			{
				PhoenixServerLogger.log("Service "+serviceName+" Found after refresh");
				return getService(serviceName);	
			}
			else
			{
				PhoenixServerLogger.error("Service "+serviceName+" not found");
				return null;
			}
		}
		
		
	}
	
	public static void loadJNI()
	{
		PhoenixServerLogger.log("In loadJNI()");
		String JNIconfigurationFilePath = PhoenixServerConfigurationManager.getConfiguration(PhoenixServerConfigurationConstants.JNI_CONFIGURATION_FILE_PATH);
		PhoenixServerLogger.log("Loading JNI configurations from "+JNIconfigurationFilePath);
		Properties jniConfigurationPropFile = new Properties();
		
		try {
			FileInputStream jniConfigurationFile = new FileInputStream(new File(JNIconfigurationFilePath));		
			jniConfigurationPropFile.load(jniConfigurationFile);
			Enumeration<String> keySet = (Enumeration<String>) jniConfigurationPropFile.propertyNames();
			
			while(keySet.hasMoreElements())
			{				
				String jniLibName = (String) keySet.nextElement();				
				String jniLibPath = jniConfigurationPropFile.getProperty(jniLibName);
				PhoenixServerLogger.log("Adding "+jniLibPath+" to java lib path");
				PhoenixServiceLoader.addToEnv(jniLibName);
				PhoenixServerLogger.log("Loading JNI Lib "+jniLibName);
				System.load(jniLibName);
			}
		} catch ( Exception e) {
			
			e.printStackTrace();
			PhoenixServerLogger.error("Failed to load JNI ");
			PhoenixServerLogger.log(e.getMessage());
		}
		
		PhoenixServerLogger.log("Leaving loadJNI()");
	}

	/**
	 * @param jniLibName
	 */
	private static void addToEnv(String jniLibName) {
		PhoenixServerLogger.log("In addToEnv()");
		
		
		
		PhoenixServerLogger.log("Leaving addToEnv()");
		
	}

}
