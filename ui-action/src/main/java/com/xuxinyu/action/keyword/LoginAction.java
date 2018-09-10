package com.xuxinyu.action.keyword;
//import org.apache.log4j.PropertyConfigurator;
import java.net.MalformedURLException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.xuxinyu.action.autohandle.TestSuitByExcel;
import com.xuxinyu.uidriver.utils.ElementObjectMap;
import com.xuxinyu.uidriver.utils.JsExcutorUtil;
import com.xuxinyu.uidriver.basehandle.Constant;
import com.xuxinyu.uidriver.basehandle.LogObject;
import com.xuxinyu.uidriver.utils.ExcelUtil;
import com.xuxinyu.uidriver.utils.WaitUtil;
import com.xuxinyu.uidriver.utils.InitHomePage;
import com.xuxinyu.uidriver.utils.GetRemoteDriver;
import com.xuxinyu.uidriver.basehandle.RemoteGridParams;

/**映射了xls文件中的关键字和测试代码的对应关系
 * **/

public class LoginAction {
	
	public static WebDriver driver = null;
	public static ElementObjectMap objectmap =  new ElementObjectMap();
	
	/*static{
		//设置log4j配置文件，此时读取的配置文件应该在工程的根目录
		PropertyConfigurator.configure("log4j.properties");
	}*/
	
	/*本方法映射xls文件中的关键字列，方法名要和关键字保持一致，关键字的值将会通过方法参数传入方法内*/
	
	
	public static void remoteweb(String remotebrowser){
		
		RemoteGridParams remotegridparams = new RemoteGridParams();
		if(remotebrowser.equals("ie")||remotebrowser.equals("ie9")){
			try {
				remotegridparams.setRemoteParams("internet explorer", Platform.WIN10);
				driver = GetRemoteDriver.getDriver(remotegridparams);
				LogObject.info("远程加载IE浏览器已完成");
			} catch (MalformedURLException e) {
				TestSuitByExcel.testresult = false;
				LogObject.error("远程加载IE浏览器失败："+e.getMessage());
				e.printStackTrace();
			}
		}else if(remotebrowser.equals("firefox")){
			try {
				remotegridparams.setRemoteParams("firefox", Platform.WIN10);
				driver = GetRemoteDriver.getDriver(remotegridparams);
				LogObject.info("远程加载火狐浏览器已完成");
			} catch (MalformedURLException e) {
				TestSuitByExcel.testresult = false;
				LogObject.error("远程加载火狐浏览器失败："+e.getMessage());
				e.printStackTrace();
			}
		}else if(remotebrowser.equals("chrome")){
			try {
				remotegridparams.setRemoteParams("chrome", Platform.WIN10);
				driver = GetRemoteDriver.getDriver(remotegridparams);
				LogObject.info("远程加载chrome浏览器已完成");
			} catch (MalformedURLException e) {
				TestSuitByExcel.testresult = false;
				LogObject.error("远程加载谷歌浏览器失败："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void openbrowser(String browsername){
		
		if(browsername.equals("ie9")||browsername.equals("ie")){
			System.setProperty("webdriver.ie.driver", "D:\\softs\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			LogObject.info("加载IE浏览器已完成");
		} else if(browsername.equals("firefox")){
			System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			ProfilesIni allprofiles = new ProfilesIni();
		  	FirefoxProfile profiles =allprofiles.getProfile("profileWebdriver");
			driver = new FirefoxDriver(profiles);
			LogObject.info("加载火狐浏览器已完成");
		} else if(browsername.equals("chrome")){
			//System.setProperty("webdriver.chrome.driver", "D:\\softs\\chromedriver v2.27\\chromedriver_win32\\chromedriver.exe");
			driver = new  ChromeDriver();
			LogObject.info("加载谷歌浏览器已完成");
		}
	}
	
	/*用于除了登陆以外的场景获取当前的登陆driver*/
	public static WebDriver getdriver(String browsername) throws Exception {
		if (!(driver == null)) {
			return driver;
		} else {
			if (ExcelUtil.getCelldate(Constant.sheet_login, 1, 5).equals("y")) {
				remoteweb(browsername);
			} else if (ExcelUtil.getCelldate(Constant.sheet_login, 2, 5)
					.equals("y")) {
				openbrowser(browsername);
			}
			return driver;
		}
	}
	
	//对应第二行
	public static void naviget(String URL){
		try{
		//初始化InitHomePage类将直接进行首页初始化的判断操作和页面打开操作
		new InitHomePage(driver,URL);
		LogObject.info("初始化首页，并对首页做元素检查");
		}catch(Exception e){
			TestSuitByExcel.testresult = false;
			LogObject.error("初始化首页失败："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void windowsmax(String string){
		try {
			driver.manage().window().maximize();
			LogObject.info("最大化窗口");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("最大化窗口失败："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//对应第三行，因为本行没有关键字值，所以本方法的参数虽然有但是用不到
	//但是为了后续反射调用方法，仍然需要加一个参数
	public static void account(String string) {
		try {
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.account")).click();
			LogObject.info("点击账号密码标签框");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("点击账号密码标签框失败："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void iframe(String string) {
		try {
			driver.switchTo().frame(driver.findElement(objectmap.getLocator("126Mail.LoginPage.iframeforlogin")));
			LogObject.info("切换iframe");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("切换iframe失败："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void username(String userName) {
		try {
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.username")).clear();
			LogObject.info("清空用户名输入框");
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.username")).sendKeys(userName);
			LogObject.info("输入用户名账号");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("在输入用户名输入框时出现异常："+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void password(String passwd) {
		try {
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.password")).clear();
			LogObject.info("清空密码输入框");
			driver.findElement(objectmap.getLocator("126Mail.LoginPage.password")).sendKeys(passwd);
			LogObject.info("输入用户密码");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("在输入密码时出现异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void loginbutton(String string) {
		try {
			//经测试，ie浏览器无法直接用click点击，故而需要调用js点击
			if (ExcelUtil.getCelldate(Constant.sheet_login, 1, 4).equals("ie")
					|| ExcelUtil.getCelldate(Constant.sheet_login, 2, 4)
							.equals("ie")) {
				JsExcutorUtil.jsclick(driver, driver.findElement(
								objectmap.getLocator("126Mail.LoginPage.submitbutton")));
			}else{
			driver.findElement(
					objectmap.getLocator("126Mail.LoginPage.submitbutton"))
					.click();
			}
			LogObject.info("点击登陆按钮");
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("点击登陆按钮出现异常：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void WaitFor_Element(String xpathexpression) {
		
		try {
			WaitUtil.waitElement(driver,objectmap.getLocator(xpathexpression));
			LogObject.info("显式等待完成，元素是："+xpathexpression);
		} catch (Exception e) {
			TestSuitByExcel.testresult = false;
			LogObject.error("显式等待出现异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void closebrowser(String string) {
		driver.close();
		LogObject.info("关闭浏览器！");
	}

}
