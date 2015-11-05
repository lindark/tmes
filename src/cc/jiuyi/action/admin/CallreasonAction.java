package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.service.CallreasonService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 呼叫原因
 * 
 */
@ParentPackage("admin")
public class CallreasonAction extends BaseAdminAction {

	private static final long serialVersionUID = 2035858552742339646L;
	
	private Callreason callreason;
	
	@Resource
	private CallreasonService callreasonService;
	
	public String list() {
		//pager = callreasonService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		callreason = callreasonService.load(id);
		return INPUT;
	}

	//保存
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "callreason.callType", message = "呼叫类型不允许为空!"),
						@RequiredStringValidator(fieldName = "callreason.callReason", message = "呼叫原因不允许为空!")
				  }
				  
		)
	@InputConfig(resultName = "error")
	public String save() {
		callreasonService.save(callreason);
		redirectionUrl = "callreason!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Callreason persistent = callreasonService.load(id);
		BeanUtils.copyProperties(callreason, persistent, new String[] { "id" });
		callreasonService.update(persistent);
		redirectionUrl = "callreason!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		callreasonService.updateisdel(ids, "Y");
		redirectionUrl = "callreason!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
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
		if(pager.is_search()==true && filters != null){//需要查询条件
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
			JSONObject obj = JSONObject.fromObject(Param);
			if(obj.get("callType") != null){
				String callType = obj.get("callType").toString();
				map.put("callType", callType);				
			}
			if(obj.get("callReason")!=null){
				String callReason = obj.get("callReason").toString();
				map.put("callReason", callReason);				
			}
			if(obj.get("state")!=null){
				String state = obj.get("state").toString();
				map.put("state", state);				
			}		
		}
		pager = callreasonService.getCallreasonPager(pager,map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}

	public Callreason getCallreason() {
		return callreason;
	}

	public void setCallreason(Callreason callreason) {
		this.callreason = callreason;
	}
	

}
