package cc.jiuyi.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel 导出公用类文件
 * @author weita
 *
 */
public class ExportExcel {
	
	/**
	 * Excel 导出公用类
	 * @param title  标题
	 * @param header  表头
	 * @param body  表体
	 * @param path  路径
	 * @throws IOException
	 */
	public static void exportExcel(String title,List<String> header,List<String []> body,String path) throws IOException{
		
		//声明一个工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//生产一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		//设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		//设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //把字体应用到当前样式
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);
        
        
        //在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<header.size();i++){
        	 String head = header.get(i);
        	 HSSFCell cell = row.createCell(i);
             cell.setCellValue(ThinkWayUtil.null2String(head));
             cell.setCellStyle(style);
        }
        
        for(int i=0;i<body.size();i++){
        	HSSFRow row1 = sheet.createRow(i+1);
        	String[] objs = body.get(i);
        	for(int y=0;y<objs.length;y++){
        		HSSFCell cell = row1.createCell(y);
        		cell.setCellValue(ThinkWayUtil.null2String(objs[y]));
                cell.setCellStyle(style2);
        	}
        }
        
       
        FileOutputStream fout = new FileOutputStream("f:/ceshi.xls");
        workbook.write(fout);
        fout.close();
	}
	
	//public 
}
