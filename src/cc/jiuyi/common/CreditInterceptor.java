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
import cc.jiuyi.service.ResourceService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 方法拦截器
 * 
 * @author weita
 * 
 */
public class CreditInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6469337425520111325L;
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessFunctionService accessfunctionservice;
	@Resource
	private ResourceService resourceService;

	@Override
	public String intercept(ActionInvocation invo) throws Exception {
		String methodName = invo.getProxy().getMethod();// 获取方法名称

		// 刷卡保存,刷卡提交,刷卡确认,刷卡撤销
		if (methodName.equals("creditsave")
				|| methodName.equals("creditsubmit")
				|| methodName.equals("creditapproval")
				|| methodName.equals("creditundo")) {
			ActionContext actionContext = invo.getInvocationContext();// 获取上下文
			HttpServletRequest request = (HttpServletRequest) actionContext
					.get(StrutsStatics.HTTP_REQUEST); // 获取request
			HttpServletResponse response = (HttpServletResponse) actionContext
					.get(StrutsStatics.HTTP_RESPONSE);// 获取response
			String path = request.getRequestURI();// 获取当前访问的路径
			System.out.println(path);
			// 卡号
			Admin admin = adminservice.getLoginAdmin();// 获取登录身份
			List<Role> roleList = new ArrayList<Role>(admin.getRoleSet());

			List<String> roleid = new ArrayList<String>();
			for (Role role : roleList) {
				roleid.add(role.getId());
			}
			Integer count = resourceService
					.getListByadmin(roleid, path);
			if (count <= 0) {//未找到
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
			} else {
				return invo.invoke();// 继续执行
			}
		} else {
			return invo.invoke();
		}
	}

}
