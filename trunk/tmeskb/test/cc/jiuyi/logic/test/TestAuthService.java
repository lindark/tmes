package cc.jiuyi.logic.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import cc.jiuyi.common.Key;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import junit.framework.TestCase;

public class TestAuthService extends TestCase {

	
	protected void setUp() {
		//userSvc = new UserServiceImpl();
	}
	
	public void genKey() throws IOException{


		BASE64Decoder bASE64Decoder = new BASE64Decoder();
		BASE64Encoder bASE64Encoder = new BASE64Encoder();
		//请输入站点授权文件名，例如"tcrm.txt"
		String keyFile_before = bASE64Encoder.encodeBuffer("tcrm.txt".getBytes());
		//将下面生成的加密串（请去掉A==后的串）放到applicationContext.xml的Initializing处的keyfile中
		System.out.println("keyFile_before:"+keyFile_before);
		
		//请输入加密全局属性名，例如"TCRM_KEY"
		String attrname_before = bASE64Encoder.encodeBuffer("TCRM_KEY".getBytes());
		System.out.println("attrname_before:"+attrname_before);
		
		
		
	}	
	
	public void testAuthUser() throws IOException{

		BASE64Decoder bASE64Decoder = new BASE64Decoder();
		BASE64Encoder bASE64Encoder = new BASE64Encoder();
		String keyFile = "c2hvcHh4LnR4d";
		
		String keyFile_before = bASE64Encoder.encodeBuffer("tcrm.txt".getBytes());
		System.out.println("keyFile_before:"+keyFile_before);
		
		keyFile = new String(bASE64Decoder.decodeBuffer(keyFile + "A=="));
		
		System.out.println("keyFile:"+keyFile);
		//Method readKey = Class.forName("cc.jiuyi.common.Key").getMethod("readKeyFile", String.class);
		//String content = (String) readKey.invoke(null, servletContext.getRealPath(keyFile));
		
		String attrname_before = bASE64Encoder.encodeBuffer("TCRM_KEY".getBytes());
		System.out.println("attrname_before:"+attrname_before);
		
		
		String content = Key.readKeyFile("D://SRC/tcrm/WebRoot/tcrm.txt");
		System.out.println("content:"+content);
		
		String attr_name = new String(bASE64Decoder.decodeBuffer("UEhTSE9QX0tFWQ=="));
		
		System.out.println("attr_name:"+attr_name);
		//servletContext.setAttribute(new String(bASE64Decoder.decodeBuffer("U0hPUFhYX0tFWQ==")), content);
		
        
		
	}	
	
}


