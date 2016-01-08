package cc.jiuyi.sendmsg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cc.jiuyi.util.ThinkWayUtil;

public class Test {
	public static void main(String [] args){
		
		String phone="18668196276";
		String message="你好";
		
		Sendmsg_Service service=new Sendmsg_Service();
		int i=Integer.parseInt(phone.substring(phone.trim().length()-4,phone.trim().length()))*3+5698;
		
		String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info>"+ 
			    "<msg_id><![CDATA[-1]]></msg_id>"+  
			    "<password><![CDATA["+i+"]]></password>"+ 
			    "<src_tele_num><![CDATA[106573064090]]></src_tele_num>"+ 
			    "<dest_tele_num><![CDATA["+phone+"]]></dest_tele_num>"+ 
			    "<msg><![CDATA["+message+"]]></msg>"+  
			   "</info>"+
			"</infos>";

		String xml=service.getSendmsgHttpPort().sendmsg("nhjx_nb064090", msg);
	}
}
