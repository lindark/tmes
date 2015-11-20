package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.CraftLog;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceLog;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DeviceLogService;
import cc.jiuyi.service.DeviceService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 设备
 */

@ParentPackage("admin")
public class DeviceAction extends BaseAdminAction {

	private static final long serialVersionUID = -5133263227242342963L;
	
	private Device device;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;
	
	@Resource
	private DeviceService deviceService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private DeviceLogService deviceLogService;
	
	// 添加
	public String add() {
		if(aid!=null){
			abnormal=abnormalService.load(aid);
		}	
		return INPUT;
	}

	// 编辑
	public String edit() {
		device = deviceService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		//pager = deviceService.findByPager(pager);
		return LIST;
	}

	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			
		   if (obj.get("team") != null) { 
			   String state = obj.getString("team").toString();
			   map.put("team", state);
			}

		   if (obj.get("productName") != null) { 
			   String productName = obj.getString("productName").toString();
			   map.put("productName", productName);
		   }

		}

		pager = deviceService.getDevicePager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Device device = (Device) pagerlist.get(i);
			device.setAbnormal(null);
			device.setDeviceLogSet(null);
			device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", device.getState()));		
			pagerlist.set(i,device);
		}
		pager.setList(pagerlist);

		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		deviceService.delete(ids);
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}
	
	public String save(){		
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin1 = adminService.get("username", loginUsername);
		
		abnormal=abnormalService.load(abnormalId);
		device.setAbnormal(abnormal);
		device.setState("0");
		device.setIsDel("N");
		
		deviceService.save(device);
		
		DeviceLog log = new DeviceLog();
		log.setOperator(admin1.getName());
		log.setInfo("已提交");
		log.setDevice(device);
		deviceLogService.save(log);
		
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}
	
	@InputConfig(resultName = "error")
	public String update() {
		Device persistent = deviceService.load(id);
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state"});
		deviceService.update(persistent);
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public String getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(String abnormalId) {
		this.abnormalId = abnormalId;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	
	
}
