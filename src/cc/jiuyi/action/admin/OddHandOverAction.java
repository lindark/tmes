package cc.jiuyi.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 后台管理、管理员 
 */

@ParentPackage("admin")
public class OddHandOverAction extends BaseAdminAction {
	
	private static final long serialVersionUID = -4732324994891087871L;
	
	
	
	private String[] workingBillIds;
	private Double[] actualMounts;
	private Double[] unMounts;
	private String cardnumber;//刷卡卡号
	private Admin admin;
	private String[] workingCode;
	private String nowDate;
	private String shift;
	private String loginid;
	
	private String workingBillCode;
	private String materialCode;
	private String end;
	private String start;
	private String state;
	
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService materialService;
	@Resource
	private OddHandOverService oddHandOverService;
	@Resource
	private DictService dictService;

	// 零头数记录表 @author Reece 2016/3/15
	public String history() {
		return "history";
	}
		
	// 零头数记录表 @author Reece 2016/3/15
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		try {
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("materialCode") != null) {
					String materialCode = obj.getString("materialCode").toString();
					map.put("materialCode", materialCode);
				}
				if (obj.get("workingBillCode") != null) {
					String workingBillCode = obj.getString("workingBillCode")
							.toString();
					map.put("workingBillCode", workingBillCode);
				}
				if (obj.get("start") != null && obj.get("end") != null) {
					String start = obj.get("start").toString();
					String end = obj.get("end").toString();
					map.put("start", start);
					map.put("end", end);
				}
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
			}
			pager = oddHandOverService.historyjqGrid(pager, map);
			List<OddHandOver> oddList = pager.getList();
			List<OddHandOver> lst = new ArrayList<OddHandOver>();
			for (int i = 0; i < oddList.size(); i++) {
				OddHandOver oddHandOver = oddList.get(i);
				oddHandOver.setWorkingBillCode(oddHandOver.getWorkingBill().getWorkingBillCode());
				oddHandOver.setMaktx(oddHandOver.getWorkingBill().getMaktx());
				oddHandOver.setProductDate(oddHandOver.getWorkingBill().getProductDate());
				oddHandOver.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "oddStatus", oddHandOver.getState()));
				oddHandOver.setMatnr(oddHandOver.getWorkingBill().getMatnr());
				lst.add(oddHandOver);
			}
			pager.setList(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(OddHandOver.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	// Excel导出 @author Reece 2016/3/15
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("workingBillCode", workingBillCode);
		map.put("materialCode", materialCode);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);

		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("交接随工单号");
		header.add("产品编码");
		header.add("产品名称");
		header.add("下班随工单号");
		header.add("生产日期");
		header.add("组件编码");
		header.add("组件名称");
		
		header.add("实际零头数交接数量");
		header.add("实际异常交接数量");
		header.add("实际物料数量");
		header.add("实际异常物料数量");
		
		header.add("物料凭证号");
		header.add("交接日期");
		header.add("提交人");
		header.add("确认人");
		header.add("状态");

		List<Object[]> workList = oddHandOverService.historyExcelExport(map);
		for (int i = 0; i < workList.size(); i++) {
			Object[] obj = workList.get(i);
			OddHandOver oddHandOver = (OddHandOver) obj[0];//oddHandOver
        	WorkingBill workingbill = (WorkingBill)obj[1];//workingbill
        	
        	
			Object[] bodyval = {
					workingbill.getWorkingBillCode(),
					workingbill.getMatnr(),
					workingbill.getMaktx(),
					oddHandOver.getAfterWorkingCode(),
					workingbill.getProductDate(),
					oddHandOver.getMaterialCode(),
					oddHandOver.getMaterialDesp(),
				
					oddHandOver.getActualHOMount()==null?0:oddHandOver.getActualHOMount(),
					oddHandOver.getUnHOMount()==null?0:oddHandOver.getUnHOMount(),
					oddHandOver.getActualBomMount()==null?0:oddHandOver.getActualBomMount(),
					oddHandOver.getUnBomMount()==null?0:oddHandOver.getUnBomMount(),
					
					
					oddHandOver.getMblnr(),
					oddHandOver.getCreateDate(),
					oddHandOver.getSubmitName(),
					oddHandOver.getSureName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"oddStatus", oddHandOver.getState())};
			body.add(bodyval);
		}

		try {
			String fileName = "零头交接记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("零头交接记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
		
		
		/**=====================end  method================================*/
		
		/**=====================get/set  start==============================*/
		
	
	
	
	//刷卡提交
	public String creditsubmit(){
		//获取当前登录人信息
		//Admin admin = adminService.getLoginAdmin();
		//admin = adminService.get(admin.getId());
	/*	if(workingBillIds!=null && workingBillIds.length>0){
			for(int i=0;i<workingBillIds.length;i++){
				//获取随工单 
				WorkingBill wb = workingBillService.get(workingBillIds[i]);
				List<OddHandOver> ohoSets = new ArrayList<OddHandOver>(wb.getOddHandOverSet());
				if(ohoSets!=null && ohoSets.size()>0){
					for(OddHandOver oho : ohoSets){
						if("2".equals(oho.getState())){
							return ajaxJsonErrorMessage("当前班次已确认交接，无法再次交接");
						}
					}
				}
			}
		}*/
		Admin admin = adminService.get(loginid);
		
		//获取维护物料信息
		List<Material> materialList = materialService.getAll();
			if(materialList!=null && materialList.size()>0){
						for(int i=0;i<actualMounts.length;i++){
							if((actualMounts[i]!=null && !"".equals(actualMounts[i])) || (unMounts[i]!=null && !"".equals(unMounts[i]))){
								
								//获取随工单 
								WorkingBill wb = workingBillService.get(workingBillIds[i]);
								//WorkingBill wbnext = workingBillService.getCodeNext(wb.getWorkingBillCode(),nowDate,shift);
								WorkingBill wbnext = workingBillService.getCodeNext(admin,wb.getWorkingBillCode(),nowDate,shift);
								if(wbnext==null){
									return ajaxJsonErrorMessage("未找到下班随工单");
								}
								
								//若存在先删除
								try {
									List<OddHandOver> ohoSets = new ArrayList<OddHandOver>(wb.getOddHandOverSet());
									if(ohoSets!=null && ohoSets.size()>0){
										for(OddHandOver oho : ohoSets){
											oddHandOverService.delete(oho);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									return ajaxJsonErrorMessage("系统异常，重新进入");
								}
								
								
								//获取Bom
								String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
								List<Bom> bomList = bomService.findBom(aufnr, wb.getProductDate(),wb.getWorkingBillCode());
								if(bomList == null || bomList.size()<=0){
									//addActionError("未找到一条BOM信息");
									return ajaxJsonErrorMessage("未找到一条BOM信息");
								}
								
								//删除Bom中未维护物料
								List<Bom> bmls = new ArrayList<Bom>();
								for(Bom bm : bomList){
									for(Material mt : materialList){
										if(bm.getMaterialCode().equals(mt.getMaterialCode()) && wb.getWerks().equals(mt.getFactoryunit().getWorkShop().getFactory().getFactoryCode())){
											bmls.add(bm);
											break;
										}
									}
								}
								 
								//通过交接数量，计算bom实际数量
								if(bmls.size()>0){
									Set<OddHandOver> ohoSets = new HashSet<OddHandOver>();
									for(Bom bm : bmls){
										OddHandOver oho = new OddHandOver();
										if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
											Double mount = actualMounts[i] * (bm.getMaterialAmount() / wb.getPlanCount());
											BigDecimal   b   =   new   BigDecimal(mount);  
											mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
											oho.setActualHOMount(mount);
										}
										oho.setBeforeWokingCode(wb.getWorkingBillCode());
										
										oho.setAfterWorkingCode(wbnext.getWorkingBillCode());//下班随工单
										//获取提交人
										if(admin == null){
											admin = new Admin();
										}
										admin = adminService.getByCardnum(cardnumber);
										oho.setSubmitCode(admin.getCardNumber());
										oho.setSubmitName(admin.getName());
										oho.setWorkingBill(wb);
										oho.setActualBomMount(actualMounts[i]);
										oho.setUnBomMount(unMounts[i]);
										oho.setState("1");
										oho.setMaterialCode(bm.getMaterialCode());
										oho.setMaterialDesp(bm.getMaterialName());
										if(unMounts[i]!=null && !"".equals(unMounts[i])){
											Double mount = unMounts[i] * (bm.getMaterialAmount() / wb.getPlanCount());
											BigDecimal   b   =   new   BigDecimal(mount);  
											mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
											oho.setUnHOMount(mount);
										}
										oddHandOverService.save(oho);
										ohoSets.add(oho);
									}
									wb.setOddHandOverSet(ohoSets);
									workingBillService.update(wb);
								}else{
									//addActionError("没有找到可交接记录");
									return ajaxJsonErrorMessage("没有找到可交接记录");
								}
							}
						}
		}else{
			//addActionError("未维护物料信息");
			//return ERROR;
			return ajaxJsonErrorMessage("未维护物料信息");
			
		}
		return ajaxJsonSuccessMessage("您的操作已成功");
	}
	
	//刷卡确认
	public String creditapproval(){
		Set<OddHandOver> ohoSet = new HashSet<OddHandOver>();
		boolean f = false;
		if(actualMounts!=null && actualMounts.length>0){
			for(int i=0;i<actualMounts.length;i++){
				if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
					//获取随工单 
					WorkingBill wb = workingBillService.get(workingBillIds[i]);
					try {
						//获取零头数交接
						ohoSet  = wb.getOddHandOverSet();
						if(ohoSet!=null && ohoSet.size()>0){
							for(OddHandOver oho : ohoSet){
								//获取确认人
								admin = adminService.getByCardnum(cardnumber);
								oho.setSureCode(admin.getCardNumber());
								oho.setSureName(admin.getName());
								oho.setState("2");
								oddHandOverService.update(oho);
							}
							f = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						return ajaxJsonErrorMessage("系统异常，重新进入");
					}
					
				}
			}
			if(f){
				return ajaxJsonSuccessMessage("您的操作已成功");
			}else{
				return ajaxJsonErrorMessage("无可交接数据,不可确认");
			}
		}else{
			return ajaxJsonErrorMessage("无可交接数据,不可确认");
		}
		
	}
	
	
	public String findAfterWorkingCode(){
		Admin admin = adminService.get(loginid);
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		if(workingCode!=null && workingCode.length>0){
			for(int i=0;i<workingCode.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				WorkingBill nextWorkingbill = workingBillService.getCodeNext(admin,workingCode[i],nowDate,shift);//下一随工单
				if(nextWorkingbill == null){
					map.put("afterCode","");
				}else{
					map.put("afterCode", nextWorkingbill.getWorkingBillCode());
				}
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return ajaxJson(jsonArray.toString());
		}else{
			return ajaxJsonErrorMessage("没有上班随工单");
		}
		
	}
	
	
	public Double[] getActualMounts() {
		return actualMounts;
	}

	public void setActualMounts(Double[] actualMounts) {
		this.actualMounts = actualMounts;
	}

	public String[] getWorkingBillIds() {
		return workingBillIds;
	}

	public void setWorkingBillIds(String[] workingBillIds) {
		this.workingBillIds = workingBillIds;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Double[] getUnMounts() {
		return unMounts;
	}

	public void setUnMounts(Double[] unMounts) {
		this.unMounts = unMounts;
	}

	public String[] getWorkingCode() {
		return workingCode;
	}

	public void setWorkingCode(String[] workingCode) {
		this.workingCode = workingCode;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	


}
