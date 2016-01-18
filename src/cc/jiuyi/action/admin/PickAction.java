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

	private static final String UNCHECK="1";//未确认
	private static final String CONFIRMED = "2";//已确认
	private static final String REPEAL = "3";//已撤销

	private Pick pick;
	// 获取所有状态
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
	private List<Pick> pickRfc;
	private String cardnumber;//卡号

	// 添加
	public String add() {
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill = workingBillService.get(workingBillId);
		return LIST;
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

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		pager = pickService.getPickPager(workingBillId,pager, map);
		List<Pick> pickList = pager.getList();
		List<Pick> lst = new ArrayList<Pick>();
		for (int i = 0; i < pickList.size(); i++) {
			Pick pick = (Pick) pickList.get(i);
			pick.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
					"pickState", pick.getState()));
			if (pick.getConfirmUser() != null) {
				pick.setXconfirmUser(pick.getConfirmUser().getName());
			}
			if (pick.getCreateUser() != null) {
				pick.setXcreateUser(pick.getCreateUser().getName());
			}
			lst.add(pick);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Pick.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		pickService.updateisdel(ids, "Y");
		for (String id : ids) {
			Pick pick = pickService.load(id);
		}
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		pick = pickService.load(id);
		return INPUT;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		Pick persistent = pickService.load(id);
		BeanUtils.copyProperties(pick, persistent, new String[] { "id",
				"createDate", "modifyDate" });
		pickService.update(persistent);
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		pickService.save(pick);
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 刷卡确认
	public String creditapproval() {
		ids = id.split(",");
		String message = "";
		String pickId = "";
		List<Pick> list = pickService.get(ids);
		admin = adminService.getByCardnum(cardnumber);
		List<PickDetail> pickdetailList = new ArrayList<PickDetail>();
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			pickId = pick.getId();
			if (REPEAL.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
			if (CONFIRMED.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已确认的不需要再次确认!");
			}
			pkList = pickDetailService.getPickDetail(pickId);
			for (int j = 0; j < pkList.size(); j++) {
				PickDetail pickDetail = pkList.get(j);
				pickDetail.setXh(pickId);
				pickdetailList.add(pickDetail);
			}
		}
		try {
			Boolean flag = true;
			pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", list,
					pickdetailList);
			for (Pick pick2 : pickRfc) {
				String e_type = pick2.getE_type();
				if (e_type.equals("E")) { // 如果有一行发生了错误
					flag = false;
					message += pick2.getE_message();
				}
			}
			if (!flag)
				return ajaxJsonErrorMessage(message);
			else {
				flag = true;
				pickRfc = pickRfcImple.BatchMaterialDocumentCrt("", list,
						pickdetailList);
				for (Pick pick2 : pickRfc) {
					String e_type = pick2.getE_type();
					String e_message = pick2.getE_message();
					String ex_mblnr = pick2.getEx_mblnr();
					// String move_type = pick2.getMove_type();
					if (e_type.equals("E")) { // 如果有一行发生了错误
						flag = false;
						message += pick2.getE_message();
					} else {
						Pick pickReturn = pickService.get(pick2.getId());
						pickReturn.setE_message(e_message);
						pickReturn.setEx_mblnr(ex_mblnr);
						pickReturn.setE_type(e_type);
						// pickReturn.setMove_type(move_type);
						pickReturn.setState(CONFIRMED);
						pickReturn.setConfirmUser(admin);
						pickService.update(pickReturn);	
						
						List<PickDetail> pickDetailList = new ArrayList<PickDetail>(pickReturn.getPickDetail());						
						HashMap<String, Object> map = new HashMap<String, Object>();
						pickDetailService.updateWorkingInoutCalculate(pickDetailList , map);//往投入产出表中写数据
					}
				}
				if (!flag)
					return ajaxJsonErrorMessage(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡撤销
	public String creditundo() {
		String str="";
		admin = adminService.getByCardnum(cardnumber);
		ids = id.split(",");
		String message = "";
		String pickId="";
		List<Pick> list = pickService.get(ids);
		List<PickDetail> pickdetailList=new ArrayList<PickDetail>();
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			if(str.equals("")){
				str=pick.getState();
			}else if(!pick.getState().equals(str)){
				return ajaxJsonErrorMessage("请选择同一状态的记录进行撤销!"); 
			}
		}	
		
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			pickId=pick.getId();
			//已经撤销的不用再撤销
			if (REPEAL.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
			//未确认的不调用接口
			if(UNCHECK.equals(pick.getState())){
				str="1";
			}	
			//已确认的调用SAP接口
			if(CONFIRMED.equals(pick.getState())){
				str="2";
           }
		}

		if (str.equals("1")) {
			for (int i = 0; i < list.size(); i++) {
				Pick pick = list.get(i);
				pick.setState(REPEAL);
				pick.setConfirmUser(admin);
				pickService.update(pick);				
			}
			return ajaxJsonSuccessMessage("您的操作已成功!");
		}
		
		List<Pick> listsap = new ArrayList<Pick>();
		if (str.equals("2")) {
			for (int i = 0; i < list.size(); i++) {
				Pick pick = list.get(i);
				if (!"262".equals(pick.getMove_type())) {
					pick.setMove_type("262");
				} else {
					pick.setMove_type("261");
				}

				listsap.add(pick);

				pkList = pickDetailService.getPickDetail(pick.getId());
				List<Pick> list1 = pickService.get(ids);
				for (int j = 0; j < pkList.size(); j++) {
					PickDetail pickDetail = pkList.get(j);
					pickDetail.setXh(pick.getId());
					pickdetailList.add(pickDetail);
				}
			}
		}
			try {
				Boolean flag = true;
				pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", listsap,
						pickdetailList);
				for (Pick pick2 : pickRfc) {
					String e_type = pick2.getE_type();
					if (e_type.equals("E")) { // 如果有一行发生了错误
						flag = false;
						message += pick2.getE_message();
					}
				}
				if (!flag)
					return ajaxJsonErrorMessage(message);
				else {
					flag = true;
					pickRfc = pickRfcImple.BatchMaterialDocumentCrt("", listsap,pickdetailList);
					for (Pick pick2 : pickRfc) {
						String e_type = pick2.getE_type();
						String e_message = pick2.getE_message();
						String ex_mblnr = pick2.getEx_mblnr();
						//String move_type = pick2.getMove_type();
						if (e_type.equals("E")) { // 如果有一行发生了错误
							flag = false;
							message += pick2.getE_message();
						} else {
							Pick pickReturn = pickService.get(pick2.getId());
							pickReturn.setE_message(e_message);
							pickReturn.setEx_mblnr(ex_mblnr);
							pickReturn.setE_type(e_type);
							//pickReturn.setMove_type(move_type);
							pickReturn.setState(REPEAL);
							pickReturn.setConfirmUser(admin);
							pickService.update(pickReturn);
							
							List<PickDetail> pickDetailList = new ArrayList<PickDetail>(pickReturn.getPickDetail());
							pickDetailService.updateWorkingInoutCalculateBack(pickDetailList);//往投入产出表中写数据
						}
					}
					if (!flag)
						return ajaxJsonErrorMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
			} catch (Exception e) {
				e.printStackTrace();
				return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
			}
		return ajaxJsonSuccessMessage("您的操作已成功!");
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

	// 获取所有状态
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

	public List<Pick> getPickRfc() {
		return pickRfc;
	}

	public void setPickRfc(List<Pick> pickRfc) {
		this.pickRfc = pickRfc;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	
	
}
