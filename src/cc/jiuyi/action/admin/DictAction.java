package cc.jiuyi.action.admin;

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
import cc.jiuyi.service.DictService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - DICT
 */

@ParentPackage("admin")
public class DictAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private Dict dict;
	private String type;

	@Resource
	private DictService dictService;
	
	//获取dict的html标签,状态
	public String getDict1() {
		try
		{
			List<Dict> list=dictService.getState(dict.getDictname());
			if(list.size()>0)
			{
				String str="<select>";
				str+="<option value=''>---请选择---</option>";
				for(int i=0;i<list.size();i++)
				{
					Dict d=list.get(i);
					str+="<option value='"+d.getDictkey()+"'>"+d.getDictvalue()+"</option>";
				}
				str+="</select>";
				return ajaxHtml(str);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ajaxHtml("<select><option></option></select>");
		}
		return ajaxHtml("<select><option></option></select>");
	}
	
	public String getDictByAcess(){
		List<Dict> dictList = dictService.getState(dict.getDictname());
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<dictList.size();i++){
			Dict dict = dictList.get(i);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("dictkey", dict.getDictkey());
			jsonobject.put("dictvalue", dict.getDictvalue());
			jsonarray.add(jsonobject);
		}
		JSONObject json = new JSONObject();
		json.put("list", jsonarray);
		
		return ajaxJson(json.toString());
	}
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		dict = dictService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("createDate");
		}
		pager = dictService.findByPager(pager);
		
		
		//JSONArray jsonArray = JSONArray.fromObject(pager);
		
		//return ajaxJson
		//System.out.println(jsonArray.toString());
		return LIST;
		//return ajaxJson(jsonArray.toString());
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
//		Repairorder repa = new Repairorder();
//		repa.syncRepairorder();
		HashMap map = new HashMap();
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("createDate");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		if(pager.is_search()==true && Param != null){//普通搜索功能
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject param = JSONObject.fromObject(Param);
			//dict = (Dict)JSONObject.toBean(param,Dict.class);
			String startdate = param.get("startdate").toString();
			String enddate = param.get("enddate").toString();
			map.put("startdate", startdate);
		}
		
		pager = dictService.getDictPager(pager,map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		//System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	// 删除
	public String delete() {
		ids = id.split(",");
		dictService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
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
		dictService.save(dict);
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
		Dict persistent = dictService.load(id);
		BeanUtils.copyProperties(dict, persistent, new String[]{"id", "createDate", "modifyDate"});
		dictService.update(persistent);
		redirectionUrl = "dict!list.action";
		return SUCCESS;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}