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
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-领/退料从表
 */

@ParentPackage("admin")
public class PickDetailAction extends BaseAdminAction {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8353535527507793596L;
	
	private PickDetail pickDetail;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private PickDetailService pickDetailService;
	@Resource
	private DictService dictService;
	
//	//是否已存在ajax验证
//	public String checkPickDetailsCode(){
//		String pickDetailCode=pickDetail.getPickDetailCode();
//		if(pickDetailService.isExistByProccessCode(pickDetailCode)){
//			return ajaxText("false");
//		}else{
//			return ajaxText("true");
//		}
//	}
	
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
//		List<PickDetail> pickDetailList = pager.getList();
//		for (PickDetail pickDetail1 : pickDetailList) {
//			pickDetail1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"pickDetailState", pickDetail1.getState()));
//		}
		//dictService.getDictValueByDictKey("pickDetailState", pickDetail.getState());
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
//			if (obj.get("pickDetailCode") != null) {
//				System.out.println("obj=" + obj);
//				String pickDetailCode = obj.getString("pickDetailCode").toString();
//				map.put("pickDetailCode", pickDetailCode);
//			}
//			if (obj.get("pickDetailName") != null) {
//				String pickDetailName = obj.getString("pickDetailName").toString();
//				map.put("pickDetailName", pickDetailName);
//			}
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

			pager = pickDetailService.getPickDetailPager(pager, map);
			List<PickDetail> pickDetailList = pager.getList();
			List<PickDetail> lst = new ArrayList<PickDetail>();
			for (int i = 0; i < pickDetailList.size(); i++) {
				PickDetail pickDetail = (PickDetail) pickDetailList.get(i);
				pickDetail.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "pickDetailState", pickDetail.getState()));
				lst.add(pickDetail);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		pickDetailService.updateisdel(ids, "Y");
//		for (String id:ids){
//			PickDetail pickDetail=pickDetailService.load(id);
//		}
		redirectionUrl = "pickDetail!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			pickDetail= pickDetailService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			PickDetail persistent = pickDetailService.load(id);
			BeanUtils.copyProperties(pickDetail, persistent, new String[] { "id","createDate", "modifyDate"});
			pickDetailService.update(persistent);
			redirectionUrl = "pickDetail!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "pickDetail.pickType", message = "领料类型不能为空!"),
					
			  },
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "pickDetail.pickAmount", min = "0", message = "领料数量不能为空!")
				
			}
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		pickDetailService.save(pickDetail);
		redirectionUrl="pickDetail!list.action";
		return SUCCESS;	
	}
		


	public PickDetail getPickDetail() {
		return pickDetail;
	}


	public void setPickDetail(PickDetail pickDetail) {
		this.pickDetail = pickDetail;
	}


	public PickDetailService getPickDetailService() {
		return pickDetailService;
	}


	public void setPickDetailService(PickDetailService pickDetailService) {
		this.pickDetailService = pickDetailService;
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
