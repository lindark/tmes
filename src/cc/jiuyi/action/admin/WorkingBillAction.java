package cc.jiuyi.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.SystemConfig;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.Repairorder;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.ThinkWayUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.compass.core.json.JsonObject;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - DICT
 */

@ParentPackage("admin")
public class WorkingBillAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private WorkingBill workingbill;
	

	@Resource
	private WorkingBillService workingbillService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		return INPUT;
	}

	// List 页面
	public String list() {
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		HashMap<String,String> map = new HashMap<String,String>();
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
			if(!filters.equals("")){
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		if(pager.is_search()==true && Param != null){//普通搜索功能
			if(!Param.equals("")){
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject param = JSONObject.fromObject(Param);
				String start = ThinkWayUtil.null2String(param.get("start"));
				String end = ThinkWayUtil.null2String(param.get("end"));
				String workingBillCode = ThinkWayUtil.null2String(param.get("workingBillCode"));//随工单
				map.put("start", start);
				map.put("end", end);
				map.put("workingBillCode", workingBillCode);
			}
		}
		
		pager = workingbillService.findPagerByjqGrid(pager, map);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			WorkingBill workingbill  = (WorkingBill)pagerlist.get(i);
			workingbill.setEnteringwareHouse(null);
			pagerlist.set(i, workingbill);
		}
		pager.setList(pagerlist);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	// 删除
	public String delete() {
		ids = id.split(",");
		workingbillService.updateisdel(ids,"Y");
		redirectionUrl = "working_bill!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	//同步
	public String sync() {
		Repairorder r = new Repairorder();
		try {
			r.syncRepairorder(workingbillService);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}//同步
		redirectionUrl = "working_bill!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "dict.dictname", message = "字典名不允许为空!"),
			@RequiredStringValidator(fieldName = "dict.dictkey", message = "字典Key不允许为空!"),
			@RequiredStringValidator(fieldName = "dict.dictvalue", message = "字典Value不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "dict.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "dict.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		//workingbillService.save(dict);
		redirectionUrl = "dict!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "dict.dictname", message = "字典名不允许为空!"),
			@RequiredStringValidator(fieldName = "dict.dictkey", message = "字典Key不允许为空!"),
			@RequiredStringValidator(fieldName = "dict.dictvalue", message = "字典Value不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "dict.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "dict.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		//Dict persistent = dictService.load(id);
		//BeanUtils.copyProperties(dict, persistent, new String[]{"id", "createDate", "modifyDate"});
		//dictService.update(persistent);
		redirectionUrl = "dict!list.action";
		return SUCCESS;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}


}