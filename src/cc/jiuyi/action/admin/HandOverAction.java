package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;





import org.apache.struts2.convention.annotation.ParentPackage;





import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;

/**
 * 后台Action类 - 交接主表
 */
@ParentPackage("admin")
public class HandOverAction extends BaseAdminAction {

	private List<WorkingBill> workingbillList;
	private String cardnumber;
	private String loginid;//当前登录人的ID
	private String[] workingBillIds;//零头数交接Id
	private String[] actualMounts;//零头数交接数量
	
	@Resource
	private AdminService adminservice;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private HandOverProcessService handOverProcessService;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	@Resource
	private KaoqinService kaoqinservice;
	@Resource
	private HandOverService handOverService;
	@Resource
	private OddHandOverService oddHandOverService;
	
	/**
	 * 刷卡提交
	 * @return
	 */
	public String creditsubmit(){
		Admin admin = adminservice.getByCardnum(cardnumber);//获取当前登录身份
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份当班的所有随工单
		Object[] workingbillIdList = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			workingbillIdList[i] = workingbill.getId();
		}
		
		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//根据随工单获取所有的工序交接记录
		for(HandOverProcess handoverprocess : handoverprocessList){
			String state = handoverprocess.getState();
			if(!state.equals("approval")){//如果状态 不等于 审批完成
				return ajaxJsonErrorMessage("对不起，有工序未交接完成!");
			}
		}
		
		List<OddHandOver> oddHandOverList = oddHandOverService.getList("workingBill.id", workingbillIdList);
		for(OddHandOver oddHandOver : oddHandOverList){
			String state = oddHandOver.getState();
			if(!state.equals("2")){//如果状态 不等于 审批完成
				return ajaxJsonErrorMessage("对不起，有零头交接未交接完成!");
			}
		}
		
		/**主表修改状态和修改人**/
		handOverService.saveandgx(admin,handoverprocessList,oddHandOverList);
		return ajaxJsonSuccessMessage("您的操作已成功");
	}
	
	/**
	 * 刷卡确认
	 * @return
	 */
	public String creditapproval(){
		Admin admin = adminservice.getByCardnum(cardnumber);//获取当前登录身份
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份当班的所有随工单
		Object[] workingbillIdList = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			workingbillIdList[i] = workingbill.getId();
		}
		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//根据随工单获取所有的工序交接记录
		if(handoverprocessList == null){
			return ajaxJsonErrorMessage("未找到任何交接记录");
		}
		List<OddHandOver> oddHandOverList = oddHandOverService.getList("workingBill.id", workingbillIdList);
		try {
			String mblnr="";
			String handoverId="";
			List<String> aufnrList = new ArrayList<String>();
			for(int i=0;i<handoverprocessList.size();i++){//取出所有的不同aufnr 的集合
				HandOverProcess handoverprocess = handoverprocessList.get(i);
				String aufnr = handoverprocess.getBeforworkingbill().getAufnr();//生产订单号
				HandOver handover = handoverprocess.getHandover();
				String state = handover.getState();
				if(!state.equals("2")){
					return ajaxJsonErrorMessage("对不起,交接状态未处于提交状态，无法确认");
				}
				handoverId = handover.getId();
				if(!aufnrList.contains(aufnr)){
					aufnrList.add(aufnr);
				}
			}
			
			for(OddHandOver oddHandOver : oddHandOverList){
				String state = oddHandOver.getState();
				if(!state.equals("2")){//如果状态 不等于 审批完成
					return ajaxJsonErrorMessage("对不起,交接状态未处于确认状态，无法确认!");
				}
				String aufnr = oddHandOver.getWorkingBill().getAufnr();//生产订单号
				if(!aufnrList.contains(aufnr)){
					HandOver handover = oddHandOver.getHandOver();
					handoverId = handover.getId();
					aufnrList.add(aufnr);
				}
			}
			
			List<HandOverProcess> handoverprocessList01 = null;
			for(int i=0;i<aufnrList.size();i++){
				handoverprocessList01 = new ArrayList<HandOverProcess>();
				String aufnr = aufnrList.get(i);
				for(int y=0;y<handoverprocessList.size();y++){
					HandOverProcess handoverprocess = handoverprocessList.get(y);
					if(aufnr.equals(handoverprocess.getBeforworkingbill().getAufnr())){
						if(handoverprocess.getMblnr()!=null){
							continue;
						}
						handoverprocessList01.add(handoverprocess);
					}
				}
				for(int y=0;y<oddHandOverList.size();y++){
					OddHandOver oddHandOver = oddHandOverList.get(y);
					if(aufnr.equals(oddHandOver.getWorkingBill().getAufnr())){
						HandOverProcess handoverprocess = new HandOverProcess();
						handoverprocess.setMaterialCode(oddHandOver.getMaterialCode()); //物料编码
						handoverprocess.setActualAmount(oddHandOver.getActualBomMount());//实际零头数交接数量
						handoverprocess.setAfterworkingbill(workingbillservice.getCodeNext(oddHandOver.getWorkingBill().getWorkingBillCode()));//下班随工单
						handoverprocess.setBeforworkingbill(oddHandOver.getWorkingBill());//上班随工单
						handoverprocess.setId(oddHandOver.getId());//id
						handoverprocessList01.add(handoverprocess);
					}
				}
				if(handoverprocessList01.size()<=0)
					continue;
				if(!mblnr.equals(""))
					mblnr+=",";
				String mblnr1 = handoverprocessrfc.BatchHandOver(handoverprocessList01, "",loginid);//执行
				mblnr+=mblnr1;
				handOverService.updateHand(handoverprocessList01,mblnr1,handoverId,admin);
			}
			//以上跟SAP接口完全没有问题，成功后
			
			kaoqinservice.updateHandOver(handoverId, mblnr, admin);
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题");
		}
		
		
		
		return ajaxJsonSuccessMessage("您的操作已成功");
	}

	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}

	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String[] getWorkingBillIds() {
		return workingBillIds;
	}

	public void setWorkingBillIds(String[] workingBillIds) {
		this.workingBillIds = workingBillIds;
	}

	public String[] getActualMounts() {
		return actualMounts;
	}

	public void setActualMounts(String[] actualMounts) {
		this.actualMounts = actualMounts;
	}
	
	
}
