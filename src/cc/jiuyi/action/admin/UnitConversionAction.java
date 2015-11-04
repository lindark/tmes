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
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-单位转换
 */

@ParentPackage("admin")
public class UnitConversionAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private UnitConversion unitConversion;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private UnitConversionService unitConversionService;
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
//		List<UnitConversion> unitConversionList = pager.getList();
//		for (UnitConversion unitConversion1 : unitConversionList) {
//			unitConversion1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"unitConversionState", unitConversion1.getState()));
//		}
		//dictService.getDictValueByDictKey("unitConversionState", unitConversion.getState());
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
			if (obj.get("unitCode") != null) {
				System.out.println("obj=" + obj);
				String unitConversionCode = obj.getString("unitCode").toString();
				map.put("unitCode", unitConversionCode);
			}
			if (obj.get("unitDescription") != null) {
				String unitConversionName = obj.getString("unitDescription").toString();
				map.put("unitDescription", unitConversionName);
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

			pager = unitConversionService.getUnitConversionPager(pager, map);
			List<UnitConversion> unitConversionList = pager.getList();
			List<UnitConversion> lst = new ArrayList<UnitConversion>();
			for (int i = 0; i < unitConversionList.size(); i++) {
				UnitConversion unitConversion = (UnitConversion) unitConversionList.get(i);
				unitConversion.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "unitConversionState", unitConversion.getState()));
				lst.add(unitConversion);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		unitConversionService.updateisdel(ids, "Y");
//		for (String id:ids){
//			UnitConversion unitConversion=unitConversionService.load(id);
//		}
		redirectionUrl = "unitConversion!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			unitConversion= unitConversionService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			UnitConversion persistent = unitConversionService.load(id);
			BeanUtils.copyProperties(unitConversion, persistent, new String[] { "id","createDate", "modifyDate"});
			unitConversionService.update(persistent);
			redirectionUrl = "unitConversion!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "unitConversion.unitCode", message = "单位编码不允许为空!"),
					@RequiredStringValidator(fieldName = "unitConversion.unitDescription", message = "单位描述不允许为空!"),
					@RequiredStringValidator(fieldName = "unitConversion.conversationRatio", message = "兑换比例不允许为空!"),
					@RequiredStringValidator(fieldName = "unitConversion.convertUnit", message = "转换单位不允许为空!"),
			  },
			requiredFields = {
					@RequiredFieldValidator(fieldName = "unitConversion.orderList", message = "排序不允许为空!")
						
			}, 
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "unitConversion.orderList", min = "0", message = "排序必须为零或正整数!")
				}
			  
	)
	public String save()throws Exception{
		unitConversionService.save(unitConversion);
		redirectionUrl="unitConversion!list.action";
		return SUCCESS;	
	}
		


	public UnitConversion getUnitConversion() {
		return unitConversion;
	}


	public void setUnitConversion(UnitConversion unitConversion) {
		this.unitConversion = unitConversion;
	}


	public UnitConversionService getUnitConversionService() {
		return unitConversionService;
	}


	public void setUnitConversionService(UnitConversionService unitConversionService) {
		this.unitConversionService = unitConversionService;
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
