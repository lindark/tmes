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
import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class CardManagementAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private CardManagement cardManagement;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private CardManagementService cardManagementService;
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
//		List<CardManagement> cardManagementList = pager.getList();
//		for (CardManagement cardManagement1 : cardManagementList) {
//			cardManagement1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"cardManagementState", cardManagement1.getState()));
//		}
		//dictService.getDictValueByDictKey("cardManagementState", cardManagement.getState());
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
			if (obj.get("posCode") != null) {
				System.out.println("obj=" + obj);
				String cardManagementCode = obj.getString("posCode").toString();
				map.put("posCode", cardManagementCode);
			}
			if (obj.get("pcIp") != null) {
				String cardManagementName = obj.getString("pcIp").toString();
				map.put("pcIp", cardManagementName);
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

			pager = cardManagementService.getCardManagementPager(pager, map);
			List<CardManagement> cardManagementList = pager.getList();
			List<CardManagement> lst = new ArrayList<CardManagement>();
			for (int i = 0; i < cardManagementList.size(); i++) {
				CardManagement cardManagement = (CardManagement) cardManagementList.get(i);
				cardManagement.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "cardManagementState", cardManagement.getState()));
				lst.add(cardManagement);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		cardManagementService.updateisdel(ids, "Y");
//		for (String id:ids){
//			CardManagement cardManagement=cardManagementService.load(id);
//		}
		redirectionUrl = "card_management!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			cardManagement= cardManagementService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			CardManagement persistent = cardManagementService.load(id);
			BeanUtils.copyProperties(cardManagement, persistent, new String[] { "id","createDate", "modifyDate"});
			cardManagementService.update(persistent);
			redirectionUrl = "card_management!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "cardManagement.posCode", message = "刷卡机编码不允许为空!"),
					@RequiredStringValidator(fieldName = "cardManagement.pcIp", message = "电脑IP不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		cardManagementService.save(cardManagement);
		redirectionUrl="card_management!list.action";
		return SUCCESS;	
	}
		


	public CardManagement getCardManagement() {
		return cardManagement;
	}


	public void setCardManagement(CardManagement cardManagement) {
		this.cardManagement = cardManagement;
	}


	public CardManagementService getCardManagementService() {
		return cardManagementService;
	}


	public void setCardManagementService(CardManagementService cardManagementService) {
		this.cardManagementService = cardManagementService;
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
