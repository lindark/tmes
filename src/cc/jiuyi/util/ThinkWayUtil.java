package cc.jiuyi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;


public class ThinkWayUtil {
	
	/**
	 * 根据dictName=dictName,keyValue=keyValu取出描述
	 * @param dictService
	 * @param dictName
	 * @param keyValue
	 * @return
	 */
	  public static String getDictValueByDictKey(DictService dictService,Object dictName,Object keyValue){
		  String retVal = null;
		  if(keyValue.equals("")){
			  retVal="";
		  }else{
			  retVal = dictService.getDictValueByDictKey(dictName,keyValue);
	  	  }
		  return retVal;
	  }
	
	  
	  public static Admin getUsernameById(AdminService adminService,String id){
		  Admin admin = adminService.get(id);
		  return admin;
	  }
	  
	  public static String null2String(String s)
	    {
	        return s != null ? s : "";
	    }
	  public static String null2String(Object s)
	    {
	        return (String) (s != null ? s : "");
	    }
	   /**
	     * @param datetime - 时间格式的字符串”YYYY-MM-DD HH:MM:SS”
	     * @param second – 加上的时间（秒）
	     * @return 返回增加second后的时间格式的字符串
	     * 
	     * 给时间datetime加上时间second.
	     * datetime 必须为 ”YYYY-MM-DD HH:MM:SS” ，HH为24小时制标识，如”2004-07-04 09:24:05”
	     * second为需要增加的秒数，如果为负数，则为减去。如果datetime不满足时间格式，返回null。如：
	     * 
	     * timeAdd (”2004-07-04 09:24:05”,3600) 返回”2004-07-04 10:24:05”
	     * timeAdd (”2004-07-04 09:24:05”,86400) 返回”2004-07-05 09:24:05”
	     * timeAdd (”2004-07-04 09:24:05”,-86400) 返回”2004-07-04 09:24:05”
	     * timeAdd (”2004-7-4 9:24:05”,-86400) 返回null
	     * 
	     */
	    public static String timeAdd(String datetime, int second) {
	        Calendar calendar = getCalendar(datetime) ;
	        if(calendar == null) return null ;
	        calendar.add(Calendar.SECOND, second) ;
	        
	        return getTimeString(calendar) ;
	    }
	    /**
	     * @param datetime - 给定的日期时间，格式为 '2004-05-12 12:00:23'  或者 '2004-05-12'
	     * @return 返回给定日历， 如果格式不正确，返回null
	     */
	    public static Calendar getCalendar(String datetime) {
	        int datetimelength = datetime.length() ;
	                
	        switch(datetimelength) {
	            case 19 :
	                return getCalendar(datetime , "yyyy'-'MM'-'dd' 'HH:mm:ss") ;
	            case 10 :
	                return getCalendar(datetime , "yyyy'-'MM'-'dd") ;
	            default :
	                return null ;
	        }
	        
	    }
	    /**
	     * @param calendar - 日历
	     * @return 返回给定日历的时间字符，格式为 yyyy'-'MM'-'dd' 'HH:mm:ss 
	     */
	    public static String getTimeString(Calendar calendar) {
	        String timestrformart = "yyyy'-'MM'-'dd' 'HH:mm:ss" ;
	        SimpleDateFormat SDF = new SimpleDateFormat(timestrformart) ;
	        
	        return SDF.format(calendar.getTime()) ;
	    }
	    /**
	     * @param datetime - 给定的日期时间
	     * @param formart - 给定的日期时间的格式
	     * @return 返回给定日历， 如果格式不正确，返回null
	     */
	    public static Calendar getCalendar(String datetime, String formart) {
	        SimpleDateFormat SDF = new SimpleDateFormat(formart) ;
	        
	        Calendar calendar = Calendar.getInstance() ;
	        try {
	            calendar.setTime(SDF.parse(datetime)) ;
	        } catch (ParseException e) {
	            return null ;
	        }
	        
	        return calendar ;
	    }
	    /**
	     * 获取系统当前时间
	     * @return
	     */
	    public static String SystemDate(){
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	    	//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	    	return df.format(new Date());
	    }
}
