package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.ReceiptReason;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReceiptReasonService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 单据原因
 */
@ParentPackage("admin")
public class ReceiptReasonAction extends BaseAdminAction {

	private static final long serialVersionUID = 5411663477762315138L;

	private ReceiptReason receiptReason;
	// 获取所有类型
	private List<Dict> allType;
	// 获取所有状态
	private List<Dict> allState;
	
	@Resource
	private DictService dictService;
	@Resource
	private ReceiptReasonService receiptReasonService;
	
	
	// 添加
	public String add() {
		return INPUT;
	}	

	// 编辑
	public String edit() {
		receiptReason = receiptReasonService.load(id);
		return INPUT;
	}	

	// 列表
	public String list() {
		return LIST;
	}			
		
		
	/**
	 * ajax 列表
	 * 
	 * @return
	 */	
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
			if (obj.get("reasonName") != null) {
				String reasonName = obj.getString("reasonName").toString();
				map.put("reasonName", reasonName);
			}
			
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			
		}

		pager = receiptReasonService.getReceiptReasonPager(pager, map);
		List<ReceiptReason> receiptReasonList = pager.getList();
		List<ReceiptReason> lst = new ArrayList<ReceiptReason>();
		for (int i = 0; i < receiptReasonList.size(); i++) {
			ReceiptReason receiptReason = (ReceiptReason) receiptReasonList.get(i);
			receiptReason.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "reasonState", receiptReason.getState()));
			receiptReason.setTypeReamrk(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptType", receiptReason.getType()));
			lst.add(receiptReason);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ReceiptReason.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}	
		
		
	// 删除
	public String delete() {
		ids = id.split(",");
		receiptReasonService.updateisdel(ids, "Y");
		redirectionUrl = "receipt_reason!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}			
		
		
	// 更新
	public String update() {
		ReceiptReason persistent = receiptReasonService.load(id);
		BeanUtils.copyProperties(receiptReason, persistent, new String[] { "id",
				"createDate", "modifyDate","type","isDel"});
		receiptReasonService.update(persistent);
		redirectionUrl = "receipt_reason!list.action";
		return SUCCESS;
	}	
			
	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {			
		receiptReason.setIsDel("N");
		receiptReasonService.save(receiptReason);
		redirectionUrl = "receipt_reason!list.action";
		return SUCCESS;
	}	

	public ReceiptReason getReceiptReason() {
		return receiptReason;
	}

	public void setReceiptReason(ReceiptReason receiptReason) {
		this.receiptReason = receiptReason;
	}		
		
	public List<Dict> getAllType() {
		return dictService.getList("dictname", "receiptType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "stateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}
}
