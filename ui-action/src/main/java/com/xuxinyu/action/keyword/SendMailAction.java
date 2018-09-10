package com.xuxinyu.action.keyword;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.xuxinyu.action.autohandle.TestSuitByExcel;
import com.xuxinyu.uidriver.utils.ElementObjectMap;
import com.xuxinyu.uidriver.utils.ExcelUtil;
import com.xuxinyu.uidriver.utils.JsExcutorUtil;
import com.xuxinyu.uidriver.utils.KeyBroadUtil;
import com.xuxinyu.uidriver.basehandle.LogObject;
import com.xuxinyu.uidriver.utils.WaitUtil;
import com.xuxinyu.uidriver.basehandle.Constant;


/**本类中的方法需要在登陆的基础上进行执行
 * 用于发送一封邮件
 * **/
public class SendMailAction  {
	
	private static WebDriver driver = null;
	private static ElementObjectMap objectmap =  new ElementObjectMap();
	
	
	public static void setdriver(String browsername){
		try{
			driver = LoginAction.getdriver(browsername);
			LogObject.info("设置driver为："+browsername);
		}catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("设置driver异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void writemail(String string) {
		
		try {
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.writemail")).click();
			LogObject.info("点击写邮件按钮！");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("点击写邮件按钮异常："+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void WaitFor_Element(String xpathexpression){
		try {
			WaitUtil.waitElement(driver, objectmap.getLocator(xpathexpression));
			LogObject.info("显式等待页面出现收件人按钮");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("等待页面出现收件人按钮异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void receiver(String receive) {
		try {
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.receiver")).clear();
			LogObject.info("清理收件人输入框");
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.receiver")).sendKeys(receive);
			LogObject.info("输入收件人地址");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("输入收件人地址异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void subject(String sub) {
		try {
			/*driver.findElement(objectmap.getLocator("126Mail.LoginPage.subject")).clear();
			LogObject.info("清理主题输入框");*/
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.subject")).sendKeys(sub);
			LogObject.info("输入主题信息");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("输入主题异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void fulltext(String text){
		try {
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='APP-editor-iframe']")));
		JsExcutorUtil.inputfulltext(driver,text);
		driver.switchTo().defaultContent();
		LogObject.info("输入正文内容");
		}catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("输入正文内容异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void presstab(String string){
		try{
			KeyBroadUtil.pressTabKey();
			LogObject.info("按下TAB键");
		} catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("按下TAB异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void sendkeytoupload(String context){
		
		try{
			
			if (ExcelUtil.getCelldate(Constant.sheet_login, 1, 4).equals("ie9")
					|| ExcelUtil.getCelldate(Constant.sheet_login, 2, 4)
							.equals("ie9")) {
				driver.findElement(objectmap.getLocator("126Mail.LoginPage.attachfileinIE")).sendKeys(context);
			}else{
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.attachfile")).sendKeys(context);
			}
			LogObject.info("上传附件完成");
		}catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("上传附件异常："+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void paste(String context){
		
		try{
			KeyBroadUtil.setAndctrlVClipboraddata(context);
			LogObject.info("将内容设置到系统粘贴板内");
		} catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("设置粘贴板异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void attachfile(String string) {
		
		try {
			
			//经测试，ie浏览器直接进行click点击，ie浏览器下的元素定位xpath也有改变
			if (ExcelUtil.getCelldate(Constant.sheet_login, 1, 4).equals("ie9")
					|| ExcelUtil.getCelldate(Constant.sheet_login, 2, 4)
							.equals("ie9")) {
				driver.findElement(objectmap.getLocator("126Mail.LoginPage.attachfileinIE")).click();
			}else{
			//chrome内核使用js点击
			JsExcutorUtil.jsclick(driver, driver.findElement(objectmap.getLocator("126Mail.LoginPage.attachfile")));
			}
			LogObject.info("点击附件上传按钮");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("点击附件上传按钮异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void sleep(String time){
		
		WaitUtil.sleep(Integer.parseInt(time));
		LogObject.info("暂停等待"+time+"秒！");
	}
	
	public static void pressenter(String string) {

		try{
			KeyBroadUtil.pressEnterKey();
			LogObject.info("按下ENTER键");
		} catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("按下enter异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void sendmailbuttons(String string) {
		
		List<WebElement> buttons;
		try {
			buttons = driver.findElements(objectmap.getLocator("126Mail.LoginPage.sendmailbutton"));
			buttons.get(0).click();
			LogObject.info("点击发送按钮");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("点击发送按钮异常："+e.getMessage());
			e.printStackTrace();
		}

	}
	
	public static void Assert_String(String asscontext){
		try{
		Assert.assertTrue(driver.getPageSource().contains(asscontext));
		LogObject.info("断言"+asscontext+"成功！");
		}catch(AssertionError e){
			TestSuitByExcel.testresult = false;
			LogObject.error("没有找到指定的字符，断言失败！"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void closebrowser(String string) throws Exception{
		
		driver.quit();
		LogObject.info("关闭浏览器！");
		
	}


}
