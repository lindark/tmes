package cc.jiuyi.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PumPackHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PumPackHandOverService;
import cc.jiuyi.service.WorkingBillService;

/**
 * 后台Action类 - 抽包异常交接
 */

@ParentPackage("admin")
public class PumPackHandOverAction extends BaseAdminAction {

	private static final long serialVersionUID = -4276260700673985157L;
	
	private String[] workingBillIds;
	private Double[] actualMounts;
	private String cardnumber;//刷卡卡号
	private Admin admin;
	
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService materialService;
	@Resource
	private PumPackHandOverService pumPackHandOverService;
	
	//刷卡提交
	public String creditSubmit(){
		//获取当前登录人信息
		//Admin admin = adminService.getLoginAdmin();
		//admin = adminService.get(admin.getId());
		
		//获取维护物料信息
		List<Material> materialList = materialService.getAll();
		if(materialList != null && materialList.size()>0){
			if(actualMounts!=null && actualMounts.length>0){
				for(int i=0;i<actualMounts.length;i++){
					if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
						//获取随工单 
						WorkingBill wb = workingBillService.get(workingBillIds[i]);
						
						//若存在先删除
						Set<PumPackHandOver> pphoSet = wb.getPumPackHandOverSet();
						if(pphoSet!=null && pphoSet.size()>0){
							for(PumPackHandOver oho : pphoSet){
								pumPackHandOverService.delete(oho);
							}
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
								if(bm.getMaterialCode().equals(mt.getMaterialCode()) && wb.getWerks().equals(mt.getFactory().getFactoryCode())){
									bmls.add(bm);
									break;
								}
							}
						}
						 
						//通过交接数量，计算bom实际数量
						if(bmls.size()>0){
							Set<PumPackHandOver> pphoSets = new HashSet<PumPackHandOver>();
							for(Bom bm : bmls){
								PumPackHandOver  ppho = new PumPackHandOver();
								Double mount = actualMounts[i] * (bm.getMaterialAmount() / bm.getProductAmount());
								BigDecimal   b   =   new   BigDecimal(mount);  
								mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
								//获取提交人
								if(admin == null){
									admin = new Admin();
								}
								admin = adminService.getByCardnum(cardnumber);
								ppho.setSubmitCode(admin.getCardNumber());
								ppho.setSureName(admin.getName());
								ppho.setWorkingBill(wb);
								ppho.setActualHOMount(actualMounts[i]);
								ppho.setActualBomMount(mount);
								ppho.setState("1");
								ppho.setMaterialCode(bm.getMaterialCode());
								pumPackHandOverService.save(ppho);
								pphoSets.add(ppho);
							}
							wb.setPumPackHandOverSet(pphoSets);
							workingBillService.update(wb);
						}else{
							//addActionError("没有找到可交接记录");
							return ajaxJsonErrorMessage("没有找到可交接记录");
						}
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
					Set<PumPackHandOver> pphoSets = wb.getPumPackHandOverSet();
					if(pphoSets!=null && pphoSets.size()>0){
						for(PumPackHandOver ppho : pphoSets){
							//获取确认人
							admin = adminService.getByCardnum(cardnumber);
							ppho.setSubmitCode(admin.getCardNumber());
							ppho.setSureName(admin.getName());
							ppho.setState("2");
							pumPackHandOverService.update(ppho);
						}
					}
				}
			}
		}
		return ajaxJsonSuccessMessage("您的操作已成功");
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

}
