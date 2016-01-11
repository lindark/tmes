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
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.WorkingBillService;

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
		// TODO Auto-generated method stub
		return handOverProcessDao.getHandOverProcessPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		handOverProcessDao.updateisdel(ids, oper);
		
	}
	

	
	@Override
	public void saveorupdate(List<HandOverProcess> handoverprocessList,String state,String cardNumber) {
		Admin admin = adminservice.get("cardNumber", cardNumber);
//		HandOver handOver = new HandOver();
//		handOver.setState("1");
//		handOver.setSubmitadmin(admin);
		
		
		//String hanoverId=handoverservice.save(handOver);//保存主表
		//handOver.setId(hanoverId);
		for(HandOverProcess handoverprocess : handoverprocessList){
			//Admin admin = adminservice.get("cardNumber", cardNumber);
			if(state.equals("creditsubmit")){//刷卡提交
				handoverprocess.setSubmitadmin(admin);
				handoverprocess.setState("notapproval");
				//handoverprocess.setHandover(handOver);
			}
			if(state.equals("creditapproval")){//刷卡确认
				handoverprocess.setApprovaladmin(admin);
				handoverprocess.setState("approval");
				//handoverprocess.setHandover(handOver);
			}
			if(state.equals("creditsave")){//刷卡保存
				handoverprocess.setSaveadmin(admin);
				handoverprocess.setState("notsubmit");
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
		
	}

	@Override
	public HandOverProcess findhandoverBypro(String materialCode,
			String processid, String matnr,String workingBillId) {
		return handOverProcessDao.findhandoverBypro(materialCode, processid, matnr,workingBillId);
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
		
		this.saveorupdate(handoverprocessList,state,cardNumber);
		return flag+","+"操作成功";
	}
	
	
	
	
}
