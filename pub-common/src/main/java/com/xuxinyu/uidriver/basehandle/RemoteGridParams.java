package com.xuxinyu.uidriver.basehandle;
import org.openqa.selenium.Platform;

public class RemoteGridParams {
	
	private String browserName ;
//	private String os;
	private Platform platform;
	
	public void setRemoteParams(String browserName,Platform platform){
		this.browserName = browserName;
		this.platform = platform;
	}
	
	public String getBrowserName(){
		return browserName;
	}
	
	public Platform getPlatform(){
		return platform;
	}

}
