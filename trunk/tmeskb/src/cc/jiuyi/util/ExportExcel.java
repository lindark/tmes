package cc.jiuyi.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	 * @param os 输出流
	 * @throws IOException
	 */
	public static void exportExcel(String title,List<String> header,List<Object []> body,OutputStream os) throws IOException{
		
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
        	Object[] objs = body.get(i);
        	for(int y=0;y<objs.length;y++){
        		HSSFCell cell = row1.createCell(y);
        		String cellvalue="";
        		if(objs[y]!=null){
        			cellvalue = ""+objs[y];
        		}
        		cell.setCellValue(cellvalue);
                cell.setCellStyle(style2);
        	}
        }
        
      //让列宽随着导出的列长自动适应  modify by Reece 2016/3/8
        for (int colNum = 0; colNum < header.size(); colNum++) {  
            int columnWidth = sheet.getColumnWidth(colNum) / 256;  
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {  
                HSSFRow currentRow;  
                //当前行未被使用过  
                if (sheet.getRow(rowNum) == null) {  
                    currentRow = sheet.createRow(rowNum);  
                } else {  
                    currentRow = sheet.getRow(rowNum);  
                }  
                if (currentRow.getCell(colNum) != null) {  
                    HSSFCell currentCell = currentRow.getCell(colNum);  
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {  
                        int length = currentCell.getStringCellValue().getBytes().length;  
                        if (columnWidth < length) {  
                            columnWidth = length;  
                        }  
                    }  
                }  
            }  
            sheet.setColumnWidth(colNum, (columnWidth+4) * 256);  
        }
        
        workbook.write(os);
	}
	
	//public 
}
