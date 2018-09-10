package com.xuxinyu.uidriver.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 当前类封装了js的一些动作
 * jsclick()可以用于js点击对应元素的按钮
 * setattribute()可以用于修改页面上特定元素的属性值
 * removeattribute()可以对特定元素属性进行删除
 * hightlight()高亮对应元素
 * inputfulltext()操作富文本
 * 
 * executeScript()方法用于执行js的脚本
 * 第一个参数是必填字段，表示要执行的脚本内容，用"js代码(args[])"括起来
 * 后面的参数长度不定，分别表示要传入这个js脚本的参数
 * 如果要在js中调用executeScript()的参数，可以使用arguments[0]、arguments[1]进行代替，以此类推
 * 
 * js脚本其实是一个匿名的参数不定的js函数
 * 
 * **/

public class JsExcutorUtil {

	public static void jsclick(WebDriver driver, WebElement element)
			throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		try {
			// ||表示逻辑或，&&表示逻辑且
			if (element.isEnabled() || element.isDisplayed()) {
				System.out.println("js单击页面元素");
				jse.executeScript("arguments[0].click();", element);

			} else {
				System.out.println("页面上的元素无法单击");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("页面元素没有附加在网页上");
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			System.out.println("页面上没有这个元素");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("无法完成单击动作");
			e.printStackTrace();
		}

	}

	public static void setattribute(WebDriver driver, WebElement element,
			String attributeName, String value) {
		JavascriptExecutor jes = (JavascriptExecutor) driver;
		jes.executeScript(
				"arguments[0].setAttribute(arguments[1],arguments[2]);",
				element, attributeName, value);

	}

	public static void removeattribute(WebDriver driver, WebElement element,
			String attributeName) {
		JavascriptExecutor jes = (JavascriptExecutor) driver;
		jes.executeScript(
				"arguments[0].removeAttribute(arguments[1],arguments[2]);",
				element, attributeName);
	}

	// 高亮对应的元素值
	public static void hightlight(WebDriver driver, WebElement element) {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("arguments[0].setAttribute('style',arguments[1]);",
				element, "background:yellow;border:2px solid red;");

	}
	
	public static void inputfulltext(WebDriver driver,String context) {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("document.getElementsByTagName('body')[0].innerHTML='<b>"+context+"<b>'");

	}

}
