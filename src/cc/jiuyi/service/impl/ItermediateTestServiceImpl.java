package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ItermediateTestDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -半成品巡检主表Service实现类
 * @author Reece
 *
 */

@Service
public class ItermediateTestServiceImpl extends BaseServiceImpl<ItermediateTest, String>implements ItermediateTestService{

	@Resource
	private ItermediateTestDao itermediateTestDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	
	@Resource
	public void setBaseDao(ItermediateTestDao itermediateTestDao){
		super.setBaseDao(itermediateTestDao);
	}
	
	@Override
	public void delete(String id) {
		ItermediateTest itermediateTest = itermediateTestDao.load(id);
		this.delete(itermediateTest);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<ItermediateTest> getItermediateTestList() {		
		return itermediateTestDao.getItermediateTestList();
	}

	@Override
	public Pager getItermediateTestPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return itermediateTestDao.getItermediateTestPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		itermediateTestDao.updateisdel(ids, oper);
		
	}


	
}
