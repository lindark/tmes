package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.service.FaultReasonService;

/**
 * Service实现类 -故障原因管理
 *
 */

@Service
public class FaultReasonServiceImpl extends BaseServiceImpl<FaultReason, String>implements FaultReasonService{

	@Resource
	private FaultReasonDao faultReasonDao;
	
	@Resource
	public void setBaseDao(FaultReasonDao faultReasonDao){
		super.setBaseDao(faultReasonDao);
	}

	@Override
	public Pager getFaultReasonPager(Pager pager, HashMap<String, String> map) {
		return faultReasonDao.getFaultReasonPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		faultReasonDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<FaultReason> getAllFaultReason() {
		// TODO Auto-generated method stub
		return faultReasonDao.getAllFaultReason();
	}
}
