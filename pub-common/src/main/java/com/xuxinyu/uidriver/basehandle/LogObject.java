package com.xuxinyu.uidriver.basehandle;
import org.apache.log4j.Logger;

public class LogObject {
	
private static Logger logger = Logger.getLogger(LogObject.class);
	
	public static void StartTestCase(String testcasename){
		logger.info("---------------------------------------------------");
		logger.info("******************"+"\""+testcasename+" \""+"开始执行"+"*****************");
		
	}
	
	public static void EndTestCase(String testcasename){
		logger.info("******************"+"\""+testcasename+" \""+"执行结束"+"*****************");
		logger.info("---------------------------------------------------");
		
	}
	
	public static void error(String message){
		
		logger.error(message);
	}
	
	public static void info(String message){
		
		logger.info(message);
	}
	
	public static void debug(String message){
		
		logger.debug(message);
	}
	
	public static void warn(String message){
		
		logger.warn(message);
	}
	
	

}
