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
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.service.RepairinService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 返修收货
 * 
 */
@ParentPackage("admin")
public class RepairinAction extends BaseAdminAction {

	private static final long serialVersionUID = -5368121517667092305L;
	
	private Repairin repairin;
	
	@Resource
	private RepairinService repairinService;
	
	public String list() {
		//pager = repairinService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		repairin = repairinService.load(id);
		return INPUT;
	}

	public String save() {
		repairinService.save(repairin);
		redirectionUrl = "repairin!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Repairin persistent = repairinService.load(id);
		BeanUtils.copyProperties(repairin, persistent, new String[] { "id" });
		repairinService.update(persistent);
		redirectionUrl = "repairin!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		repairinService.updateisdel(ids, "Y");
		redirectionUrl = "repairin!list.action";
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
			if(obj.get("receiveAmount") != null){
				String receiveAmount = obj.get("receiveAmount").toString();
				map.put("receiveAmount", receiveAmount);				
			}
			if(obj.get("totalAmount")!=null){
				String totalAmount = obj.get("totalAmount").toString();
				map.put("totalAmount", totalAmount);				
			}
			if(obj.get("state")!=null){
				String state = obj.get("state").toString();
				map.put("state", state);				
			}
			if(obj.get("confirmUser")!=null){
				String confirmUser = obj.get("confirmUser").toString();
				map.put("confirmUser", confirmUser);				
			}
		}
		pager = repairinService.getRepairinPager(pager,map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}

	public Repairin getRepairin() {
		return repairin;
	}

	public void setRepairin(Repairin repairin) {
		this.repairin = repairin;
	}
	

}
