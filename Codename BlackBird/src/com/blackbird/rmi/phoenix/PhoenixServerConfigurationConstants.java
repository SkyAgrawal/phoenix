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
public  enum PhoenixServerConfigurationConstants {

	LOG_FILE_PATH ( "logFilePath"),
	ERROR_LOG_FILE ( "errorLogFilePath"),
	MAX_NUMBER_LOG_ENTRY ( "maxNumberOfLogEntries"),
	PHOENIX_SERVICE_CONFIG_FILE_PATH ("phoenixServiceConfigFilePath"),
	JNI_CONFIGURATION_FILE_PATH ( "jniConfigurationFilePath");

	private final String ConstantValue;
	PhoenixServerConfigurationConstants(String value)
	{
		ConstantValue = value;
	}

	public String toString(){
	       return ConstantValue;
	    }



}
