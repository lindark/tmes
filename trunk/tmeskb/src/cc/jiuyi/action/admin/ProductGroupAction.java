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
import cc.jiuyi.entity.ProductGroup;
import cc.jiuyi.service.ProductGroupService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 缺陷代码管理
 * 
 */
@ParentPackage("admin")
public class ProductGroupAction extends BaseAdminAction {

	private static final long serialVersionUID = -5636235171155711396L;
	
	private ProductGroup productGroup;
	
	@Resource
	private ProductGroupService productGroupService;
	
	public String list() {
		//pager = productGroupService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productGroup = productGroupService.load(id);
		return INPUT;
	}

	//保存
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "productGroup.productGroupCode", message = "产品组编号不允许为空!"),
						@RequiredStringValidator(fieldName = "productGroup.productName", message = "产品组名字不允许为空!")
				  }						  
		)
	@InputConfig(resultName = "error")
	public String save() {
		productGroupService.save(productGroup);
		redirectionUrl = "product_group!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		ProductGroup persistent = productGroupService.load(id);
		BeanUtils.copyProperties(productGroup, persistent, new String[] { "id" });
		productGroupService.update(persistent);
		redirectionUrl = "product_group!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		productGroupService.updateisdel(ids, "Y");
		redirectionUrl = "product_group!list.action";
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
			if(obj.get("productGroupCode") != null){
				String productGroupCode = obj.get("productGroupCode").toString();
				map.put("productGroupCode", productGroupCode);				
			}
			if(obj.get("productGroupType")!=null){
				String productGroupType = obj.get("productGroupType").toString();
				map.put("productGroupType", productGroupType);				
			}
			if(obj.get("productGroupName")!=null){
				String productGroupName = obj.get("productGroupName").toString();
				map.put("productGroupName", productGroupName);				
			}		
		}
		pager = productGroupService.getProductGroupPager(pager,map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}
	
}
