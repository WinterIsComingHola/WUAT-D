package com.xuxinyu.uidriver.utils;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.xuxinyu.uidriver.basehandle.FileUtil;
import com.xuxinyu.uidriver.basehandle.DateFormatUtil;
/**实现对屏幕截图
 * 的工具类
 * **/

public class ScreenShotUtil {

	public static void takescreenshot(WebDriver driver,
			String screenshotfilepath) {

		Date date = new Date();

		/* 创建年月日的目录 */
		String dirbydate = screenshotfilepath + "\\"
				+ String.valueOf(DateFormatUtil.getYear(date)) + "-"
				+ String.valueOf(DateFormatUtil.getMouth(date)) + "-"
				+ String.valueOf(DateFormatUtil.getDay(date));
		if (!new File(dirbydate).exists()) {
			new FileUtil().creatdir(dirbydate);
		}

		/* 创建年月日时分秒为文件名的png文件 */
		String shotscreenfilebydate = dirbydate + "\\"
				+ String.valueOf(DateFormatUtil.getHour(date)) + "-"
				+ String.valueOf(DateFormatUtil.getMinute(date)) + "-"
				+ String.valueOf(DateFormatUtil.getSecond(date)) + ".png";

		File srcshotscreen = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(srcshotscreen, new File(shotscreenfilebydate));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
