package cc.jiuyi.common;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;

import sun.misc.BASE64Decoder;

/**
 * 系统初始化
 */

public class Initializing implements InitializingBean {
	
	private String keyFile = "key";
	/*
	@Resource
	private ServletContext servletContext;
	*/
	/*
	@Override
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			
			//BASE64Decoder bASE64Decoder = new BASE64Decoder();
			//keyFile = new String(bASE64Decoder.decodeBuffer(keyFile + "A=="));
			//Method readKey = Class.forName("cc.jiuyi.common.Key").getMethod("readKeyFile", String.class);
			//String content = (String) readKey.invoke(null, servletContext.getRealPath(keyFile));
			//servletContext.setAttribute(new String(bASE64Decoder.decodeBuffer("UEhTSE9QX0tFWQ==")), content);
		   
			servletContext.setAttribute("TKWRDP_KEY", "tkwrdp");
		}
	}
	*/
	public void afterPropertiesSet() throws Exception {
		
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

}