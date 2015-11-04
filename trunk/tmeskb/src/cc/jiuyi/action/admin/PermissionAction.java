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
import cc.jiuyi.entity.Permission;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PermissionService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-权限管理
 */

@ParentPackage("admin")
public class PermissionAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Permission permission;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private PermissionService permissionService;
	@Resource
	private DictService dictService;
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
//		List<Permission> permissionList = pager.getList();
//		for (Permission permission1 : permissionList) {
//			permission1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"permissionState", permission1.getState()));
//		}
		//dictService.getDictValueByDictKey("permissionState", permission.getState());
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
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
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("permissionName") != null) {
				System.out.println("obj=" + obj);
				String permissionName = obj.getString("permissionName").toString();
				map.put("permissionName", permissionName);
			}
			if (obj.get("permissionType") != null) {
				String permissionType = obj.getString("permissionType").toString();
				map.put("permissionType", permissionType);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = permissionService.getPermissionPager(pager, map);
			List<Permission> permissionList = pager.getList();
			List<Permission> lst = new ArrayList<Permission>();
			for (int i = 0; i < permissionList.size(); i++) {
				Permission permission = (Permission) permissionList.get(i);
				permission.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "permissionState", permission.getState()));
				lst.add(permission);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		permissionService.updateisdel(ids, "Y");
//		for (String id:ids){
//			Permission permission=permissionService.load(id);
//		}
		redirectionUrl = "permission!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			permission= permissionService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Permission persistent = permissionService.load(id);
			BeanUtils.copyProperties(permission, persistent, new String[] { "id","createDate", "modifyDate"});
			permissionService.update(persistent);
			redirectionUrl = "permission!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "permission.permissionName", message = "权限名称不允许为空!"),
					@RequiredStringValidator(fieldName = "permission.permissionType", message = "权限类型不允许为空!")
			  },
			requiredFields = {
					@RequiredFieldValidator(fieldName = "permission.orderList", message = "排序不允许为空!")
						
			}, 
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "permission.orderList", min = "0", message = "排序必须为零或正整数!")
				}
			  
	)
	public String save()throws Exception{
		permissionService.save(permission);
		redirectionUrl="permission!list.action";
		return SUCCESS;	
	}
		


	public Permission getPermission() {
		return permission;
	}


	public void setPermission(Permission permission) {
		this.permission = permission;
	}


	public PermissionService getPermissionService() {
		return permissionService;
	}


	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}


	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "StateRemark");
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
