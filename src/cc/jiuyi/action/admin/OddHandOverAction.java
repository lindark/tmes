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

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.WorkingBillService;

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
	
	//刷卡提交
	public String creditSubmit(){
		//获取当前登录人信息
		//Admin admin = adminService.getLoginAdmin();
		//admin = adminService.get(admin.getId());
		
		//获取维护物料信息
		List<Material> materialList = materialService.getAll();
			if(materialList!=null && materialList.size()>0){
						for(int i=0;i<actualMounts.length;i++){
							if((actualMounts[i]!=null && !"".equals(actualMounts[i])) || (unMounts[i]!=null && !"".equals(unMounts[i]))){
								//获取随工单 
								WorkingBill wb = workingBillService.get(workingBillIds[i]);
								
								//若存在先删除
								Set<OddHandOver> ohoSet = wb.getOddHandOverSet();
								if(ohoSet!=null && ohoSet.size()>0){
									for(OddHandOver oho : ohoSet){
										oddHandOverService.delete(oho);
									}
								}
								
								//获取Bom
								String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
								List<Bom> bomList = bomService.findBom(aufnr, wb.getProductDate());
								if(bomList == null || bomList.size()<=0){
									//addActionError("未找到一条BOM信息");
									return ajaxJsonErrorMessage("未找到一条BOM信息");
								}
								
								//删除Bom中未维护物料
								List<Bom> bmls = new ArrayList<Bom>();
								for(Bom bm : bomList){
									for(Material mt : materialList){
										if(bm.getMaterialCode().equals(mt.getMaterialCode()) && wb.getWerks().equals(mt.getFactory().getFactoryCode())){
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
											Double mount = actualMounts[i] * (bm.getMaterialAmount() / bm.getProductAmount());
											BigDecimal   b   =   new   BigDecimal(mount);  
											mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
											oho.setActualHOMount(mount);
										}
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
										if(unMounts[i]!=null && !"".equals(unMounts[i])){
											Double mount = unMounts[i] * (bm.getMaterialAmount() / bm.getProductAmount());
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
	public String crediTapproval(){
		if(actualMounts!=null && actualMounts.length>0){
			for(int i=0;i<actualMounts.length;i++){
				if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
					//获取随工单 
					WorkingBill wb = workingBillService.get(workingBillIds[i]);
					
					//获取零头数交接
					Set<OddHandOver> ohoSet = wb.getOddHandOverSet();
					if(ohoSet!=null && ohoSet.size()>0){
						for(OddHandOver oho : ohoSet){
							//获取确认人
							admin = adminService.getByCardnum(cardnumber);
							oho.setSureCode(admin.getCardNumber());
							oho.setSureName(admin.getName());
							oho.setState("2");
							oddHandOverService.update(oho);
						}
					}
				}
			}
		}
		return ajaxJsonSuccessMessage("您的操作已成功");
	}
	
	
	public String findAfterWorkingCode(){
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		if(workingCode!=null && workingCode.length>0){
			for(int i=0;i<workingCode.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				WorkingBill nextWorkingbill = workingBillService.getCodeNext(workingCode[i],nowDate,shift);//下一随工单
				if(nextWorkingbill == null){
					return ajaxJsonErrorMessage("无下一班随工单");
				}
				map.put("afterCode", nextWorkingbill.getWorkingBillCode());
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return ajaxJson(jsonArray.toString());
		}
		return ajaxJsonErrorMessage("操作成功");
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
	


}
