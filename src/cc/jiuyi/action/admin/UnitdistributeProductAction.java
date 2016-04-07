package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
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
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-单元管理
 */

@ParentPackage("admin")
public class UnitdistributeProductAction extends BaseAdminAction {

	private static final long serialVersionUID = -433964280757192334L;

	private UnitdistributeProduct unitdistributeProduct;
	
	
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private DictService dictService;
	
	//添加
	public String add(){
		return INPUT;
	}
	
	//列表
	public String list(){
		return LIST;
	}
	
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist()
	{

		HashMap<String, String> map = new HashMap<String, String>();

		if (pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);
		pager.setOrderBy("modifyDate");
		if (pager.is_search() == true && filters != null)
		{// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null)
		{// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			
			if (obj.get("unitName") != null)
			{
				String unitName = obj.getString("unitName").toString();
				map.put("unitName", unitName);
			}
			if (obj.get("materialName") != null)
			{
				String materialName = obj.getString("materialName").toString();
				map.put("materialName", materialName);
			}
		}

		pager = unitdistributeProductService.getUnitProductPager(pager, map);
		List<UnitdistributeProduct> unitList = pager.getList();
		List<UnitdistributeProduct> lst = new ArrayList<UnitdistributeProduct>();
		for (int i = 0; i < unitList.size(); i++)
		{
			UnitdistributeProduct unit = (UnitdistributeProduct) unitList.get(i);
			unit.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "unitdistributeProductState", unit.getState()));
			unit.setXunitName(unit.getFactoryunit().getFactoryUnitName());
			unit.setXunitCode(unit.getFactoryunit().getFactoryUnitCode());
			lst.add(unit);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UnitdistributeProduct.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		unitdistributeProductService.updateisdel(ids, "Y");
		redirectionUrl = "unitdistribute_product!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	
	//编辑
		public String edit(){
			unitdistributeProduct= unitdistributeProductService.load(id);
			return INPUT;	
		}
		
	//单元选择
	public String browser(){		
		return "browser";	
	}
		
	//更新
		/*@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitCode", message = "单元编号不允许为空!"),
						@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitName", message = "单元名称不允许为空!")
				  }
				  
		)
		@InputConfig(resultName = "error")*/
		public String update() {
			UnitdistributeProduct persistent = unitdistributeProductService.load(id);
			//BeanUtils.copyProperties(unitdistributeProduct, persistent, new String[] { "id","createDate", "modifyDate","unitCode","unitName"});
			persistent.setMaterialName(unitdistributeProduct.getMaterialName());
			persistent.setState(unitdistributeProduct.getState());
			persistent.setModifyDate(new Date());
			unitdistributeProductService.update(persistent);
			redirectionUrl = "unitdistribute_product!list.action";
			return SUCCESS;
		}
		
	//保存
	/*@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitCode", message = "单元编号不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitName", message = "单元名称不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.warehouse", message = "线边仓编码不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.warehouseName", message = "线边仓描述不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")*/
	public String save(){
		if(check())
		{
			unitdistributeProductService.save(unitdistributeProduct);		
			redirectionUrl="unitdistribute_product!list.action";
			return SUCCESS;	
		}
		this.addActionError("单元和物料编码已同时存在!");
		return ERROR;
	}
	
	/**
	 * 根据单元id和物料编码查询是否已存在
	 * @return
	 */
	public String checkinfo()
	{
		if(check())
		{
			return this.ajaxJsonSuccessMessage("");
		}
		return this.ajaxJsonErrorMessage("");
	}
	/**
	 * 根据单元id和物料编码查询是否已存在
	 * @return
	 */
	public boolean check()
	{
		UnitdistributeProduct up=this.unitdistributeProductService.getByConditions(unitdistributeProduct.getFactoryunit().getId(),unitdistributeProduct.getMaterialCode());
		if(up!=null)
		{
			return false;
		}
		return true;
	}

	public UnitdistributeProduct getUnitdistributeProduct() {
		return unitdistributeProduct;
	}

	public void setUnitdistributeProduct(UnitdistributeProduct unitdistributeProduct) {
		this.unitdistributeProduct = unitdistributeProduct;
	}
		
	
	
}
