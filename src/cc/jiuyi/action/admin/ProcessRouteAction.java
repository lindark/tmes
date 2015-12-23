package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.action.cron.ExtremelyMessage;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.SwiptCardService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工艺路线
 */
@ParentPackage("admin")
public class ProcessRouteAction extends BaseAdminAction {

	private static final long serialVersionUID = 2850935142585121482L;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource
	private ProcessService processservice;
	
	private List<ProcessRoute> processrouteList;
	private String productid;//产品ID
	private List<cc.jiuyi.entity.Process> processAll;
	
	//读取清单
	public String list(){
		return LIST;
	}
	//获取产品的工艺路线
	public String getRoute(){
		processrouteList = processrouteservice.getList("products.id", productid);
		JSONObject json = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<processrouteList.size();i++){
			ProcessRoute processroute = processrouteList.get(i);
			Products product = processroute.getProducts();
			int version1 = processroute.getVersion();
			int version2 = processrouteservice.getMaxVersion(productid);
			if(version1==version2){
				cc.jiuyi.entity.Process process = processroute.getProcess();
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("productsid", product.getId());
				jsonobject.put("productsCode", product.getProductsCode());
				jsonobject.put("productsName", product.getProductsName());
				jsonobject.put("processId", process.getId());
				jsonobject.put("processCode", process.getProcessCode());
				jsonobject.put("processName", process.getProcessName());
				jsonobject.put("productsNum", processroute.getProductAmount());
				jsonobject.put("sortcode", processroute.getSortcode());
				jsonobject.put("unit", processroute.getUnit());
				jsonobject.put("version", processroute.getVersion());
				jsonobject.put("workCenter", processroute.getWorkCenter());
				jsonarray.add(jsonobject);				
			}
		}
		json.put("list", jsonarray);
		System.out.println(json.toString());
		return ajaxJson(json.toString());
	}
	
	public String maintain(){
		
		return "maintain";
	}
	
	public String save(){
		processrouteservice.mergeProcessroute(processrouteList, productid);
		return SUCCESS;
	}
	
	public String update(){
		
		return null;
	}
	
	public String add(){
		
		return null;
	}
	
	public String edit(){
		
		return null;
	}

	public List<ProcessRoute> getProcessrouteList() {
		return processrouteList;
	}

	public void setProcessrouteList(List<ProcessRoute> processrouteList) {
		this.processrouteList = processrouteList;
	}
	public List<cc.jiuyi.entity.Process> getProcessAll() {
		return processservice.getAll();
	}
	public void setProcessAll(List<cc.jiuyi.entity.Process> processAll) {
		this.processAll = processAll;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}

	
}
