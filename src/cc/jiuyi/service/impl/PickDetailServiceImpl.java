package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDao;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;

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
	private PickDao pickDao;
	
	
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
//	@Resource
//	private PickDetailService pickDetailService;
//	@Resource
//	private PickRfcImpl pickRfcImpl;
	
	
	private String pickRfc;
	private List<PickDetail> pkList;
	
	
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

	
	public String save(List<PickDetail> pickDetailList,Pick pick) {
		String pk = pickService.save(pick);
		pick.setId(pk);
		for(PickDetail pickDetail: pickDetailList){
			pickDetail.setPick(pick);//建关系
			pickDetailDao.save(pickDetail);
		}
		return pk;
	}
	

	@Override
	public List<PickDetail> getPickDetail(String id) {
		// TODO Auto-generated method stub
		return pickDetailDao.getPickDetail(id);
	}

	@Override
	public void updateAll(Pick pick, List<PickDetail> pickDetail,String cardNumber,String info) {
		Admin admin = this.adminService.getByCardnum(cardNumber);
		Pick pickNew = this.pickDao.get(pick.getId());//获取主表对象
		pickNew.setModifyDate(new Date());
		pickNew.setModifyUser(admin);
		this.pickDao.update(pickNew);
		

		for (int i = 0; i < pickDetail.size(); i++) {
			PickDetail pickDetailOld =pickDetail.get(i);
			if(pickDetailOld.getId()!=null&&!"".equals(pickDetailOld.getId())){
				PickDetail pickDetailNow =this.pickDetailDao.get(pickDetailOld.getId());
				if(pickDetailOld.getPickAmount()!=null&&!"".equals(pickDetailOld.getPickAmount())){
					pickDetailNow.setPickAmount(pickDetailOld.getPickAmount());
					pickDetailNow.setPickType(pickDetailOld.getPickType());
					pickDetailNow.setModifyDate(new Date());
					this.pickDetailDao.update(pickDetailNow);
				}
				else{
					this.pickDetailDao.delete(pickDetailNow.getId());
				}
			}
			else{
				if(pickDetailOld.getPickAmount()!=null&&!"".equals(pickDetailOld.getPickAmount())){
					pickDetailOld.setPickType(info);
					pickDetailOld.setPick(pickNew);
					this.pickDetailDao.save(pickDetailOld);
				}				
			}
		}
    }
}
