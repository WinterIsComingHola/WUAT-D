package com.xuxinyu.uidriver.basehandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



/***
 * 创建文件处理工具类
 * 用于文件相关的处理
 * */

public class FileUtil {
	
	public boolean creatFile(String destfilename){
		
		File file = new File(destfilename);//用字符串创建一个File对象，字符串是文件名称
		if(file.exists()){
			LogObject.error("创建单个文件"+destfilename+"失败，文件已存在！");
			return false;
		}else if(destfilename.endsWith(File.separator)){
			/*destfilename.endsWith(File.separator)
			 * File.separator主要用于获取文件名称中的路径格式符，Windows一般为“\”，
			 * linux一般为“/”，如果destfilename以这个符号结尾，那么是一个路径而不是文件
			 * String.endsWith(args)主要是和args进行比对，是否是以args结尾，返回boolean
			 * */
			LogObject.error("创建单个文件"+destfilename+"失败，目标文件不能是目录！");
			return false;
		}
		
		/*File对象表示的是一个文件或者文件夹
		 * 如File file = new File("D:\\rs\\test\\xuxinyu.text");
		 * 如果使用file.createNewFile();那么会在test下创建xuxinyu.text，前提是test要存在否则抛异常
		 * 如果使用file.mkdirs();那么会在test下创建xuxinyu.text文件夹，前提是test要存在否则抛异常
		 * 如果使用file.getParentFile().createNewFile()，那么会在rs下创建名为test的文件，前提是rs要存在否则抛异常
		 * 如果使用file.getParentFile().mkdirs()，那么会在rs下创建名称为test的文件夹，前提是rs要存在否则抛异常
		 * 如果上述任意一个操作，在对应的目录下已经存在相同的文件夹或者文件，则自动略过处理
		 * 
		 * */
		if(!file.getParentFile().exists()){
			LogObject.error("目标文件所在不目录不存在，准备创建它！");
			if(!file.getParentFile().mkdirs()){
				LogObject.error("创建目标文件所在目录失败！");
				return false;
			}
		}
		
		try {
			if(file.createNewFile()){
				LogObject.info("创建目标文件"+destfilename+"成功！");
				return true;
			}else{
				LogObject.error("创建目标文件"+destfilename+"失败！");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean creatdir(String destdir){
		
		File dir = new File(destdir);
		
		if(dir.exists()){
			LogObject.error("创建文件夹"+dir+"失败，文夹已存在！");
			return false;
	}
		if(dir.mkdirs()){
			LogObject.info("创建文件夹"+dir+"成功！");
			return true;
		}else{
			LogObject.error("创建文件夹"+dir+"失败！");
			return false;

		}
		
	}
	
	/*读取csv文件的工具类，返回一个二维的对象数组，可以给testng的dataPrivoder注解方法使用*/
	
	public Object[][] readcsvfile(String filename){
		
		List<Object[]> records = new ArrayList<Object[]>();
		String record;
		try {
			/*BufferedReader类的IO处理理解
			 * BufferedReader是Reader的子类，可以修饰任意一种Reader，用于缓冲区处理
			 * 而InputStreamReader是一种Reader（子类），可以放入BufferedReader中
			 * 
			 * InputStreamReader可用于对字符编码进行设置，他可以对串流（InputStream）
			 * 进行修饰，使用方法InputStreamReader(InputStream,charset)
			 * 而FileInputStream是一种串流，可以放入InputStreamReader中
			 * 
			 * FileInputStream主要用于文件类的串流读取，是InputStream的子类
			 * 另外系统的System.in也是串流的一种实例，用于系统输入，所以可以这样获取系统输入：
			 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
			 * */
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename),"UTF-8"));
			
			try {
				br.readLine();//readLine方法读取一行数据并以换行字符为依据。这里读取csv文件的第一行标题行
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				while((record=br.readLine())!=null){
					//while循环读取文件并把内容存入records中
					String fields[] = record.split(",");
					records.add(fields);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*定义一个二维Object数组存放list中的字符串数组
		 * list中的每个元素就是一个字符串数组
		 * new Object[records.size()][]表示这个二维数组内部小数组的个数就是list的长度
		 * 形式是这样的{{"nihao","haha","woshishui"},
		 * 		   {"nihao","haha","woshishui","hahaha"}}
		 * 这种表示list长度为2，也就是两个小数组
		 * 
		 * 二维数组results[]表示每个小数组自身，所以用for循环将list一一取出并赋值给小数组
		 * 在明确下：results表示二维数组实例
		 * results[]表示二维中的每个一维小数组实例
		 * results[][]表示二维数组中的每个元素
		*/
		Object[][] results = new Object[records.size()][];
		for(int i = 0;i<records.size();i++){
			results[i] = records.get(i);
		}
		
		return results;
		
	}
	
	
	/*读取xls文件工具类，返回一个二维的对象数组，可以给testng的dataPrivoder注解方法使用*/
	
	public Object[][] readxlsfile(String filepath,String filename,
			String sheetname) throws IOException{
		
		File file = new File(filepath+"\\"+filename);
		
		FileInputStream inputstream = new FileInputStream(file);
		
		Workbook workbook = null;
		
		/*String.indexOf(".")，表示对文件名字符串中的.进行匹配，并返回.所在的索引号，从0开始
		 * 比如"11.txt".indexOf(".")，返回2
		 * String.substring(int)表示对字符串第几个字符开始进行切割，返回切割后的一个新的字符串，从1开始
		 * 比如"11.txt".substring(2)，返回".txt"
		 * */
		String fileexname = filename.substring(filename.indexOf("."));
		
		//根据后缀名确认建立具体的Workbook实例
		if(fileexname.equals(".xlsx")){
			workbook = new XSSFWorkbook(inputstream);
		}else if(fileexname.equals(".xls")){
			workbook = new HSSFWorkbook(inputstream);
		}
		
		//Workbook的getSheet(sheetname)方法返回一个Sheet对象
		Sheet sheet = workbook.getSheet(sheetname);
		
		//获取这个sheet页中的行数（最后一行号减去第一行号）
		int rowcount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		System.out.printf("sheet.getLastRowNum()表示为索引号%d，实际为第%d行，sheet.getFirstRowNum()表示为索引号%d，实际为第%d行"
				+ "两个参数相减后+1即为行数%d%n", sheet.getLastRowNum(),sheet.getLastRowNum()+1
				,sheet.getFirstRowNum(),sheet.getFirstRowNum()+1,
				rowcount+1);
		
		List<Object[]> records = new ArrayList<>();
		
		for(int i = 1 ;i<=rowcount;i++){
			/*sheet.getRow(i)解释：
			 * 返回以0为第一个索引的逻辑行(非物理行)。
			 * 。
			 * 当然第一行代表表格的第二行，这是真正需要的值
			 */
			Row row = sheet.getRow(i);
			
			String[] fields = new String[row.getLastCellNum()];
			for(int j=0;j<row.getLastCellNum();j++){
				//获取每一行的值
				fields[j] = row.getCell(j).getStringCellValue();
			}
			records.add(fields);
			
		}
		inputstream.close();
		workbook.close();
		
		//将records转换为二维数组并返回
		Object[][] results = new Object[records.size()][];
		for(int i = 0;i<records.size();i++){
			results[i] = records.get(i);
		}
		
		return results;
	}

}
