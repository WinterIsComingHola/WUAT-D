package com.xuxinyu.uidriver.testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.xuxinyu.uidriver.basehandle.Constant;


/*和RetryListener类配合，完成用例重跑的判断逻辑*/
public class TestNGRetry implements IRetryAnalyzer {
	
	private int retryCount = 1;
	private static int maxRetryCount;
	
	static{
		maxRetryCount = Constant.retrycount;
		
	}
	
	
	@Override
	public boolean retry(ITestResult result) {
		
		if(retryCount <= maxRetryCount){
			
			String message = "Retry for [" + result.getName() + "] on class [" + result.getTestClass().getName()
					+ "] Retry " + retryCount + " times";
			System.out.println(message);
			retryCount++;
			return true;
		}
		
		return false;
	}

}
