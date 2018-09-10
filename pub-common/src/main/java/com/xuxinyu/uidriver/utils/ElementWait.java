package com.xuxinyu.uidriver.utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;
import com.xuxinyu.uidriver.basehandle.LogObject;

/**
 * 实现页面元素的显式等待，并根据结果返回布尔值
 * 因为关键字驱动的框架中，业务上的显式等待基本上在xls文件中做了定义，所以这个类主要用于一些需要boolean的地方来调用
 * 
 * **/
public class ElementWait {
	
	public static boolean ElementIsClickable(WebDriver driver,By by){
	 try{
		new WebDriverWait(driver,10).until(
				ExpectedConditions.elementToBeClickable(by));
		}catch(NoSuchElementException e){
			LogObject.error("页面元素不可点击！");
			return false;
		}
		return true;
	}
	
	public static boolean ElementIsLocaled(WebDriver driver,By by){
		try{
			new WebDriverWait(driver,10).until(
					ExpectedConditions.presenceOfElementLocated(by));
		}catch(NoSuchElementException e){
			LogObject.error("页面元素不存在于页面！");
			return false;
		}
		return true;
	}
	
	public static boolean ElementIsSelected(WebDriver driver,By by){
		try{
			new WebDriverWait(driver,10).until(
					ExpectedConditions.elementToBeSelected(by));
		}catch(NoSuchElementException e){
			LogObject.error("页面元素未被选中！");
			return false;
		}
		return true;
	}
	
	public static boolean TextInElement(WebDriver driver,By by,String text){
		try{
			new WebDriverWait(driver,10).until(
					ExpectedConditions.textToBePresentInElementLocated(by,text));
		}catch(NoSuchElementException e){
			LogObject.error("页面元素中不包含"+"“"+text+"”"+"！");
			return false;
		}
		return true;
	}
	
	public static boolean ValueInElement(WebDriver driver,By by,String text){
		try{
			new WebDriverWait(driver,10).until(
					ExpectedConditions.textToBePresentInElementValue(by,text));
		}catch(NoSuchElementException e){
			LogObject.error("页面元素的value值中不包含"+"“"+text+"”"+"！");
			return false;
		}
		return true;
	}
	
	public static boolean TextInTitle(WebDriver driver,String text){
		try{
			new WebDriverWait(driver,10).until(
					ExpectedConditions.titleContains(text));
		}catch(NoSuchElementException e){
			LogObject.error("页面标题中不包含"+"“"+text+"”"+"！");
			return false;
		}
		return true;
	}

}
