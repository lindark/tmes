package cc.jiuyi.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;


import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;

public class ThinkWayUtil {

	/**
	 * 根据dictName=dictName,keyValue=keyValu取出描述
	 * 
	 * @param dictService
	 * @param dictName
	 * @param keyValue
	 * @return
	 */
	public static String getDictValueByDictKey(DictService dictService,
			Object dictName, Object keyValue) {
		String retVal = null;
		if (keyValue.equals("")) {
			retVal = "";
		} else {
			retVal = dictService.getDictValueByDictKey(dictName, keyValue);
		}
		return retVal;
	}

	public static Admin getUsernameById(AdminService adminService, String id) {
		Admin admin = adminService.get(id);
		return admin;
	}

	public static String null2String(String s) {
		return s != null ? s : "";
	}

	public static String null2String(Object s) {
		return (String) (s != null ? s : "");
	}

	/**
	 * @param datetime
	 *            - 时间格式的字符串”YYYY-MM-DD HH:MM:SS”
	 * @param second
	 *            – 加上的时间（秒）
	 * @return 返回增加second后的时间格式的字符串
	 * 
	 *         给时间datetime加上时间second. datetime 必须为 ”YYYY-MM-DD HH:MM:SS”
	 *         ，HH为24小时制标识，如”2004-07-04 09:24:05”
	 *         second为需要增加的秒数，如果为负数，则为减去。如果datetime不满足时间格式，返回null。如：
	 * 
	 *         timeAdd (”2004-07-04 09:24:05”,3600) 返回”2004-07-04 10:24:05”
	 *         timeAdd (”2004-07-04 09:24:05”,86400) 返回”2004-07-05 09:24:05”
	 *         timeAdd (”2004-07-04 09:24:05”,-86400) 返回”2004-07-04 09:24:05”
	 *         timeAdd (”2004-7-4 9:24:05”,-86400) 返回null
	 * 
	 */
	public static String timeAdd(String datetime, int second) {
		Calendar calendar = getCalendar(datetime);
		if (calendar == null)
			return null;
		calendar.add(Calendar.SECOND, second);

		return getTimeString(calendar);
	}

	/**
	 * @param datetime
	 *            - 给定的日期时间，格式为 '2004-05-12 12:00:23' 或者 '2004-05-12'
	 * @return 返回给定日历， 如果格式不正确，返回null
	 */
	public static Calendar getCalendar(String datetime) {
		int datetimelength = datetime.length();

		switch (datetimelength) {
		case 19:
			return getCalendar(datetime, "yyyy'-'MM'-'dd' 'HH:mm:ss");
		case 10:
			return getCalendar(datetime, "yyyy'-'MM'-'dd");
		default:
			return null;
		}

	}

	/**
	 * @param calendar
	 *            - 日历
	 * @return 返回给定日历的时间字符，格式为 yyyy'-'MM'-'dd' 'HH:mm:ss
	 */
	public static String getTimeString(Calendar calendar) {
		String timestrformart = "yyyy'-'MM'-'dd' 'HH:mm:ss";
		SimpleDateFormat SDF = new SimpleDateFormat(timestrformart);

		return SDF.format(calendar.getTime());
	}

	/**
	 * @param datetime
	 *            - 给定的日期时间
	 * @param formart
	 *            - 给定的日期时间的格式
	 * @return 返回给定日历， 如果格式不正确，返回null
	 */
	public static Calendar getCalendar(String datetime, String formart) {
		SimpleDateFormat SDF = new SimpleDateFormat(formart);

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(SDF.parse(datetime));
		} catch (ParseException e) {
			return null;
		}

		return calendar;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String SystemDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		// System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		return df.format(new Date());
	}

	/**
	 * 判断浏览器类型，是否移动端，或者是电脑浏览器
	 * 
	 * @param request
	 * @return
	 */
	public static boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile",
				"wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",
				"windows ce", "symbian", "series", "webos", "sony",
				"blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",
				"pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin",
				"huawei", "novarra", "coolpad", "webos", "techfaith",
				"palmsource", "alcatel", "amoi", "ktouch", "nexian",
				"ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",
				"smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop",
				"benq", "haier", "^lct", "320x320", "240x320", "176x220",
				"w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
				"bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
				"doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",
				"keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
				"midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
				"newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",
				"port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",
				"send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",
				"sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-",
				"upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",
				"wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase()
						.indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}

	public static String formatdateDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dd = format.format(date);
		return dd;
	}

	public static String formatdateDateTime(Date datetime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dd = format.format(datetime);
		return dd;
	}

	/**
	 * 排除bean之间的关系字段
	 * 
	 * @param bean
	 * @return
	 */
	public static String[] getExcludeFields(Class clazz) {
		Set<String> list = new HashSet<String>();
		list.add("handler");
		list.add("hibernateLazyInitializer");

		Method[] method = clazz.getMethods();// 通过反射获取所有的方法

		for (Method m : method) {
			if (m.getAnnotation(OneToOne.class) != null
					|| m.getAnnotation(OneToMany.class) != null
					|| m.getAnnotation(ManyToOne.class) != null
					|| m.getAnnotation(ManyToMany.class) != null) {
				String name = m.getName().replace("get", "");
				String oldfirst = name.substring(0, 1);
				String newfirst = oldfirst.toLowerCase();// 转换为小写
				name = name.replaceFirst(oldfirst, newfirst);
				list.add(name);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	
	
	/*** 
     *  
     * @param date 
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String formatDateByPattern(Date date,String dateFormat){  
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        String formatTimeStr = null;  
        if (date != null) {  
            formatTimeStr = sdf.format(date);  
        }  
        return formatTimeStr;  
    }  
    /*** 
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014" 
     * @param date  : 时间点 
     * @return 
     */  
    public static String getCron(java.util.Date  date){  
        String dateFormat="ss mm HH dd MM ? yyyy";  
        return formatDateByPattern(date, dateFormat);  
    }  

}
