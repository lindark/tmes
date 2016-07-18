package cc.jiuyi.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.csvreader.CsvWriter;
public class ReportTxt {
	public void txt(List<String[]> list){
		InputStream is = exportxt(list);
		ExcelUtil.outFileInfo(is,"投入产出报表.csv");
	}
	public InputStream exportxt(List<String[]> list){
			try {
				InputStream doExport = doExport(list);
				return doExport;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
	}		
			
	
	//导出excel
	public InputStream  doExport(List<String[]> list) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		 CsvWriter wr =new CsvWriter(output,',',Charset.forName("UTF-8"));
		 for(int i=0;i<list.size();i++){
			 	Object[] objs = list.get(i);
			 	String[] strs = Arrays.asList( objs ).toArray( new String[0] );
	        	//String[] str= new String[objs.length];
	        	//list.toArray(a)
	        	wr.writeRecord(strs);
	        }
		 wr.close();
		 output.flush();
		 output.close();
		return new ByteArrayInputStream(output.toByteArray());
	}
	
	
}
