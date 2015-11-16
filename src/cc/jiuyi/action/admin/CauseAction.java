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
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 缺陷代码管理
 * 
 */
@ParentPackage("admin")
public class CauseAction extends BaseAdminAction {

	private static final long serialVersionUID = 8114269369652720624L;

	private Cause cause;

	@Resource
	private CauseService causeService;
	@Resource
	private DictService dictService;

	public String list() {
		// pager = causeService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		cause = causeService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "cause.causeCode", message = "代码编码不允许为空!"),
			@RequiredStringValidator(fieldName = "cause.causeType", message = "代码类型不允许为空!"),
			@RequiredStringValidator(fieldName = "cause.causeName", message = "代码描述不允许为空!") }

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		causeService.save(cause);
		redirectionUrl = "cause!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Cause persistent = causeService.load(id);
		BeanUtils.copyProperties(cause, persistent, new String[] { "id" });
		causeService.update(persistent);
		redirectionUrl = "cause!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		causeService.updateisdel(ids, "Y");
		redirectionUrl = "cause!list.action";
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
			if (obj.get("causeCode") != null) {
				String causeCode = obj.get("causeCode").toString();
				map.put("causeCode", causeCode);
			}
			if (obj.get("causeType") != null) {
				String causeType = obj.get("causeType").toString();
				map.put("causeType", causeType);
			}
			if (obj.get("causeName") != null) {
				String causeName = obj.get("causeName").toString();
				map.put("causeName", causeName);
			}
			if (obj.get("state") != null) {
				String state = obj.get("state").toString();
				map.put("state", state);
			}
		}
		pager = causeService.getCausePager(pager, map);
		List<Cause> causeList = pager.getList();
		List<Cause> lst = new ArrayList<Cause>();
		for (int i = 0; i < causeList.size(); i++) {
			Cause cause = (Cause) causeList.get(i);
			cause.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "causeState", cause.getState()));
			cause.setCauseTypeRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "causeTypeRemark", cause.getCauseType()));
			lst.add(cause);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 是否已存在 ajax验证
	public String checkCauseCode() {
		String causeCode = cause.getCauseCode();
		if (causeService.isExist("causeCode", causeCode)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

}
