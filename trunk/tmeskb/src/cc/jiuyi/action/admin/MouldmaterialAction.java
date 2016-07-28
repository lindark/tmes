package cc.jiuyi.action.admin;

import java.io.IOException;
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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Equipments;
import cc.jiuyi.entity.Mouldmaterial;
import cc.jiuyi.sap.rfc.EquipmentRfc;
import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EquipmentService;
import cc.jiuyi.service.MouldmaterialService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 模具物料
 */
@ParentPackage("admin")
public class MouldmaterialAction extends BaseAdminAction {

	private static final long serialVersionUID = 6732237421792628782L;

	private Mouldmaterial mouldmaterial;
	

	@Resource
	private MouldmaterialService mouldService;
	@Resource
	private DictService dictService;
	
	// 添加
	public String add() {	
		return INPUT;
	}	

	// 编辑
	public String edit() {
		mouldmaterial = mouldService.load(id);
		return INPUT;
	}	

	// 列表
	public String list() {
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
			
		   if (obj.get("factory") != null) { 
			   String factory = obj.getString("factory").toString();
			   map.put("factory",factory);
			}

		   if (obj.get("materialCode") != null) { 
			   String materialCode = obj.getString("materialCode").toString();
			   map.put("materialCode",materialCode);
		   }

		}

		pager = mouldService.getMouldmaterialPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Mouldmaterial mould = (Mouldmaterial) pagerlist.get(i);
		
			mould.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "equipmentState", mould.getState()));
			
			pagerlist.set(i,mould);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Mouldmaterial.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}	

	// 保存
	public String save() {		
		mouldmaterial.setIsDel("N");
		mouldService.save(mouldmaterial);     
		redirectionUrl = "mouldmaterial!list.action";
		return SUCCESS;
	}	
		

	public String update() {
		Mouldmaterial persistent = mouldService.load(id);
		BeanUtils.copyProperties(mouldmaterial, persistent, new String[] { "id","createDate", "modifyDate","isDel","state",});		
		mouldService.update(persistent);
		redirectionUrl = "mouldmaterial!list.action";
		return SUCCESS;
	}	
		
    // 删除
	public String delete() throws Exception {
		ids = id.split(",");
		mouldService.updateisdel(ids, "Y");
		redirectionUrl = "mouldmaterial!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Mouldmaterial getMouldmaterial() {
		return mouldmaterial;
	}

	public void setMouldmaterial(Mouldmaterial mouldmaterial) {
		this.mouldmaterial = mouldmaterial;
	}	
	
	
	
}
