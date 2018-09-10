package com.xuxinyu.action;

import com.xuxinyu.action.autohandle.TestSuitByExcel;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.xuxinyu.uidriver.basehandle.LogObject;
import com.xuxinyu.uidriver.utils.ExcelUtil;



public class CaseSuitsTest {
	
  @Test
  public void webSeleniumDemoTest() throws Exception {
	  TestSuitByExcel.ActionSuitsHandle();
	  
	  if(TestSuitByExcel.testresult==true){
		  Reporter.log("测试通过");
	  }else{
		  Assert.fail("测试不通过");
		  
	  }
	  
  }




  
  @Test
  public void webSeleniumDemoTest2(){
	  ExcelUtil.setExcelfileinmaven("xlsx");
	  LogObject.info("ceshipass");
	  Reporter.log("测试通过");
	  Reporter.log("测试通过");
	  Reporter.log("测试通过");
	  Reporter.log("测试通过");
  }
  
}		


