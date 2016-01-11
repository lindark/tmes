package cc.jiuyi.common;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AdminService;

public class JiuyiPasswordEncoder extends Md5PasswordEncoder {
	@Resource
	private AdminService adminservice;

	public boolean isPasswordValid(String encPass,String rawPass,Object salt) throws DataAccessException{
		if(rawPass.indexOf("shuaka-")> -1){//刷卡
			String[] shuakaArr = rawPass.split("-");
			String kahao = shuakaArr[1];
			Admin admin = adminservice.get("cardNumber", kahao);
			if(admin == null)
				return false;
			else
				return true;
			
		}
		String pass1 = ""+encPass;
		String pass2 = encodePassword(rawPass, salt);
		return pass1.equals(pass2);
	}
}
