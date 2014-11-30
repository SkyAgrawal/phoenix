/**
 * 
 */
package com.blackbird.rmi.phoenix;

import java.lang.reflect.Method;

/**
 * @author Aakash
 *
 */
public class ActionPerformer {
	
	
	public String executeService(String serviceName , String serviceCall ,String... arguments )
	{
		if(serviceName.isEmpty())
		{
			PhoenixServerLogger.error("class name is empty");
			return null;
		}
		try{
		PhoenixServiceLoader serviceLoader = new PhoenixServiceLoader();
		
		Class<?> serviceClass = serviceLoader.getService(serviceName);
		if(null == serviceClass)
		{
			return null;
		}
		
		Method[] methodsInService = serviceClass.getMethods();
		for(Method m : methodsInService)
		{
			
			if(m.getName().equals(serviceCall))
			{
				PhoenixServerLogger.log(serviceCall+" method found in "+serviceClass);
				if(m.getParameterTypes().length==arguments.length)
				{
					PhoenixServerLogger.log(serviceCall+" method found in "+serviceClass);
					Object serviceObj = serviceClass.getConstructor();
					String returnValue = (String) m.invoke(serviceObj, (Object[])arguments);
					return returnValue;
				}
				else
				{
					PhoenixServerLogger.error("Argument Mismatch.Incorrect number of arguments passed");
					break;	
				}
				
				
			}			
		}

		PhoenixServerLogger.error(serviceCall+" method not found in "+serviceClass);
		}catch(Exception e)
		{
			PhoenixServerLogger.error("Caught Exception"+e.getMessage());
		}
		
		return null;
	}

}
