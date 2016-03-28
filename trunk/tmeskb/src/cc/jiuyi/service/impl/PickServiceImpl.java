package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -领/退料主表
 * @author Reece
 *
 */

@Service
public class PickServiceImpl extends BaseServiceImpl<Pick, String>implements PickService{

	@Resource
	private PickDao pickDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	
	@Resource
	public void setBaseDao(PickDao pickDao){
		super.setBaseDao(pickDao);
	}
	
	@Override
	public void delete(String id) {
		Pick pick = pickDao.load(id);
		this.delete(pick);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Pick> getPickList() {		
		return pickDao.getPickList();
	}

	@Override
	public Pager getPickPager(String workingBillId,Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return pickDao.getPickPager(workingBillId,pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		pickDao.updateisdel(ids, oper);
		
	}

	@Override
	public void saveConfirm(List<Pick> list, Admin admin, String stu,String mblnr) {
		for (int i = 0; i < list.size(); i++) {
			Pick pick=list.get(i);
			pick.setState(stu);
			pick.setConfirmUser(admin);
			pick.setMblnr(mblnr);
			this.update(pick);
		}
		
	}

	
	public void saveRepeal(List<Pick> list, Admin admin, String stu) {
		for (int i = 0; i < list.size(); i++) {	
			Pick pick=list.get(i);
			pick.setState(stu);
			pick.setConfirmUser(admin);
			this.update(pick);
		}
	}
	
	
	@Override
	public void updateSap(String pk, String ex_mblnr) {
		Pick xpick= pickDao.get(pk);
		xpick.setEx_mblnr(ex_mblnr);
		xpick.setState("2");	
	}

	@Override
	public void updateWorkingInoutCalculate(List<Pick> paramaterList,
			HashMap<String, Object> maps) {
		
		
	}

	@Override
	public List<Pick> getPickByWorkingbillId(String WorkingBillId) {
		// TODO Auto-generated method stub
		return pickDao.getPickByWorkingbillId(WorkingBillId);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return pickDao.historyjqGrid(pager, map);
	}
	
	

}
