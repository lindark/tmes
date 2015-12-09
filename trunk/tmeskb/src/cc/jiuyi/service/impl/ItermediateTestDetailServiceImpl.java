package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ItermediateTestDetailDao;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -半成品巡检Service实现类
 * @author Reece
 *
 */

@Service
public class ItermediateTestDetailServiceImpl extends BaseServiceImpl<ItermediateTestDetail, String>implements ItermediateTestDetailService{

	@Resource
	private ItermediateTestDetailDao itermediateTestDetailDao;
	
	
	@Resource
	public void setBaseDao(ItermediateTestDetailDao itermediateTestDetailDao){
		super.setBaseDao(itermediateTestDetailDao);
	}
	@Resource
	private AdminService adminService;
	@Resource
	private ItermediateTestService itermediateTestService;
	@Resource
	private WorkingBillService workingBillService;
	
	
	

	@Override
	public List<ItermediateTestDetail> getItermediateTestDetailList() {		
		return itermediateTestDetailDao.getItermediateTestDetailList();
	}

	@Override
	public Pager getItermediateTestDetailPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return itermediateTestDetailDao.getItermediateTestDetailPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		itermediateTestDetailDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<ItermediateTestDetail> getItermediateTestDetail(String id) {
		// TODO Auto-generated method stub
		return itermediateTestDetailDao.getItermediateTestDetail(id);
	}

	@Override
	public void save(List<ItermediateTestDetail> itermediateTestDetailList,
			String woringBillId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItermediateTestDetail getBySidAndMid(String sid, String mid) {
		
		return itermediateTestDetailDao.getBySidAndMid(sid, mid);
	}


	


	
}
