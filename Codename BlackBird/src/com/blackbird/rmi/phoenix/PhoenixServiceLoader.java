/**
 * Project Codename BlackBird
 * Package com.blackbird.rmi.phoenix
 * Author Aakash
 */
package com.blackbird.rmi.phoenix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	public PhoenixServiceLoader() {
		super();

		if (PhoenixServiceLoader.dirtyMap) {
			PhoenixServerLogger.log("sericeMap is dirty.");
			if (!loadMapfromConfig()) {
				PhoenixServerLogger
						.log("Failed to load config into serviceMap");
				PhoenixServiceLoader.dirtyMap = true;
			} else {
				PhoenixServerLogger.log("sericeMap successfully loaded");
				PhoenixServiceLoader.dirtyMap = false;

			}
		} else {
			
			PhoenixServerLogger.log("sericeMap is not dirty.");
			
		}
		
		PhoenixServerLogger.log("Number of configured services "+PhoenixServiceLoader.serviceMap.size());

	}

	private boolean loadMapfromConfig() {
		boolean loadingIsSuccessfull = false;
		InputStream input = null;
		try {

			Properties serviceProperties = new Properties();

			/*
			 * 
			 * First load from System properties AKA JVM argument
			 * -DPhoenixConfigMapPath If failed to get the path then try from
			 * class loader If it still fails screw u !!
			 */

			String configPath = System.getProperty("PhoenixConfigMapPath");
			if (configPath.isEmpty()) {
				/*
				 * 
				 * Could not get from System propertiees Trying from class path
				 */

				PhoenixServerLogger
						.log("Failed to get PhoenixConfigMapPath from system properties. Trying from class path ");

				/*
				 * 
				 * TODO : Need to get from configuration
				 */
				String filename = "PhoenixConfigMap.properties";
				input = getClass().getClassLoader().getResourceAsStream(
						filename);

			} else {
				input = new FileInputStream(configPath);
			}

			if (null == input) {

				PhoenixServiceLoader.serviceMap.clear();
				PhoenixServerLogger
						.log("Failed to obtain service mappings. No service is loaded ");
				return false;
			}

			serviceProperties.load(input);

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
					.log("Got Exception when loading configuration. Clearing serviceMap");
			ex.printStackTrace();
			PhoenixServiceLoader.serviceMap.clear();
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

}
