package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandOverProcessDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 -工序交接
 * @author Reece
 *
 */

@Service
public class HandOverProcessServiceImpl extends BaseServiceImpl<HandOverProcess, String>implements HandOverProcessService{

	@Resource
	private HandOverProcessDao handOverProcessDao;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private AdminService adminservice;
//	@Resource
//	private WorkingInoutService workinginoutservice;
	
	
//	public void setWorkinginoutservice(WorkingInoutService workinginoutservice) {
//		this.workinginoutservice = workinginoutservice;
//	}

	@Resource
	public void setBaseDao(HandOverProcessDao handOverProcessDao){
		super.setBaseDao(handOverProcessDao);
	}
	
	@Override
	public void delete(String id) {
		HandOverProcess handOverProcess = handOverProcessDao.load(id);
		this.delete(handOverProcess);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}
	

	@Override
	public List<HandOverProcess> getHandOverProcessList() {		
		return handOverProcessDao.getHandOverProcessList();
	}

	@Override
	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map) {
		return handOverProcessDao.getHandOverProcessPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		handOverProcessDao.updateisdel(ids, oper);
		
	}
	

	
	@Override
	public Admin saveorupdate(List<HandOverProcess> handoverprocessList,String state,String cardNumber) {
		Admin admin = adminservice.get("cardNumber", cardNumber);
//		HandOver handOver = new HandOver();
//		handOver.setState("1");
//		handOver.setSubmitadmin(admin);
		
		
		//String hanoverId=handoverservice.save(handOver);//保存主表
		//handOver.setId(hanoverId);
		for(HandOverProcess handoverprocess : handoverprocessList){
			HandOverProcess handOverProcess = null;
			try{
			 handOverProcess = this.findhandoverBypro(handoverprocess.getMaterialCode(), handoverprocess.getProcessid(),handoverprocess.getBeforworkingbill().getId());
			}catch(Exception e){
				e.printStackTrace();
			}
			if(handOverProcess != null){
				handoverprocess.setSubmitadmin(handOverProcess.getSubmitadmin());
				handoverprocess.setApprovaladmin(handOverProcess.getApprovaladmin());
				handoverprocess.setSaveadmin(handOverProcess.getSaveadmin());
			}
			
			//Admin admin = adminservice.get("cardNumber", cardNumber);
			if(state.equals("creditsubmit")){//刷卡提交
				handoverprocess.setSubmitadmin(admin);
				handoverprocess.setState("notapproval");
				Double cqsl = handoverprocess.getCqsl();//裁切倍数
				Double cqamount = handoverprocess.getCqamount();//裁切后正常交接数量
				Double cqrepairamount = handoverprocess.getCqrepairAmount();//裁切后返修交接数量
				cqsl = ThinkWayUtil.null2o(cqsl);
				cqamount = ThinkWayUtil.null2o(cqamount);
				cqrepairamount = ThinkWayUtil.null2o(cqrepairamount);
				Double amount = ArithUtil.round(ArithUtil.div(cqamount, cqsl),2);
				Double repairamount = ArithUtil.round(ArithUtil.div(cqrepairamount, cqsl), 2);
				handoverprocess.setAmount(amount);
				handoverprocess.setRepairAmount(repairamount);
				//handoverprocess.setHandover(handOver);
			}
			if(state.equals("creditapproval")){//刷卡确认
				handoverprocess.setApprovaladmin(admin);
				handoverprocess.setState("approval");
				//handoverprocess.setHandover(handOver);
//				boolean flag = workinginoutservice.isExist(handoverprocess.getBeforworkingbill().getId(), handoverprocess.getMaterialCode());
//				if(!flag){//如果不存在，新增  --- 上一随工单信息
//					WorkingInout workinginout = new WorkingInout();
//					workinginout.setWorkingbill(handoverprocess.getBeforworkingbill());
//					workinginout.setMaterialCode(handoverprocess.getMaterialCode());
//					workinginout.setMaterialName(handoverprocess.getMaterialName());
//					workinginoutservice.save(workinginout);
//				}
//				boolean flag1 = workinginoutservice.isExist(handoverprocess.getAfterworkingbill().getId(),handoverprocess.getMaterialCode() );
//				if(!flag1){//如果不存在,新增 --- 下一随工单信息
//					WorkingInout workinginout = new WorkingInout();
//					workinginout.setWorkingbill(handoverprocess.getAfterworkingbill());
//					workinginout.setMaterialCode(handoverprocess.getMaterialCode());
//					workinginout.setMaterialName(handoverprocess.getMaterialName());
//					workinginoutservice.save(workinginout);
//				}
				
			}
			if(state.equals("creditsave")){//刷卡保存
				handoverprocess.setSaveadmin(admin);
				handoverprocess.setState("notsubmit");
				
				Double cqsl = handoverprocess.getCqsl();//裁切倍数
				Double cqamount = handoverprocess.getCqamount();//裁切后正常交接数量
				Double cqrepairamount = handoverprocess.getCqrepairAmount();//裁切后返修交接数量
				Double amount = ArithUtil.round(ArithUtil.div(cqamount, cqsl),2);
				Double repairamount = ArithUtil.round(ArithUtil.div(cqrepairamount, cqsl), 2);
				handoverprocess.setAmount(amount);
				handoverprocess.setRepairAmount(repairamount);
				
			}
			HandOverProcess handover = this.findhandover(handoverprocess.getMaterialCode(), handoverprocess.getProcessid(), handoverprocess.getBeforworkingbill().getId());
			if(handover != null){
				if(handoverprocess.getAmount() <=0){
					handOverProcessDao.delete(handover);
				}else{
					handoverprocess.setId(handover.getId());
					handOverProcessDao.merge(handoverprocess);//对象不属于持久化
				}
			}
			else{
				if(handoverprocess.getAmount() > 0){
					handOverProcessDao.save(handoverprocess);
				}
			}
				
		}
		return admin;
	}

	@Override
	public HandOverProcess findhandoverBypro(String materialCode,
			String processid,String workingBillId) {
		return handOverProcessDao.findhandoverBypro(materialCode, processid,workingBillId);
	}
	
	public List<HandOverProcess> getList(String propertyName, Object[] objlist,String orderBy,String ordertype) {
		return handOverProcessDao.getList(propertyName, objlist, orderBy, ordertype);
	}

	@Override
	public HandOverProcess findhandover(String materialCode, String processid,
			String matnrid) {
		return handOverProcessDao.findhandover(materialCode, processid, matnrid);
	}

	@Override
	public String savehandover(List<HandOverProcess> handoverprocessList,String state,String cardNumber) {
		String message="";
		Integer ishead=0;
		Boolean flag = true;
		for(int i=0;i<handoverprocessList.size();i++){
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			String afterworkingbillCode = handoverprocess.getAfterworkingbill().getWorkingBillCode();
			WorkingBill afterworkingbill = workingbillservice.get("workingBillCode", afterworkingbillCode);
			if(afterworkingbill == null){
				flag = false;
				if(ishead == 0)
					message+="第"+i+"行,未找到下一随工单";
				else
					message+=",第"+i+"行,未找到下一随工单";
			}
			handoverprocess.setAfterworkingbill(afterworkingbill);
			handoverprocessList.set(i, handoverprocess);
		}
		if(!flag)
			return flag+","+message;
		
		Admin admin = this.saveorupdate(handoverprocessList,state,cardNumber);
		return flag+","+"操作成功"+admin.getName();
	}


	
}
