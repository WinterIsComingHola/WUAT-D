package com.xuxinyu.uidriver.utils;

import org.openqa.selenium.WebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.xuxinyu.uidriver.basehandle.RemoteGridParams;
import com.xuxinyu.uidriver.basehandle.Constant;

/**
 * 实现rgid模式下，获取远端driver实例
 * **/
public class GetRemoteDriver {

	private static DesiredCapabilities capability = null;

	public static WebDriver getDriver(RemoteGridParams remotegridoarams)
			throws MalformedURLException {

		if (remotegridoarams.getBrowserName().equals("firefox")) {
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName(remotegridoarams.getBrowserName());
			capability.setPlatform(remotegridoarams.getPlatform());
			capability.setCapability("firefox_binary", Constant.firefoxpath);

		} else if (remotegridoarams.getBrowserName()
				.equals("internet explorer")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName(remotegridoarams.getBrowserName());
			capability.setPlatform(remotegridoarams.getPlatform());

		} else if (remotegridoarams.getBrowserName().equals("chrome")) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName(remotegridoarams.getBrowserName());
			capability.setPlatform(remotegridoarams.getPlatform());
		}

		return new RemoteWebDriver(new URL(Constant.remoteNoteurl), capability);

	}

}
