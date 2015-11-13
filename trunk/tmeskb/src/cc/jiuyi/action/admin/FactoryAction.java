package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class FactoryAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Factory factory;
	// 获取所有状态
	private List<Dict> allState;

	@Resource
	private FactoryService factoryService;
	@Resource
	private DictService dictService;

	// 是否已存在 ajax验证
	public String checkFactoryCode() {
		String factoryCode = factory.getFactoryCode();
		if (factoryService.isExistByFactoryCode(factoryCode)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		return LIST;
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
			if (obj.get("factoryCode") != null) {
				System.out.println("obj=" + obj);
				String factoryCode = obj.getString("factoryCode").toString();
				map.put("factoryCode", factoryCode);
			}
			if (obj.get("factoryName") != null) {
				String factoryName = obj.getString("factoryName").toString();
				map.put("factoryName", factoryName);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

		pager = factoryService.getFactoryPager(pager, map);
		List<Factory> factoryList = pager.getList();
		List<Factory> lst = new ArrayList<Factory>();
		for (int i = 0; i < factoryList.size(); i++) {
			Factory factory = (Factory) factoryList.get(i);
			factory.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "factoryState", factory.getState()));
			lst.add(factory);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		factoryService.updateisdel(ids, "Y");
		redirectionUrl = "factory!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 编辑
	public String edit() {
		factory = factoryService.load(id);
		return INPUT;
	}

	// 更新
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "factory.factoryCode", message = "工厂编号不允许为空!"),
			@RequiredStringValidator(fieldName = "factory.factoryName", message = "工厂名称不允许为空!") })
	@InputConfig(resultName = "error")
	public String update() {
		Factory persistent = factoryService.load(id);
		BeanUtils.copyProperties(factory, persistent, new String[] { "id",
				"createDate", "modifyDate" });
		factoryService.update(persistent);
		redirectionUrl = "factory!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "factory.factoryCode", message = "工厂编号不允许为空!"),
			@RequiredStringValidator(fieldName = "factory.factoryName", message = "工厂名称不允许为空!") })
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		factoryService.save(factory);
		redirectionUrl = "factory!list.action";
		return SUCCESS;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public FactoryService getFactoryService() {
		return factoryService;
	}

	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	// 获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "stateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

}
