package cc.jiuyi.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.SystemConfig;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.compass.core.json.JsonObject;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - DICT
 */

@ParentPackage("admin")
public class DictAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private Dict dict;
	@Resource
	private DictService dictService;
	
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
			pager.setOrderBy("orderList");
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
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		pager = dictService.findByPager(pager);
		
		List lis = pager.getList();
		//JSONArray jsona = JSONArray.fromObject(lis);
		JSONObject obj = new JSONObject();
		String jsonstr = obj.put("list", lis).toString();
		List<Map<String, Object>> optionList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<pager.getList().size();i++){
			Pager pa = (Pager) pager.getList().get(i);
		}
		
//		JSONArray jsonArray = JSONArray.fromObject(optionList);
//		
//		
//		
		//JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonstr.toString());
//		return ajaxJson(jsonArray.toString());
		
		
		 String jsondata = "{" +
			        "      \"list\":" +
			        "          [" +
			        "            {" +
			        "              \"id\":\"1\"," +
			        "              \"name\":\"13\"" +
//			        "              \"cell\":" +
//			        "                  [\"13\",\"2007-10-06\",\"Client 3\",\"1000.00\",\"0.00\",\"1000.00\",null]" +
			        "            }" +
//			        "            {" +
//			        "              \"id\":\"12\"," +
//			        "              \"cell\":" +
//			        "                  [\"12\",\"2007-10-06\",\"Client 2\",\"700.00\",\"140.00\",\"840.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"11\"," +
//			        "              \"cell\":" +
//			        "                  [\"11\",\"2007-10-06\",\"Client 1\",\"600.00\",\"120.00\",\"720.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"10\"," +
//			        "              \"cell\":" +
//			        "                  [\"10\",\"2007-10-06\",\"Client 2\",\"100.00\",\"20.00\",\"120.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"9\"," +
//			        "              \"cell\":" +
//			        "                  [\"9\",\"2007-10-06\",\"Client 1\",\"200.00\",\"40.00\",\"240.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"8\"," +
//			        "              \"cell\":" +
//			        "                  [\"8\",\"2007-10-06\",\"Client 3\",\"200.00\",\"0.00\",\"200.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"7\"," +
//			        "              \"cell\":" +
//			        "                  [\"7\",\"2007-10-05\",\"Client 2\",\"120.00\",\"12.00\",\"134.00\",null]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"6\"," +
//			        "              \"cell\":" +
//			        "                  [\"6\",\"2007-10-05\",\"Client 1\",\"50.00\",\"10.00\",\"60.00\",\"\"]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"5\"," +
//			        "              \"cell\":" +
//			        "                  [\"5\",\"2007-10-05\",\"Client 3\",\"100.00\",\"0.00\",\"100.00\",\"no tax at all\"]" +
//			        "            }," +
//			        "            {" +
//			        "              \"id\":\"4\"," +
//			        "              \"cell\":" +
//			        "                  [\"4\",\"2007-10-04\",\"Client 3\",\"150.00\",\"0.00\",\"150.00\",\"no tax\"]" +
//			        "            }" +
			        "          ]" +
//			        "      \"userdata\":{\"amount\":3220,\"tax\":342,\"total\":3564,\"name\":\"Totals:\"}" +
			        "    }";
		 
		 return ajaxJson(jsonstr.toString());
		
	}

	// 删除
	public String delete() {
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

}