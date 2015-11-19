package cc.jiuyi.customTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.SpringUtil;

/**
 * 自定义标签 --- 权限 
 * @author weita
 *
 */
//@Component
public class AccessTag extends TagSupport {

	private static final long serialVersionUID = -7091766915328641429L;
	
//	@Resource
//	private AccessResourceService accessResourceservice;
	
	private String accobjkey;
	
	@Override
	public int doStartTag() throws JspException {
		System.out.println("调用doStartTag()方法");
		HttpServletRequest request =(HttpServletRequest) pageContext.getRequest();
		StringBuffer html = new StringBuffer();
		WebApplicationContext web = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
		AccessObjectService accessObjectservice = (AccessObjectService)web.getBean("accessObjectServiceImpl");
		AdminService adminservice = (AdminService)web.getBean("adminServiceImpl");
		AccessResourceService accessResourceService = (AccessResourceService)web.getBean("accessResourceServiceImpl");
		Admin admin = adminservice.getLoginAdmin();//获取当前登录身份
		List<Role> roleList = new ArrayList<Role>(admin.getRoleSet());//获取当前登录身份的角色
		List<AccessResource> accessList = accessResourceService.findAccessByRoles(roleList);//获取角色对应的资源对象
		Object[] accessobj = new Object[accessList.size()];
		for(int i=0;i<accessList.size();i++){
			AccessResource accessresource = accessList.get(i);
			accessobj[i] = accessresource.getId();
		}
		List<AccessObject> accessObjList = accessObjectservice.findResourceList(accessobj,accobjkey);
		
		/*去除重复*/
		List<AccessObject> listtemp = new ArrayList<AccessObject>();
		for(int i=0;i<accessObjList.size();i++){
			AccessObject accessobject = accessObjList.get(i);
			if(listtemp.contains(accessobject)){
				accessObjList.remove(i);
			}else{
				listtemp.add(accessobject);
			}
		}
		html.append("");
		for(int i=0;i<accessObjList.size();i++){
			AccessObject accessobject = accessObjList.get(i);
			html.append(accessobject.getHtmlarea());
		}
		

		JspWriter out = pageContext.getOut();
		try {
			out.write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getAccobjkey() {
		return accobjkey;
	}

	public void setAccobjkey(String accobjkey) {
		this.accobjkey = accobjkey;
	}
	
	

}
