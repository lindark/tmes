package cc.jiuyi.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessFunctionService;
import cc.jiuyi.service.AdminService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 方法拦截器
 * @author weita
 *
 */
public class CreditInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -6469337425520111325L;
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessFunctionService accessfunctionservice;

	@Override
	public String intercept(ActionInvocation invo) throws Exception {
		 String methodName = invo.getProxy().getMethod();//获取方法名称
		 ActionContext actionContext = invo.getInvocationContext();//获取上下文
         HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);  //获取request 
         HttpServletResponse response = (HttpServletResponse)actionContext.get(StrutsStatics.HTTP_RESPONSE);//获取response
         String path = request.getRequestURI();//获取当前访问的路径
         System.out.println(path);
//		if(methodName.equals("creditsave")){//刷卡保存
//			Admin admin = adminservice.getLoginAdmin();//获取登录身份
//			List<Role> roleList = new ArrayList<Role>(admin.getRoleSet());//获取当前登录身份的角色
//			List<String> roleidList = new ArrayList<String>();
//			for(Role role : roleList){
//				roleidList.add(role.getId());
//			}
//			List<Object[]> objarrayList = accessfunctionservice.getAccessFunctionList(path, roleidList);
//			
//			
//			return "error";
//		}else if(methodName.equals("creditsubmit")){//刷卡提交
//			return null;
//		}else if(methodName.equals("creditapproval")){//刷卡确认
//			HashMap<String, String> jsonmap = new HashMap<String,String>();
//			jsonmap.put("status", "error");
//			jsonmap.put("message", "对不起,您无权限访问");
//			
//			JSONObject jsonObject = JSONObject.fromObject(jsonmap);
//			response.setContentType("text/html" + ";charset=UTF-8");
//			response.setHeader("Pragma", "No-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", 0);
//			response.getWriter().write(jsonObject.toString());
//			response.getWriter().flush();
//			return null;
//		}else if(methodName.equals("creditundo")){//刷卡撤销
//			return null;
//		}
//		else{
//			return invo.invoke();//继续执行
//		}
         return invo.invoke();
	}
	


}
