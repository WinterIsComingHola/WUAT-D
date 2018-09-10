package com.xuxinyu.action.autohandle;

import com.xuxinyu.action.keyword.SendMailAction;
import com.xuxinyu.action.keyword.LoginAction;
import java.lang.reflect.*;

import com.xuxinyu.action.keyword.WebAction;
import com.xuxinyu.uidriver.basehandle.*;
import com.xuxinyu.uidriver.utils.*;

/*利用反射机制实现excel文件中，对应的方法的调用
 * 本次同时对excel中的多个sheet中的用例进行组合调用*/

public class TestSuitByExcel {
	private static Method[] metodslogin;
	private static Method[] metodssenmail;
	private static Method[] methodswebaction;
	private static String keyword;
	private static String keyvalue;
	private static LoginAction loginaction;
	private static SendMailAction sendmailaction;
	private static WebAction webAction;
	private static int testfirststep;
	private static int testlaststep;
	private static String sheetname;
	private static String testcasenameid;
	private static String testcasesuitrunflag;
	private static String testcaserunflag;
	public static boolean testresult;

	
	public static void ActionSuitsHandle() throws Exception{
		
		loginaction = new LoginAction();
		sendmailaction = new SendMailAction();
		webAction = new WebAction();
		// 获取KeyWordsAction类的所有方法
		metodslogin = loginaction.getClass().getMethods();
		metodssenmail = sendmailaction.getClass().getMethods();
		methodswebaction = webAction.getClass().getMethods();
		// 加载ExcelUtil类的相关数据
		ExcelUtil.setExcelfileinmaven("xlsx");

		// 获取测试用例集合的sheet中的最后一行的索引号
		int casecount = ExcelUtil.getLastrow(Constant.sheet_ceses);

		// 从第二行开始循环
		for (int i = 1; i <= casecount; i++) {
			// 获取测试用例名称id和是否执行的flag testcaserunflag
			testcasenameid = ExcelUtil.getCelldate(Constant.sheet_ceses, i, 0);
			testcasesuitrunflag = ExcelUtil.getCelldate(Constant.sheet_ceses, i, 3);

			// 如果testcaserunflag=y，则此条用例集参与执行
			if (testcasesuitrunflag.equalsIgnoreCase("y")) {
				
				LogObject.StartTestCase(testcasenameid);
				
				// 获取本行用例集所对应的sheet名
				sheetname = ExcelUtil.getCelldate(Constant.sheet_ceses,
						i, 2);

				// 分别获得对应的sheet内，和本行用例集的用例名称id一致的用例起始行和结束行的索引号，关键步骤
				
				testfirststep = ExcelUtil.getFirstRowContainsTestNameIndex(
						sheetname, testcasenameid, 0);
				testlaststep = ExcelUtil.getLastRowContainsTestNameIndex(
						sheetname, testcasenameid, testfirststep, 0);
				//设置测试结果的boolean值
				testresult = true;

				// for的第一个参数再前面初始化过了，可以省略
				// 因为对应需要执行的用例行已经算出来，则对这些用例进行循环
				for (; testfirststep <= testlaststep; testfirststep++) {
					//获取对应sheet中是否执行(Y/N)列的值
					testcaserunflag = ExcelUtil.getCelldate(sheetname, testfirststep, 5);
					
					// 反射执行对应方法		
					if (testcaserunflag.equalsIgnoreCase("y")) {
						// 获取对应sheet中需要执行的用例的关键字和关键字值
						keyword = ExcelUtil.getCelldate(sheetname,
								testfirststep, 3);
						LogObject.info("关键字是：" + keyword);
						keyvalue = ExcelUtil.getCelldate(sheetname,
								testfirststep, 4);
						LogObject.info("关键字的值是：" + keyvalue);
						excuteAction();
						// System.out.println("哈哈你成功了！執行"+ExcelUtil.getCelldate(sheetname,
						// testfirststep, 0)+"： "+testfirststep+"次！");
					}

				}
				//对本次用例集的测试结果进行判断，如果用例集中有任何一个方法执行失败，则整个用例集都是失败，并写入excel
				if(testresult == false){
					ExcelUtil.setCelldata(Constant.sheet_ceses, i, 4, "测试失败");
					LogObject.EndTestCase(testcasenameid);
					break;
				}else if(testresult == true){
					ExcelUtil.setCelldata(Constant.sheet_ceses, i, 4, "测试通过");
					LogObject.EndTestCase(testcasenameid);}
			}

		}
		
		
	}
	


	/* 利用反射，获取方法名去和keyword匹配，如果相等，就执行方法 */
	public static void excuteAction() throws Exception {
		/*针对同时执行多个sheet中的用例和多个Action类中方法的情况，需要分别
		 * 去对sheetname进行匹配判断*/
		if (sheetname.equals(Constant.sheet_login)) {
			for (int i = 0; i < metodslogin.length; i++) {
				// 如果方法名和keyword相等，这个是提前设定好的
				if (methodswebaction[i].getName().equals(keyword)) {
					// 反射执行方法
					methodswebaction[i].invoke(webAction, keyvalue);
					//对单个方法的执行结果进行判断，如果执行成功则在excel中输入成功，否则输入失败
					if(testresult == true){
						ExcelUtil.setCelldata(sheetname, testfirststep, 6, "测试通过");
						break;
					}else{
						ExcelUtil.setCelldata(sheetname, testfirststep, 6, "测试失败");
						break;
					}
					
				}
			}
		} else if (sheetname.equals(Constant.sheet_sendmail)) {
			for (int i = 0; i < metodssenmail.length; i++) {
				// 如果方法名和keyword相等，这个是提前设定好的
				if (metodssenmail[i].getName().equals(keyword)) {
					// 反射执行方法
					metodssenmail[i].invoke(sendmailaction, keyvalue);
					//对单个方法的执行结果进行判断，如果执行成功则在excel中输入成功，否则输入失败
					if(testresult == true){
						ExcelUtil.setCelldata(sheetname, testfirststep, 6, "测试通过");
						break;
					}else{
						ExcelUtil.setCelldata(sheetname, testfirststep, 6, "测试失败");
						break;
					}
				}
			}

		}

	}
	
	

	/*
	 * @BeforeMethod public void beforeMethod() {
	 * System.setProperty("webdriver.firefox.bin",
	 * "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); ProfilesIni
	 * allprofiles = new ProfilesIni(); FirefoxProfile profiles
	 * =allprofiles.getProfile("profileWebdriver");
	 * 
	 * 
	 * driver = new FirefoxDriver(profiles);
	 * 
	 * 
	 * }
	 * 
	 * @AfterMethod public void afterMethod() { driver.quit(); }
	 */

}
