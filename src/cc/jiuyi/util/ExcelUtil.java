package cc.jiuyi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;



/**
 * 工具类 - Excel操作
 */

public class ExcelUtil {
	private HSSFCellStyle blackStyle;
	private HSSFCellStyle titleStyle;
	private HSSFCellStyle cellStyle;
	private HSSFCellStyle cellLeftStyle;
	private HSSFCellStyle noticeStyle;
	private HSSFFont titleFont; 
	private HSSFFont cellFont; 
	private HSSFFont noticeFont; 
	private HSSFFont blackFont; 
	private HSSFCellStyle upStyle;
	
	public ExcelUtil() {
		
	}
	

	public void init(HSSFWorkbook workbook) {
		this.cellLeftStyle=this.createTitleStyle(workbook);
	    // 设置这些样式
		cellLeftStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellLeftStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		cellLeftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellLeftStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		cellLeftStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		cellLeftStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		cellLeftStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		cellLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);	
		
		this.upStyle=this.createTitleStyle(workbook);
		 // 设置这些样式
		upStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		upStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		upStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		upStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		upStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		upStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		upStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);	
		
		this.noticeStyle=this.createTitleStyle(workbook);
	    // 设置这些样式
 		noticeStyle.setFillForegroundColor(HSSFColor.WHITE.index);
 		noticeStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
 		noticeStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
 		noticeStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
 		noticeStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
 		noticeStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
 		noticeStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
 		noticeStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
 		  // 标题字体
 		noticeFont = workbook.createFont(); 
 		noticeFont.setColor(HSSFColor.BLACK.index); 
 		noticeFont.setFontHeightInPoints((short) 20); 
 		noticeFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
 		noticeFont.setFontName("宋体");
        // 把字体应用到当前的样式
		noticeStyle.setFont(noticeFont);
		
		this.titleStyle=this.createTitleStyle(workbook);
	     // 设置这些样式
		titleStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index); 
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
        // 标题字体
		titleFont = workbook.createFont(); 
		titleFont.setColor(HSSFColor.BLACK.index); 
		titleFont.setFontHeightInPoints((short) 12); 
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleFont.setFontName("微软雅黑");
        // 把字体应用到当前的样式
		titleStyle.setFont(titleFont);
        
        //生成样式 
		cellStyle = this.createTitleStyle(workbook);
	    // 设置这些样式
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);//设置自动换行
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
        // 集合字体
		cellFont = workbook.createFont(); 
		cellFont.setFontHeightInPoints((short) 10); 
		cellFont.setFontName("微软雅黑");
        // 把字体应用到当前的样式
		cellStyle.setFont(cellFont);
		
		this.blackStyle=this.createTitleStyle(workbook);
	     // 设置这些样式
		blackStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		blackStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		blackStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		blackStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		blackStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		blackStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		blackStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		blackStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
       // 标题字体
		blackFont = workbook.createFont(); 
		blackFont.setColor(HSSFColor.BLACK.index); 
		blackFont.setFontHeightInPoints((short) 11); 
		blackFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		blackFont.setFontName("微软雅黑");
       // 把字体应用到当前的样式
		blackStyle.setFont(blackFont);
	}

	//导出excel
		public InputStream  doExport(String title, String[] headers, String[] columns, List<Map<String, Object>>datalist,int[] freeze) throws Exception {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			HSSFWorkbook workbook =printExcel(title,headers,columns,datalist,freeze) ;
			if(workbook != null){
	            try{	                 
		      			workbook.write(output);
		      			output.flush();
		      			output.close();
	              }catch(Exception e){
	            	  e.printStackTrace();
	              }
	       }
			return new ByteArrayInputStream(output.toByteArray());
		}
		
		 /** 
	     * 导出Excel的方法 
	     * @param title excel中的sheet名称 
	     * @param headers 表头 
	     * @param columns 表头对应的数据库中的列名 
	     * @param result 结果集 
	     * @throws Exception 
	     */  
	    
		private HSSFWorkbook printExcel(String title, String[] headers, String[] columns, List<Map<String, Object>> result,int[] freeze) throws Exception{  
	    	// 声明一个工作薄  
	        HSSFWorkbook workbook = new HSSFWorkbook();  
	        boolean b = true;//判断标题行有没有换行
	    	try{
	    		//创建工作簿实例 
	    		workbook = new HSSFWorkbook();
	    		//创建工作表实例 
	    		HSSFSheet sheet = workbook.createSheet(title); 
	    		
	    		//设置列宽 
//	    		for (int i=0;i<headers.length;i++) {
//	    			 if (headers[i].length()>4){
//	    				sheet.setColumnWidth(i, 4000);
//						b = false;
//	    			} else {
//	    				sheet.setColumnWidth(i, headers[i].length()*1000);
//	    			}
//	    		}
	    		//生成样式 
	    		HSSFCellStyle style = this.createTitleStyle(workbook);
	    	     // 设置这些样式
	    		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index); 
	            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
	            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	            style.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
	            style.setBorderRight(HSSFCellStyle.BORDER_THIN); 
	            style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
	            // 标题字体
	            HSSFFont font = workbook.createFont(); 
	            font.setColor(HSSFColor.BLACK.index); 
	            font.setFontHeightInPoints((short) 12); 
	            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
	            font.setFontName("微软雅黑");
	            // 把字体应用到当前的样式
	            style.setFont(font);
	            
	            //生成样式 
	    		HSSFCellStyle style2 = this.createTitleStyle(workbook);
	    	    // 设置这些样式
	    		style2.setFillForegroundColor(HSSFColor.WHITE.index);
	    		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
	    		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	    		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
	    		style2.setBorderRight(HSSFCellStyle.BORDER_THIN); 
	    		style2.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
	            // 集合字体
	            HSSFFont listFont = workbook.createFont(); 
	            listFont.setFontHeightInPoints((short) 10); 
	            listFont.setFontName("微软雅黑");
	            // 把字体应用到当前的样式
	            style2.setFont(listFont);
	    		// 产生表格标题行  
	    		HSSFRow row0 = sheet.createRow(0); 
	    		if (b) {
	    			row0.setHeight((short) 400); 
	    		} else {
	    			row0.setHeight((short) 600);//有换行加宽
	    		}
	    		for (int i = 0; i < headers.length; i++) {  
	    			HSSFCell cell = row0.createCell(i);  
	    			cell.setCellStyle(style);
	    			HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
	    			cell.setCellValue(text);  
	    		}  
	    		
	    		
	    		// 遍历集合数据，产生数据行  
	    		if(result != null){
	    			int index = 1;	    			  
	    			for(Map<String, Object> m:result){  
	    				HSSFRow row = sheet.createRow(index); 
	    				row.setHeight((short) 350);
	    				int cellIndex = 0;  
	    				for(String s:columns){  
	    					HSSFCell cell = row.createCell(cellIndex);  
	    					HSSFRichTextString richString = null;
	    					richString = new HSSFRichTextString(m.get(s) == null ? "" : m.get(s).toString());			
	    					cell.setCellValue(richString);
	    					cell.setCellStyle(style2);
	    					cellIndex++;  
	    				}  
	    				index++;  
	    			}     
	    		}
	    		
	    		//让列宽随着导出的列长自动适应  
	            for (int colNum = 0; colNum < headers.length; colNum++) {  
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
	               /* if(colNum == 0){  
	                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);  
	                }else{  
	                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);  
	                }  */
	            } 
	            if(freeze!=null&&freeze.length==4){
	    			sheet.createFreezePane(freeze[0],freeze[1],freeze[2],freeze[3]);
	    		}
	    		//自适应列宽
	    		/*for (int i=0;i<headers.length;i++) {
	    			sheet.autoSizeColumn(i, true);
	    		}
	    		if(freeze!=null&&freeze.length==4){
	    			sheet.createFreezePane(freeze[0],freeze[1],freeze[2],freeze[3]);
	    		}*/
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return workbook;  
	    }
		
	    //设置excel的title样式  
	    private HSSFCellStyle createTitleStyle(HSSFWorkbook wb) { 
	    	HSSFFont boldFont = wb.createFont(); 
	    	boldFont.setFontHeight((short) 200); 
	    	HSSFCellStyle style = wb.createCellStyle(); 
	    	style.setWrapText(true);
	    	style.setFont(boldFont); 
	    	style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00")); 
	    	return style;  
	    }
	    
	    public static void outFileInfo(InputStream fileInput, String fileName) {
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				// 解决firefox导出文件乱码问题
				String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
				Integer i = agent.indexOf("Firefox");
				if (agent != null && i != -1) {// FF
					String enableFileName = "=?UTF-8?B?"
							+ (new String(Base64.encodeBase64(fileName
									.getBytes("UTF-8")))) + "?=";
					response.setHeader("Content-Disposition",
							"attachment; filename=" + enableFileName);
				} else { // IE
					String enableFileName = new String(fileName.getBytes("GBK"),
							"ISO-8859-1");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + enableFileName);
				}
				// response.setCharacterEncoding(CharEncoding.UTF_8);
				// response.setContentType("multipart/form-data");
				// response.setHeader("Content-Disposition", "attachment;fileName="+
				// URLEncoder.encode(fileName, CharEncoding.UTF_8));
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;
				while ((length = fileInput.read(b)) > 0) {
					os.write(b, 0, length);
				}
				fileInput.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
		/**
		 * @param fdataFilePath 文件路径
		 * @param rowNumber 从第几行开始读取
		 * @return
		 * @throws Exception
		 */
	    public String doImport(File fdataFilePath, int rowNumber) throws Exception {
			String dataStr="";// 定义集合sms_str用于存Excel中的数据
		    String rowStr="";
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	 try {
        		 InputStream fs = new FileInputStream(fdataFilePath);
           	  	 HSSFWorkbook wk = new HSSFWorkbook(fs);
                 HSSFSheet sh = wk.getSheetAt(0);// 得到book第一个工作薄sheet  
                 int rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();  // 总行数
                 if (rows > 0) {
                     for (int r = rowNumber; r < rows; r++) { // 行循环
                 	   HSSFRow row = sh.getRow(r);
                 	   if (row != null) {// 取第一行  && r != 0
                 	       int cells = row.getLastCellNum();// 获得列数
                 	       String cellvalue=null;
                 	       for (int c = 0; c < cells; c++) { // 列循环
                 	           HSSFCell cell = row.getCell(c);
                 	           if (cell != null) {                  	        	   
                 	            	//判断单元格的格式
                   	        	  if(cell.getCellType()==  HSSFCell.CELL_TYPE_NUMERIC){
                   	        		 if( HSSFDateUtil.isCellDateFormatted(cell)){        	        			
                   	        			cellvalue=simpleDateFormat.format(cell.getDateCellValue());   
                        	        	  } else {                  	   
                        	                  cellvalue = getValue(cell);   
                        	                  }
                   	        	        } else {
                        	        		  cellvalue=getValue(cell);
                        	        		  }  
                 	        	 rowStr +=cellvalue.trim() +"|"; 
                 	                                	             
                 	            }else{
                 	            	rowStr += "|";
                 	            }
                 	         }
                 	       if("".equals(rowStr.replaceAll("\\|", ""))){
                 	    	   break;
                 	       }
                 	       dataStr +=rowStr+ ";";
                 	       rowStr="";
                 	   }else{
                 		   break;
                 	   }
                 	   System.out.println(rowStr);
                    }
                 }
			} catch (Exception e) {
				e.printStackTrace();
			}
        	 return dataStr;
		}
	    
		/**
		 * 获取Excel中某个单元格的值
		 * @param cell
		 * @return
		 * @throws Exception
		 */
		public String getValue(HSSFCell cell) throws Exception {
			String value = "";
			try {	
				DecimalFormat df = new DecimalFormat("#.#########");
	           switch (cell.getCellType()) {       
	                case HSSFCell.CELL_TYPE_NUMERIC: // 数值型        
	                    if (HSSFDateUtil.isCellDateFormatted(cell)) {    
	                        // 如果是date类型则 ，获取该cell的date值       
	                        value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();						
	                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");       
	                        java.util.Date date1 = format.parse(value);   
	                        value = format.format(date1);        
	                    } else {// 纯数字  ,保证获取的电话号码不转为科学 计数法
	                        value = df.format(cell.getNumericCellValue());      
	                    }       
	                   break;   
	                /* 此行表示单元格的内容为string类型 */       
	                case HSSFCell.CELL_TYPE_STRING: // 字符串型        
	                    value = cell.getStringCellValue();       
	                    break;       
	                case HSSFCell.CELL_TYPE_FORMULA:// 公式型       
	                    // 读公式计算值        
	                    value = String.valueOf(cell.getNumericCellValue());      
	                    if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串    
	                        value = cell.getStringCellValue().toString();  
	                    }
	                    cell.getCellFormula();    
	                    break; 
	                case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔  
	                    value = " " + cell.getBooleanCellValue();
	                break;
	                /* 此行表示该单元格值为空 */
	                case HSSFCell.CELL_TYPE_BLANK: // 空值  
	                    value = ""; 
	                    break;   
	                case HSSFCell.CELL_TYPE_ERROR: // 故障   
	                    value = "";  
	                    break;
	                default:    
	                    value = cell.getStringCellValue().toString();  
	           }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return value;
		}


		public HSSFCellStyle getTitleStyle() {
			return titleStyle;
		}


		public void setTitleStyle(HSSFCellStyle titleStyle) {
			this.titleStyle = titleStyle;
		}


		public HSSFCellStyle getCellStyle() {
			return cellStyle;
		}


		public void setCellStyle(HSSFCellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}


		public HSSFFont getTitleFont() {
			return titleFont;
		}


		public void setTitleFont(HSSFFont titleFont) {
			this.titleFont = titleFont;
		}


		public HSSFFont getCellFont() {
			return cellFont;
		}


		public void setCellFont(HSSFFont cellFont) {
			this.cellFont = cellFont;
		}


		public HSSFCellStyle getNoticeStyle() {
			return noticeStyle;
		}


		public void setNoticeStyle(HSSFCellStyle noticeStyle) {
			this.noticeStyle = noticeStyle;
		}


		public HSSFFont getNoticeFont() {
			return noticeFont;
		}


		public void setNoticeFont(HSSFFont noticeFont) {
			this.noticeFont = noticeFont;
		}


		public HSSFCellStyle getCellLeftStyle() {
			return cellLeftStyle;
		}


		public void setCellLeftStyle(HSSFCellStyle cellLeftStyle) {
			this.cellLeftStyle = cellLeftStyle;
		}


		public HSSFFont getBlackFont() {
			return blackFont;
		}


		public void setBlackFont(HSSFFont blackFont) {
			this.blackFont = blackFont;
		}


		public HSSFCellStyle getBlackStyle() {
			return blackStyle;
		}


		public void setBlackStyle(HSSFCellStyle blackStyle) {
			this.blackStyle = blackStyle;
		}


		public HSSFCellStyle getUpStyle() {
			return upStyle;
		}


		public void setUpStyle(HSSFCellStyle upStyle) {
			this.upStyle = upStyle;
		}
}