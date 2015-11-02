package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.service.LocationonsideService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 线边仓
 * 
 */
@ParentPackage("admin")
public class LocationonsideAction extends BaseAdminAction {

	private static final long serialVersionUID = -983362628543799708L;

	private Locationonside locationonside;

	@Resource
	private LocationonsideService locationonsideService;

	public String list() {
		// pager = dumpService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		locationonside = locationonsideService.load(id);
		return INPUT;
	}

	public String save() {
		locationonsideService.save(locationonside);
		redirectionUrl = "locationonside!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Locationonside persistent = locationonsideService.load(id);
		BeanUtils.copyProperties(locationonside, persistent,
				new String[] { "id" });
		locationonsideService.update(persistent);
		redirectionUrl = "locationonside!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		locationonsideService.updateisdel(ids, "Y");
		redirectionUrl = "locationonside!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
			if (obj.get("locationCode") != null) {
				String locationCode = obj.get("locationCode").toString();
				map.put("locationCode", locationCode);
			}
			/*
			 * if(obj.get("deliveryDate")!=null){ String deliveryDate =
			 * obj.get("deliveryDate").toString(); map.put("deliveryDate",
			 * deliveryDate); }
			 */
			if (obj.get("locationName") != null) {
				String locationName = obj.get("locationName").toString();
				map.put("locationName", locationName);
			}
		}
		pager = locationonsideService.getLocationPager(pager, map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());

	}

	public Locationonside getLocationonside() {
		return locationonside;
	}

	public void setLocationonside(Locationonside locationonside) {
		this.locationonside = locationonside;
	}

}
