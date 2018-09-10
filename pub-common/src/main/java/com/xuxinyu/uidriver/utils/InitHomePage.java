package com.xuxinyu.uidriver.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.xuxinyu.uidriver.basehandle.LogObject;


/**初始化首页
 * **/
public class InitHomePage extends LoadableComponent<InitHomePage> {
	private static WebDriver driver = null;
	private static String Url = null;
	
	public InitHomePage(WebDriver driver,String URL){
		InitHomePage.driver = driver;
		InitHomePage.Url = URL;
		driver.get(Url);
		
	}
	
	@Override
	//因为先执行isloaded，所以将get方法放在构造函数中，这里空白即可
	public void load(){}
	
	@Override
	//打开首页后将先进行isloaded的执行
	public void isLoaded(){
		WebDriverWait wait = new WebDriverWait(driver,10);
		 try{
			Boolean checkpage = 
					wait.until(
							new ExpectedCondition<Boolean>(){
								@Override
								public Boolean apply(WebDriver d){
									LogObject.info("等待页面加载完全！");
									Boolean result = 
											(Boolean)((JavascriptExecutor)d).executeScript("return document.readyState == 'complete'");
									return result;
								}
							});
			//Assert.assertTrue(checkpage);
			if(checkpage==true){
			LogObject.info("登陆页面加载成功！");
			}
		 }catch(WebDriverException e){
			 LogObject.error("页面加载异常"+e.getMessage());
			 e.printStackTrace();
		 }
		 
		 if(!ElementWait.ElementIsClickable(driver, By.linkText("网易首页"))){
			 LogObject.error("页面元素没有加载完全");
			 driver.quit();
		 }
	}

}
