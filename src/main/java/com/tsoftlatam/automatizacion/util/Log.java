package com.tsoftlatam.automatizacion.util;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class);
	 
	 public static void info(String String){
		 Log.info(String);	  
	}
	 
	 public static void error(String String){
		 Log.error(String);	  
	}
	 
	 //Mensaje a incorporar al inicio de la prueba	 
	 public static void startTestCase(String sTestCaseName){
		 Log.info("****************************************************************************************");	 
		 Log.info("**********************                 "+sTestCaseName+ "         **********************");	 
		 Log.info("****************************************************************************************");	 
	}
	 
	 //Mensaje a incorporar al final de la prueba	 
	 public static void endTestCase(String sTestCaseName){	
		 Log.info("****************************************************************************************");
		 Log.info("********************************             "+"****END***"+"             ********************************");	 
		 Log.info("****************************************************************************************");
		 Log.info("***");
	 }
}
