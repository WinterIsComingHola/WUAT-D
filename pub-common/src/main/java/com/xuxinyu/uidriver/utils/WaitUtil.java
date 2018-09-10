package com.xuxinyu.uidriver.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	//调用此方法进行线程休眠
	public static void sleep(long millisecond){
		
			try {
				Thread.sleep(millisecond);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	//调用此方法进行显示等待。如果元素出现则操作元素
	public static void waitElement(WebDriver driver,String xpathexpression){
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathexpression)));
	}
	//调用此方法进行显示等待。如果元素出现则操作元素，重载waitElement
	public static void waitElement(WebDriver driver,By by){
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

}
