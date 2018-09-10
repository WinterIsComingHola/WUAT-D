package com.xuxinyu.uidriver.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.xuxinyu.uidriver.basehandle.Constant;
import com.xuxinyu.uidriver.basehandle.ProLoad;

/**
 * excel文件工具类，根据传入的行和列值进行单元格的读取和写入
 * **/
public class ExcelUtil {
	private static Workbook wookbook;
	/*注意：因为需要同时操作多个sheet，所以不需要在域成员中定义sheet
	 * 而是在各个方法中各自定义
	 * private static Sheet sheet;*/
	//private static Row row;
	//private static Cell cell;


	
	/**
	 * 传入sheetname，加载excel文件
	 * 支持xls和xlsx两种格式
	 * **/
	public static void setExcelfile(){
		
		
		File file = new File(Constant.excelpath);
		try {
			FileInputStream excelfile = 
					new FileInputStream(file);
			String filename = file.getName();
			
			/*String.indexOf(".")，表示对文件名字符串中的.进行匹配，并返回.所在的索引号，从0开始
			 * 比如"11.txt".indexOf(".")，返回2
			 * String.substring(int)表示对字符串第几个字符开始进行切割，返回切割后的一个新的字符串，从1开始
			 * 比如"11.txt".substring(2)，返回".txt"
			 * 获取文件名的后缀
			 * */
			String xors = filename.substring(filename.indexOf("."));
			
			//根据文件名后缀进行wookbook的实例化
			if(xors.equals(".xls")){
				wookbook = new HSSFWorkbook(excelfile);
			}else if(xors.equals(".xlsx")){
				wookbook = new XSSFWorkbook(excelfile);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		//sheet = wookbook.getSheet(sheetname);
	}
	
	/**maven环境，读取resources目录下的excel，加载excel文件
	 * 支持xls和xlsx两种格式
	 * **/
	public static void setExcelfileinmaven(String xlsorxlsx) {
		
		InputStream inputstream = 
				ProLoad.getInstance().loadexcel("关键字驱动测试用例2.xlsx");
		try{
			if(xlsorxlsx.equals("xls")){
				wookbook = new HSSFWorkbook(inputstream);
			}else if(xlsorxlsx.equals("xlsx")){
				wookbook = new XSSFWorkbook(inputstream);
			}
			}catch(IOException e){
			e.printStackTrace();
			}
	}
	
	
	@SuppressWarnings("deprecation")
	public static String getCelldate(String sheetName,int rownum,int cellnum) throws Exception{
		
		Sheet sheet = wookbook.getSheet(sheetName);
		//根据传入的行和列，获取指定的单元格
		//行号和列号从0开始，所以实际的单元格应该是行数和列数加1
		Cell cell = sheet.getRow(rownum).getCell(cellnum);
		
		String celldata = "";
		
		/*判断单元格内容的类型，如果单元格为字符串型，则使用getStringCellValue()获取
		 * 如果单元格是int或者double型，则进行类型转换*/
		if(cell.getCellType()==Cell.CELL_TYPE_STRING){
			celldata = cell.getStringCellValue();
		}else{
			celldata = String.valueOf(Math.round(cell.getNumericCellValue()));
		}
		
		
		return celldata;
	}
	
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void setCelldata(String sheetName,int rownum,int cellnum,String result) throws Exception{
		
		Sheet sheet = wookbook.getSheet(sheetName);
		Row row = sheet.getRow(rownum);
		Cell cell = row.getCell(cellnum,row.RETURN_BLANK_AS_NULL);
		
		if(cell ==null){
			//当单元格为空的时候，创建单元格
			cell = row.createCell(cellnum);
			cell.setCellValue(result);
		}else{
			//单元格存在则直接调用setCellValue方法
			cell.setCellValue(result);
		}
		
		FileOutputStream fileout = new FileOutputStream(Constant.excelpath);
		wookbook.write(fileout);
		fileout.flush();
		fileout.close();
	}
	
	/*返回sheet中的所有数据并以Object[][]形式返回给testng的dataprovider*/
	public static Object[][] getFullDate(String sheetName) throws Exception{
		Sheet sheet = wookbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		/*System.out.println("sheetpro.getLastRowNum()"+sheet.getLastRowNum());
		System.out.println("sheetpro.getFirstRowNum()"+sheet.getFirstRowNum());
		*///定义一个Object[]用来存放一整行的数据
		List<Object[]> records = new ArrayList<>();
		
		//对每一行进行循环，把每一行的每一列数据组成一个字符串数组
		for(int i=1;i<=rowcount ;i++){
			Row hang = sheet.getRow(i);
			/*System.out.println("hang.getFirstCellNum()"+hang.getFirstCellNum());
			System.out.println("hang.getLastCellNum()"+hang.getLastCellNum());
			*///读取列的数量是总数减去最后两列，注意，getLastCellNum()的值是11
			String fields[] = new String[hang.getLastCellNum()-2];
			//如果倒数第二列的单元格的值是y，则本行数据参与执行
			if(hang.getCell(hang.getLastCellNum()-2).getStringCellValue().equals("y")){
				for(int j = 0;j<(hang.getLastCellNum()-2);j++){
					
					//判断单元格的格式是字符还是数字，字符用
					if(hang.getCell(j).getCellType()==Cell.CELL_TYPE_STRING){
						//获取hang中的每一个cell并赋值到fields数组，直至循环完毕（字符串条件下）
						fields[j] = hang.getCell(j).getStringCellValue();
					}else{
						//获取hang中的每一个cell并赋值到fields数组，直至循环完毕（数字条件下，进行类型转换）
						fields[j] = String.valueOf(Math.round(hang.getCell(j).getNumericCellValue()));
					}
				}
				//将每一行数据（fields[]）add进records中
				records.add(fields);
			}
			
		}
		
		Object[][] results = new Object[records.size()][];
		
		for(int i = 0;i<records.size();i++){
			results[i] = records.get(i);
		}
		return results;
	}
	
	//获取最后一个列数的索引号，和实际列数相等，比如有10列，那就是10，但是第一列仍然会是0，这个地方应该会是个bug
	public static int getLastcol(String sheetName){
		Sheet sheet = wookbook.getSheet(sheetName);
		return sheet.getRow(0).getLastCellNum();
	}
	
	//获取最后一个行数的索引号，因为第一个值为0，比实际行数少1，比如有10行，那就是9
	public static int getLastrow(String sheetName){
		Sheet sheet = wookbook.getSheet(sheetName);
		return sheet.getLastRowNum();
	}
	
	/*本方法在当前sheet中，如果testcaseName被匹配到，则返回第一个testcaseName的索引号，
	 * 注意：会比实际excel中的行号小1
	 * */
	public static int getFirstRowContainsTestNameIndex(String sheetName,
			String testcaseName,int col) throws Exception{
		
		int i;
		for( i=0;i<=ExcelUtil.getLastrow(sheetName);i++){
			if(ExcelUtil.getCelldate(sheetName, i, col).equalsIgnoreCase(testcaseName)){
				break;
			}
		}
		return i;
	}
	
	/*本方法在当前sheet中，如果testcaseName被匹配到，则返回最后一个testcaseName
	 * 的索引号。
	 * 注意：会比实际excel中的行号小1*/
	public static int getLastRowContainsTestNameIndex(String sheetName,
			String testcaseName,int testnamestartnum,int col) throws Exception{
		
		for(int i =	 testnamestartnum;i<=ExcelUtil.getLastrow(sheetName);i++){
			
			if(!testcaseName.equalsIgnoreCase(ExcelUtil.getCelldate(sheetName, i, col))){
				
				return i-1;
			}
		}
		int num = ExcelUtil.getLastrow(sheetName);
		return num;
	}
	
	
	/*public static void main(String args[]){
		 
		setExcelfile();
		try {
			
			int j =getFirstRowContainsTestNameIndex("登陆","login02",0);
			System.out.println(j);
			
			int i =getLastRowContainsTestNameIndex("发送邮件","SendMail01",1,0);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
		
		/* 
		setExcelfile(Constant.ExcelSheetName);
		
		try {
			String aa = getCelldate(4,0);
			System.out.println(aa);
			
			setCelldata(1,5,"夫子庙改q");
			String aaq = getCelldate(1,5);
			System.out.println(aaq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	

}
