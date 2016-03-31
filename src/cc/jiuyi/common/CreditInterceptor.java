package cc.jiuyi.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import cc.jiuyi.action.cron.WorkingBillJob;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessFunctionService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ResourcesService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import freemarker.template.utility.StringUtil;

/**
 * 方法拦截器
 * 
 * @author weita
 * 
 */
public class CreditInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -6469337425520111325L;
	public static Logger log = Logger.getLogger(CreditInterceptor.class);
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessFunctionService accessfunctionservice;
	@Resource
	private ResourcesService resourceService;
	
	@Override
	protected String doIntercept(ActionInvocation invo) throws Exception {
		ActionContext actionContext = invo.getInvocationContext();// 获取上下文
		Map<String,Object> parameters = actionContext.getParameters();//获取传入的参数
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST); // 获取request
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);// 获取response
		try{
			//request.getServletPath()
			String path = request.getRequestURI();// 获取当前访问的路径
			Object[] cardnumber = (Object[])parameters.get("cardnumber");//卡号
			String [] propertyNames = {"cardNumber","isDel","isDelete","isAccountEnabled"};
			Object[] propertyValues = {cardnumber[0],"N","N",true};
			List<Admin> admin = adminservice.getList(propertyNames,propertyValues);
			if(admin == null || admin.size()<=0){
				HashMap<String, String> jsonmap = new HashMap<String, String>();
				jsonmap.put("status", "error");
				jsonmap.put("message", "对不起,当前人员不存在");
	
				JSONObject jsonObject = JSONObject.fromObject(jsonmap);
				response.setContentType("text/html" + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(jsonObject.toString());
				response.getWriter().flush();
				return null;
				
			}
			
			if(admin.size() > 1){
				HashMap<String, String> jsonmap = new HashMap<String, String>();
				jsonmap.put("status", "error");
				jsonmap.put("message", "对不起,当前卡号维护了多个人员");
				JSONObject jsonObject = JSONObject.fromObject(jsonmap);
				response.setContentType("text/html" + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(jsonObject.toString());
				response.getWriter().flush();
				return null;
			}
			
			List<Role> roleList = new ArrayList<Role>(admin.get(0).getRoleSet());
			List<String> roleid = new ArrayList<String>();
			for (Role role : roleList) {
				roleid.add(role.getId());
			}
			Integer count = resourceService.getListByadmin(roleid, path);
			if(count <=0){
				HashMap<String, String> jsonmap = new HashMap<String, String>();
				jsonmap.put("status", "error");
				jsonmap.put("message", "对不起,您无权限访问");
	
				JSONObject jsonObject = JSONObject.fromObject(jsonmap);
				response.setContentType("text/html" + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(jsonObject.toString());
				response.getWriter().flush();
				return null;
			}
		}catch(Exception e){
			log.error(e);
			HashMap<String, String> jsonmap = new HashMap<String, String>();
			jsonmap.put("status", "error");
			jsonmap.put("message", "对不起,您无权限访问");

			JSONObject jsonObject = JSONObject.fromObject(jsonmap);
			response.setContentType("text/html" + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
			return null;
		}
		return invo.invoke();
	}

}
