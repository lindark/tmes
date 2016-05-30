package cc.jiuyi.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

import cc.jiuyi.sendmsg.Sendmsg_Service;

public class SendMsgUtil {

	
	public static String SendMsg(String phone,String message){
		int i=Integer.parseInt(phone.substring(phone.trim().length()-4,phone.trim().length()))*3+5698;

		String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info>"+ 
				    "<msg_id><![CDATA[-1]]></msg_id>"+  
				    "<password><![CDATA["+i+"]]></password>"+ 
				    "<src_tele_num><![CDATA[106573064090]]></src_tele_num>"+ 
				    "<dest_tele_num><![CDATA["+phone+"]]></dest_tele_num>"+ 
				    "<msg><![CDATA["+message+"]]></msg>"+  
				   "</info>"+
				"</infos>";
		Client client;
		String xml = "";
		try {
			client = new Client(new URL("http://111.1.2.28/webservice/services/sendmsg?wsdl"));
			Object[] results = client.invoke("sendmsg", new Object[]{"nhjx_nb064090",msg});
			xml = results[0].toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
		
		/*Sendmsg_Service service=new Sendmsg_Service();
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
		return xml;*/
	}
}
