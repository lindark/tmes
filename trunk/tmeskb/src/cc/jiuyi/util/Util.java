package cc.jiuyi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*
 * 工具类
 * -----------by ZhangChunhao V1.0--------
 */
public class Util {
	public static String null2String(String paramString) {
	    return paramString == null ? "" : paramString;
}

public static String getCurrentDateTime() {
	String returnStr = null;
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	returnStr = f.format(date);
	return returnStr;
}
/*
 * 获取今天的日期-短格式20130613
 */
public static String getTodayWithSdt(){
	Calendar c1 = Calendar.getInstance();  
    c1.setTime(new Date()); 
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
    return format.format(c1.getTime());
}
public static double getDoubleValue(String str){
	double i=0;
	try{
		i=Double.parseDouble(str);
	}catch(Exception e){
		i=-1;
	}
	return i;
}
public static int getIntValue(String str){
	int i=0;
	try{
		i=Integer.parseInt(str);
	}catch(Exception e){
		i=-1;
	}
	return i;
}
}
