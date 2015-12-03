package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-领/退料主表
 */

@ParentPackage("admin")
public class PickAction extends BaseAdminAction {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6045295823911487260L;
	
	private static final String CONFIRMED="2";
	private static final String REPEAL="3";
	
	private Pick pick;
	//获取所有状态
	private List<Dict> allState;
	private Admin admin;
	
	@Resource
	private PickService pickService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private PickDetailService pickDetailService;
	@Resource
	private AdminService adminService;
	@Resource
	private PickRfcImpl pickRfcImple;
	
	
	private String workingBillId;
	private WorkingBill workingbill;
	private String matnr;
	private List<PickDetail> pkList;
	private String pickRfc;
	

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
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill=workingBillService.get(workingBillId);
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
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

			pager = pickService.getPickPager(pager, map);
			List<Pick> pickList = pager.getList();
			List<Pick> lst = new ArrayList<Pick>();
			for (int i = 0; i < pickList.size(); i++) {
				Pick pick = (Pick) pickList.get(i);
				pick.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "pickState", pick.getState()));
				if(pick.getConfirmUser()!=null){
					pick.setXconfirmUser(pick.getConfirmUser().getName());
				}
				if(pick.getCreateUser()!=null){
					pick.setXcreateUser(pick.getCreateUser().getName());
				}	
				lst.add(pick);
			}
		pager.setList(lst);
		JsonConfig jsonConfig=new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Pick.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		pickService.updateisdel(ids, "Y");
		for (String id:ids){
			Pick pick=pickService.load(id);
		}
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			pick= pickService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Pick persistent = pickService.load(id);
			BeanUtils.copyProperties(pick, persistent, new String[] { "id","createDate", "modifyDate"});
			pickService.update(persistent);
			redirectionUrl = "pick!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		pickService.save(pick);
		redirectionUrl="pick!list.action";
		return SUCCESS;	
	}
		
	//刷卡确认
	public String confirms() {
		ids= id.split(",");
		for (int i = 0; i < ids.length; i++) {
			pick=pickService.load(ids[i]);
			if(REPEAL.equals(pick.getState())){
				addActionError("已撤销的无法再撤销");
				return ERROR;
			}
			if(CONFIRMED.equals(pick.getState())){
				addActionError("已确认的不需要再次确认");
				return ERROR;
			}
		 admin=adminService.getLoginAdmin();
		 Pick persistent=pickService.load(id);
		 BeanUtils.copyProperties(pick, persistent, new String[] { "id","createUser"});
		 persistent.setState("2");
		 persistent.setConfirmUser(admin);
		 pickService.save(persistent);
		 pkList=pickDetailService.getPickDetail(id);
		 try {
			pickRfc=pickRfcImple.MaterialDocumentCrt(persistent, pkList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CustomerException e) {
			System.out.println(e.getMsgDes());
			e.printStackTrace();
		}
		 for (int j = 0; j < pkList.size(); j++) {
			 PickDetail p=pkList.get(i);
			 p.setMblnr(pickRfc);
			 this.pickDetailService.save(p);
		}
		 redirectionUrl="pick!list.action?workingBillId="
				 +pick.getWorkingbill().getId();
		}
		return SUCCESS;
	}
	
	//刷卡撤销
		public String repeal(){
			ids= id.split(",");
			for (int i = 0; i < ids.length; i++) {
				pick=pickService.load(ids[i]);
				if(REPEAL.equals(pick.getState())){
					addActionError("已撤销的无法再确认");
					return ERROR;
				}
			 admin=adminService.getLoginAdmin();
			 Pick persistent=pickService.load(id);
			 BeanUtils.copyProperties(pick, persistent, new String[] { "id","createUser"});
			 persistent.setState("3");
			 persistent.setConfirmUser(admin);
			 pickService.save(persistent);
			 
			 redirectionUrl="pick!list.action?workingBillId="
					 +pick.getWorkingbill().getId();
			}
			return SUCCESS;
		}

	
	

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}


	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}


	public String getWorkingBillId() {
		return workingBillId;
	}


	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}


	public WorkingBill getWorkingbill() {
		return workingbill;
	}


	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}


	public Pick getPick() {
		return pick;
	}


	public void setPick(Pick pick) {
		this.pick = pick;
	}


	public PickService getPickService() {
		return pickService;
	}


	public void setPickService(PickService pickService) {
		this.pickService = pickService;
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


	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
	}


	public PickRfcImpl getPickRfcImple() {
		return pickRfcImple;
	}


	public void setPickRfcImple(PickRfcImpl pickRfcImple) {
		this.pickRfcImple = pickRfcImple;
	}


	public String getMatnr() {
		return matnr;
	}


	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}


	public List<PickDetail> getPkList() {
		return pkList;
	}


	public void setPkList(List<PickDetail> pkList) {
		this.pkList = pkList;
	}


	public String getPickRfc() {
		return pickRfc;
	}


	public void setPickRfc(String pickRfc) {
		this.pickRfc = pickRfc;
	}



	
	
}
