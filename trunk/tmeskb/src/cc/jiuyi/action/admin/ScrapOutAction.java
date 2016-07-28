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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.ScrapOut;
import cc.jiuyi.sap.rfc.ScrapOutRfc;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ScrapOutService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 报废产出对照表
 * 
 */
@ParentPackage("admin")
public class ScrapOutAction extends BaseAdminAction {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274164866494004150L;
	
	
	private ScrapOut scrapOut;
	// 获取所有状态
	private List<Dict> allState;
	// 获取所有代码类型
	private List<Dict> allScrapOutType;

	@Resource
	private ScrapOutService scrapOutService;
	@Resource
	private DictService dictService;
	@Resource
	private ScrapOutRfc scrapOutRfc;

	public String sync(){
		try {
			List<ScrapOut> outList = scrapOutRfc.getScrapOut("", "", "", "");
			scrapOutService.mergeScrapOutList(outList);
		} catch (IOException e) {
			addActionError("IO写入失败!");
			e.printStackTrace();
			return ERROR;
		} catch (CustomerException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return ERROR;
		} catch(Exception e){
			addActionError("系统出现错误，请联系系统管理员");
			e.printStackTrace();
			return ERROR;
		}
		
		
		return SUCCESS;
	}
	
	
	
	public String list() {
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);
		pager.setOrderBy("modifyDate");
		return LIST;
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		scrapOut = scrapOutService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "scrapOut.productsCode", message = "产品编码不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.productsName", message = "产品名称不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.productsUnit", message = "产品单位不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.materialCode", message = "物料编码不允许为空!"),
	        @RequiredStringValidator(fieldName = "scrapOut.materialName", message = "物料描述不允许为空!"),
	        @RequiredStringValidator(fieldName = "scrapOut.materialUnit", message = "物料单位不允许为空!") }

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		scrapOutService.save(scrapOut);
		redirectionUrl = "scrap_out!list.action";
		return SUCCESS;
	}

	//更新
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "scrapOut.productsCode", message = "产品编码不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.productsName", message = "产品名称不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.productsUnit", message = "产品单位不允许为空!"),
			@RequiredStringValidator(fieldName = "scrapOut.materialCode", message = "物料编码不允许为空!"),
	        @RequiredStringValidator(fieldName = "scrapOut.materialName", message = "物料描述不允许为空!"),
	        @RequiredStringValidator(fieldName = "scrapOut.materialUnit", message = "物料单位不允许为空!") }
	)
	@InputConfig(resultName = "error")
	public String update() {
		ScrapOut persistent = scrapOutService.load(id);
		BeanUtils.copyProperties(scrapOut, persistent, new String[] { "id" });
		scrapOutService.update(persistent);
		redirectionUrl = "scrap_out!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		scrapOutService.updateisdel(ids, "Y");
		redirectionUrl = "scrap_out!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
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

		if (pager.is_search() == true && Param != null) {
			
			
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("productsCode") != null) {
				//System.out.println("obj=" + obj);
				String productsCode = obj.getString("productsCode").toString();
				map.put("productsCode", productsCode);
			}
			if (obj.get("productsName") != null) {
				String productsName = obj.getString("productsName").toString();
				map.put("productsName", productsName);
			}
			if (obj.get("materialCode") != null) {
				String state = obj.getString("materialCode").toString();
				map.put("materialCode", state);
			}
			if (obj.get("materialName") != null) {
				String state = obj.getString("materialName").toString();
				map.put("materialName", state);
			}
		}
		pager = scrapOutService.getScrapOutPager(pager, map);
		List<ScrapOut> scrapOutList = pager.getList();
		pager.setList(scrapOutList);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ScrapOut.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}



	public ScrapOut getScrapOut() {
		return scrapOut;
	}

	public void setScrapOut(ScrapOut scrapOut) {
		this.scrapOut = scrapOut;
	}

	// 获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "scrapOutState");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public List<Dict> getAllScrapOutType() {
		return dictService.getList("dictname", "scrapOutTypeRemark");
	}

	public void setAllScrapOutType(List<Dict> allScrapOutType) {
		this.allScrapOutType = allScrapOutType;
	}

}
