package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.SystemConfig;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessResourceService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.SystemConfigUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 后台Action类 - 管理中心基类
 */

public class BaseAdminAction extends ActionSupport {

	private static final long serialVersionUID = 6718838822334455667L;
	
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessResourceService accessResourceService;

	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static final String ID="id";
	
	protected String id;
	protected String[] ids;
	protected Pager pager;
	protected String logInfo;// 日志记录信息
	protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	protected String filters;
	protected String Param;
	protected String btnhtml;//按钮html页面
	
	public String input() {
		return null;
	}

	// 获取系统配置信息
	public SystemConfig getSystemConfig() {
		return SystemConfigUtil.getSystemConfig();
	}
	
	// 获取商品价格货币格式
	public String getPriceCurrencyFormat() {
		return SystemConfigUtil.getPriceCurrencyFormat();
	}
	
	// 获取商品价格货币格式（包含货币单元）
	public String getPriceUnitCurrencyFormat() {
		return SystemConfigUtil.getPriceUnitCurrencyFormat();
	}
	
	// 获取订单货币格式
	public String getOrderCurrencyFormat() {
		return SystemConfigUtil.getOrderCurrencyFormat();
	}
	
	// 获取订单货币格式（包含货币单位）
	public String getOrderUnitCurrencyFormat() {
		return SystemConfigUtil.getOrderUnitCurrencyFormat();
	}

	// 获取Attribute
	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	// 获取Session
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 获取Session
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	// 设置Session
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取Application
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 设置页面不缓存
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	public String getParam() {
		return Param;
	}

	public void setParam(String param) {
		Param = param;
	}

	
	public String getBtnhtml() {
		StringBuffer strbuf = new StringBuffer();
		
//		Admin admin = adminservice.getLoginAdmin();
//		List<Role> roleList = new ArrayList<Role>(admin.getRoleSet());
//		for(Role role : roleList){
//			List<AccessResource> accessResourceList = new ArrayList<AccessResource>(role.getAccessResourceSet());//权限资源对象
//			for(AccessResource accessResource : accessResourceList){
//				accessResource.getAccessobjectSet();
//			}
//		}
		
		HttpServletRequest request =  this.getRequest();//获取request
		String path = request.getRequestURI();//获取当前的path地址
		//AccessResource accessResource = accessResourceService.get("resource.value", path);//根据path地址判断当前操作的功能
//		if(accessResource == null){//没有此功能的权限对象
//			strbuf.append("没找到权限对象");
//		}else{
//			List<AccessObject> accessobjectList = new ArrayList<AccessObject>(accessResource.getAccessobjectSet());
//			for(AccessObject accessobject : accessobjectList){
//				boolean flag = StringUtil.isEmpty(accessobject.getHtmlarea());//判断是否为空
//				if(!flag){
//					strbuf.append(accessobject.getHtmlarea());
//				}else{
//					strbuf.append("未找到对应的html");
//				}
//			}
//			
//		}
		
		this.btnhtml = strbuf.toString();
		return btnhtml;
	}

	public void setBtnhtml(String btnhtml) {
		this.btnhtml = btnhtml;
	}
	
	

}