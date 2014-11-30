/**
 * 
 */
package com.blackbird.rmi.phoenix;

/**
 * @author Aakash
 *
 */
public class PhoenixServerLogger {

	private static int logCount = 0;
	private static int errorLogCount = 0;
	
	static {
		
		//initialize server logger 
		
		
	}
	
	
	
	
	public static void log(String message)
	{
		//get class name
		
		//get line number
		if(logCount>)
		//log into file
		logCount++;
		
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
		//log directly
		logCount++;
		
	}
}
