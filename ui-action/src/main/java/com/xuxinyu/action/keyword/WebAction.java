package com.xuxinyu.action.keyword;

import com.xuxinyu.action.autohandle.TestSuitByExcel;
import com.xuxinyu.uidriver.basehandle.Constant;
import com.xuxinyu.uidriver.basehandle.LogObject;
import com.xuxinyu.uidriver.utils.ElementObjectMap;
import com.xuxinyu.uidriver.utils.ExcelUtil;
import com.xuxinyu.uidriver.utils.InitHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author xuxinyu
 * @date 2018/7/9 下午3:54
 */
public class WebAction {

    public static WebDriver driver = null;
    public static ElementObjectMap objectmap =  new ElementObjectMap();

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
            driver = new ChromeDriver();
            LogObject.info("加载谷歌浏览器已完成");
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


    public static void click1(String string) {
        try {
            driver.findElement(objectmap.getLocator("webdemo.zujian")).click();
            LogObject.info("执行点击");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("执行点击失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void click2(String string) {
        try {
            driver.findElement(objectmap.getLocator("webdemo.anniu")).click();
            LogObject.info("执行点击");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("执行点击失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void input1(String string) {
        try {
            driver.findElement(objectmap.getLocator("webdemo.input1")).clear();
            LogObject.info("执行清理输入框");
            driver.findElement(objectmap.getLocator("webdemo.input1")).sendKeys(string);
            LogObject.info("执行输入");
            //driver.findElement(objectmap.getLocator("126Mail.LoginPage.account")).click();
            //LogObject.info("执行点击开始");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("执行输入失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void input2(String string) {
        try {
            driver.findElement(objectmap.getLocator("webdemo.input2")).clear();
            LogObject.info("执行清理输入框");
            driver.findElement(objectmap.getLocator("webdemo.input2")).sendKeys(string);
            LogObject.info("执行输入");
            //driver.findElement(objectmap.getLocator("126Mail.LoginPage.account")).click();
            //LogObject.info("执行点击开始");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("执行输入失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void switchiframe(String string) {
        try {
            driver.switchTo().frame(driver.findElement(objectmap.getLocator("webdemo.iframe")));
            LogObject.info("切换iframe");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("切换iframe失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void switchdefault(String string) {
        try {
            driver.switchTo().defaultContent();
            LogObject.info("切换iframe");
        } catch (Exception e) {
            TestSuitByExcel.testresult = false;
            LogObject.error("切换iframe失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closebrowser(String string) {
        driver.close();
        LogObject.info("关闭浏览器！");
    }

}
