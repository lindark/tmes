package cc.jiuyi.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
         String path = request.getRequestURI();//获取当前访问的路径
         System.out.println(path);
		if(methodName.equals("creditsave")){//刷卡保存
			Admin admin = adminservice.getLoginAdmin();//获取登录身份
			List<Role> roleList = new ArrayList<Role>(admin.getRoleSet());//获取当前登录身份的角色
			List<String> roleidList = new ArrayList<String>();
			for(Role role : roleList){
				roleidList.add(role.getId());
			}
			List<Object[]> objarrayList = accessfunctionservice.getAccessFunctionList(path, roleidList);
			
			
			return "error";
		}else if(methodName.equals("creditsubmit")){//刷卡提交
			return null;
		}else if(methodName.equals("creditapproval")){//刷卡确认
			return null;
		}else if(methodName.equals("creditundo")){//刷卡撤销
			return null;
		}
		else{
			return invo.invoke();//继续执行
		}
	}
	

}
