/**
 * Project Codename BlackBird
 * Package com.blackbird.rmi.phoenix
 * Author Aakash
 */
package com.blackbird.rmi.phoenix;

/**
 * @author Aakash
 *
 */
public class PhoenixServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		//-------------SERVER STARTUP-------------
		
		//load configuration manager
		//load logger
		//load service loader - JNI 
		//load service loader
		//load action dispatcher
		
		//-------------SERVER STARTUP COMPLETE-------------
		
		//Load Phoenix Configuration
		PhoenixServerConfigurationManager.init();
		//Load Phoenix server logger
		PhoenixServerLogger.init();
		//Load Service JNI Dependencies
		PhoenixServiceLoader.loadJNI();
		//Load all services 
		PhoenixServiceLoader.init();
		
		
		
		
		

		
		PhoenixServerLogger.close();
		System.exit(0);
	}

}
