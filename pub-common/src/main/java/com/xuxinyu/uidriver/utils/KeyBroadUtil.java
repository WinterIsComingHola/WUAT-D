package com.xuxinyu.uidriver.utils;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**实现键盘操作的封装
 * 目前主要包括TAB和ENTER键
 * 实现向系统剪贴板传送数据并按下ctrl+v完成粘贴
 * **/
public class KeyBroadUtil {
	
	//按下TAB键的封装方法
	public static void pressTabKey(){
		Robot robot = null;
		try{
			robot = new Robot();
			
		}catch(AWTException e){
			e.printStackTrace();
		}
		//调用keyPress()方法，传入KeyEvent.VK_TAB实现按下tab键
		robot.keyPress(KeyEvent.VK_TAB);
		//调用keyRelease()方法，传入KeyEvent.VK_TAB实现释放tab键
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
	//按下Enter键的封装方法
	public static void pressEnterKey(){
		Robot robot = null;
		try{
			robot = new Robot();
			
		}catch(AWTException e){
			e.printStackTrace();
		}
		//调用keyPress()方法，传入KeyEvent.VK_TAB实现按下tab键
		robot.keyPress(KeyEvent.VK_ENTER);
		//调用keyRelease()方法，传入KeyEvent.VK_TAB实现释放tab键
		robot.keyRelease(KeyEvent.VK_ENTER);
		
	}
	//将指定字符串设定为剪贴板内容，然后执行粘贴操作
	//将页面焦点切换到输入框中时，调用此函数可以将指定字符粘贴到输入框中
	public static void setAndctrlVClipboraddata(String str){
		//选择要进入剪贴板的内容，外部传入
		StringSelection strselect = new StringSelection(str);
		//向剪贴板set内容
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strselect, null);
		
		
		Robot robot = null;
		try{
			robot = new Robot();
			
		}catch(AWTException e){
			e.printStackTrace();
		}
		
		//调用keyPress()方法，传入KeyEvent.VK_CONTROL+VK_V实现按下CTRL+V键
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		//调用keyRelease()方法，传入KeyEvent.VK_CONTROL+VK_V实现释放CTRL+V键
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}

}
