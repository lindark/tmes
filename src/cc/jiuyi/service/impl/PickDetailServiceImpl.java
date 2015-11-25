package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -领/退料
 * @author Reece
 *
 */

@Service
public class PickDetailServiceImpl extends BaseServiceImpl<PickDetail, String>implements PickDetailService{

	@Resource
	private PickDetailDao pickDetailDao;
	
	
	@Resource
	public void setBaseDao(PickDetailDao pickDetailDao){
		super.setBaseDao(pickDetailDao);
	}
	@Resource
	private AdminService adminService;
	@Resource
	private PickService pickService;
	@Resource
	private WorkingBillService workingBillService;
	
	
	@Override
	public void delete(String id) {
		PickDetail pickDetail = pickDetailDao.load(id);
		this.delete(pickDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<PickDetail> getPickDetailList() {		
		return pickDetailDao.getPickDetailList();
	}

	@Override
	public Pager getPickDetailPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return pickDetailDao.getPickDetailPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		pickDetailDao.updateisdel(ids, oper);
		
	}

	@Override
	public void save(List<PickDetail> pickDetailList,String workingBillId) {
		Admin admin=adminService.getLoginAdmin();
		WorkingBill workingBill=workingBillService.get(workingBillId);
		String s="";
		Pick pk=new Pick();
		if(pickDetailList.size()>0){
			Pick pick=new Pick();
			pick.setCreateDate(new Date());
			pick.setCreateUser(admin);
			pick.setState("1");
			pick.setWorkingbill(workingBill);
			s=this.pickService.save(pick);
		}
		if(s!=null&&!"".equals(s)){
		  pk=pickService.get(s);
		}
		for(int i=0;i<pickDetailList.size();i++){
			PickDetail p=pickDetailList.get(i);			
			p.setConfirmUser(admin);
		    p.setWorkingbill(workingBill);
			p.setPick(pk);
			if(p.getPickAmount()!=null&&!"".equals(p.getPickAmount())){
			  this.pickDetailDao.save(p);
			}
			
		}
	}

	


	
}
