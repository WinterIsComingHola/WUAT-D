package com.xuxinyu.uidriver.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.openqa.selenium.By;
import com.xuxinyu.uidriver.basehandle.ProLoad;


public class ElementObjectMap {
	
	private Properties pro = null;

	private InputStreamReader file = null;
	
	
	/*无参数方式初始化pro，用于maven环境下读取sources目录的配置文件*/
	
	public ElementObjectMap(){
		
		try {
			pro = ProLoad.getInstance().loadproperties("findElement.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 初始化ElementObjectMap类
	 * 并新建一个FileInputStream流
	 * 将FileInputStream对象加载进Properties中进行配置文件的读取
	 * **/
	public ElementObjectMap(String profilepath){
		
		pro = new Properties();
		try {
			/*fileinput = new FileInputStream(profilepath);*/
			
			file = new InputStreamReader(new FileInputStream(profilepath),"UTF-8");
			pro.load(file);
			
		} catch (FileNotFoundException e) {
		//	LogObject.error("配置文件不存在，请核查！");
			e.printStackTrace();
		} catch(IOException e){
		//	LogObject.error("文件加载异常，请核查");
			e.printStackTrace();
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
		//			LogObject.error("关闭文件流异常，请核查");
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	/**
	 * 读取配置文件内容并将map值和元素类型进行匹配
	 * **/
	public By getLocator(String elementName) throws Exception{
		
		String locator = pro.getProperty(elementName);
		
		String Element_Key_Type = locator.split(":",2)[0];
		String Element_Attribute = locator.split(":",2)[1];
		
		//Element_Attribute = new String(Element_Attribute.getBytes("IOS-8859-1"),"UTF-8");
		
		if(Element_Key_Type.toLowerCase().equals("id")){
			return By.id(Element_Attribute);
		}else if(Element_Key_Type.toLowerCase().equals("name")){
			return By.name(Element_Attribute);
		}else if((Element_Key_Type.toLowerCase().equals("classname"))||(Element_Key_Type.toLowerCase().equals("class"))){
			return By.className(Element_Attribute);
		}else if((Element_Key_Type.toLowerCase().equals("tagname"))||(Element_Key_Type.toLowerCase().equals("tag"))){
			return By.className(Element_Attribute);
		}else if((Element_Key_Type.toLowerCase().equals("link"))||(Element_Key_Type.toLowerCase().equals("linktext"))){
			return By.linkText(Element_Attribute);
		}else if(Element_Key_Type.toLowerCase().equals("partiallinktext")){
			return By.partialLinkText(Element_Attribute);
		}else if((Element_Key_Type.toLowerCase().equals("cssselector"))||(Element_Key_Type.toLowerCase().equals("css"))){
			return By.cssSelector(Element_Attribute);
		}else if(Element_Key_Type.toLowerCase().equals("xpath")){
			return By.xpath(Element_Attribute);
		}else{
		//	LogObject.error("元素查找失败！");
			throw new Exception("输入的Element_Key_Type未在程序中定义："+Element_Key_Type);
			}
		
	}

}
