package cc.jiuyi.action.admin;

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
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EquipmentService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 设备
 */
@ParentPackage("admin")
public class EquipmentsAction extends BaseAdminAction {

	private static final long serialVersionUID = 6734237423792688782L;

	private Equipments equipments;
	// 获取所有类型
	private List<Dict> allVersion;
	

	@Resource
	private EquipmentService equipmentService;
	@Resource
	private DictService dictService;
	
	
	// 是否已存在 ajax验证
	public String checkNo() {
		String equipmentNo = equipments.getEquipmentNo();
		if (equipmentService.isExistByEquipmentNo(equipmentNo)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}	
	
	// 添加
	public String add() {	
		return INPUT;
	}	

	// 编辑
	public String edit() {
		equipments = equipmentService.load(id);
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
			
		   if (obj.get("deviceNo") != null) { 
			   String deviceNo = obj.getString("deviceNo").toString();
			   map.put("deviceNo",deviceNo);
			}

		   if (obj.get("deviceName") != null) { 
			   String deviceName = obj.getString("deviceName").toString();
			   map.put("deviceName",deviceName);
		   }

		}

		pager = equipmentService.getEquipmentPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Equipments equipment = (Equipments) pagerlist.get(i);
		
			equipment.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "equipmentState", equipment.getState()));
			equipment.setVersionReamrk(ThinkWayUtil.getDictValueByDictKey(
					dictService, "equipmentVersion", equipment.getVersion()));
			
			pagerlist.set(i,equipment);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Equipments.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}	

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "equipments.equipmentName", message = "设备名称不允许为空!"),
			@RequiredStringValidator(fieldName = "equipments.equipmentNo", message = "设备编号不允许为空!") })
	@InputConfig(resultName = "error")	
	public String save() {		
		equipments.setIsDel("N");
		equipmentService.save(equipments);     
		redirectionUrl = "equipments!list.action";
		return SUCCESS;
	}	
		

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "equipments.equipmentName", message = "设备名称不允许为空!"),
			@RequiredStringValidator(fieldName = "equipments.equipmentNo", message = "设备编号不允许为空!") })
	@InputConfig(resultName = "error")	
	public String update() {
		System.out.println("into");
		Equipments persistent = equipmentService.load(id);
		BeanUtils.copyProperties(equipments, persistent, new String[] { "id","createDate", "modifyDate","isDel","state",});		
		equipmentService.update(persistent);
		redirectionUrl = "equipments!list.action";
		return SUCCESS;
	}	
		
    // 删除
	public String delete() throws Exception {
		ids = id.split(",");
		equipmentService.updateisdel(ids, "Y");
		redirectionUrl = "equipments!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}	
	
	public Equipments getEquipments() {
		return equipments;
	}

	public void setEquipments(Equipments equipments) {
		this.equipments = equipments;
	}

	
	public List<Dict> getAllVersion() {
		return dictService.getList("dictname", "equipmentVersion");
	}

	public void setAllVersion(List<Dict> allVersion) {
		this.allVersion = allVersion;
	}
	
	
}
