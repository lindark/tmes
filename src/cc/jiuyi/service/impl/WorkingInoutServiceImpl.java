package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.WorkingInoutService;

/**
 * Service实现类 -投入产出表
 * @author Reece
 *
 */

@Service
public class WorkingInoutServiceImpl extends BaseServiceImpl<WorkingInout, String>implements WorkingInoutService{

	
	@Resource
	private WorkingInoutDao workingInoutDao;
	
	@Resource
	public void setBaseDao(WorkingInoutDao workingInoutDao){
		super.setBaseDao(workingInoutDao);
	}
	
	@Override
	public boolean isExist(String workingBillId, String materialCode) {
		// TODO Auto-generated method stub
		return workingInoutDao.isExist(workingBillId, materialCode);
	}
}
