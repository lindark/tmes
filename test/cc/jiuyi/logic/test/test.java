package cc.jiuyi.logic.test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
public static void main(String[] args) {
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	try {
		ParsePosition pos = new ParsePosition(0);
		Date d=sdf.parse("2015-11-01",pos);
		//Date strtodate = formatter.parse(strDate, pos);
		System.out.println(d.toString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
