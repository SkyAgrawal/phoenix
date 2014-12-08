/**
 * 
 */
package com.blackbird.rmi.phoenix;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Aakash
 *
 */
public class PhoenixServerLogger {

	private static int logCount = 0;
	private static int errorLogCount = 0;
	private static int maxNumberOfLogs;
	private static File debugLogFile = null;
	private static File errorLogFile = null;
	private static Date initDateAndTime = new Date();
	private static boolean initDone = false;
	
	
	public static void init()
	{
		if(initDone)
			return;
		
		if(null == debugLogFile)
		{
			String logFilePath = PhoenixServerConfigurationManager.getConfiguration(PhoenixServerConfigurationConstants.LOG_FILE_PATH);
			
			File log_file = new File(logFilePath);
			if(null!=log_file)
			{
				debugLogFile = log_file;
			}
			
		}
		
		if(null == errorLogFile)
		{
			String errorLogFilePath = PhoenixServerConfigurationManager.getConfiguration(PhoenixServerConfigurationConstants.ERROR_LOG_FILE);
			
			File error_log_file = new File(errorLogFilePath);
			if(null!=error_log_file)
			{
				errorLogFile = error_log_file;
			}
			
		}
		try{
		maxNumberOfLogs = Integer.parseInt(PhoenixServerConfigurationManager.getConfiguration(PhoenixServerConfigurationConstants.MAX_NUMBER_LOG_ENTRY));
		}catch(Exception ex)
		{
			maxNumberOfLogs = 100;
		}
		initDateAndTime = new Date();
		
		
	}
	
	public static void log(String message)
	{
		//get class name
		String className = null;
		String lineNumber = null;
		//get line number
		log( className, lineNumber,  message);
		
	}
	
	public static void error(String message)
	{
		String className = null,lineNumber = null;
		PhoenixServerLogger.log(className,lineNumber,message);
		//log to error file 
		errorLogCount++;
	}

	/**
	 * @param className
	 * @param lineNumber
	 * @param message
	 */
	private static void log(String className, String lineNumber, String message) {

		if(logCount<maxNumberOfLogs)
		{
			
			//log into file
			
			
			
		}
		else
		{
			//Create new file and log it in it
			
			
			
			
		}
		
		logCount++;
		
	}
	
	public static void close()
	{
		
		log("Total Number of logs logged = "+PhoenixServerLogger.logCount);
		log("Total Number of error logged ="+PhoenixServerLogger.errorLogCount);
		try{
		Date currentDateAndTime = new Date();
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		currentDateAndTime = format.parse(currentDateAndTime.toString());
		 initDateAndTime= format.parse(initDateAndTime.toString());

		//in milliseconds
		long diff = currentDateAndTime.getTime() - initDateAndTime.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		log("Total Phoenix Server uptime "+diffMinutes+" minutes");
		}catch(Exception e)
		{
			log("Error in logginh server uptime "+e.getMessage());
		}
		
	}
}
