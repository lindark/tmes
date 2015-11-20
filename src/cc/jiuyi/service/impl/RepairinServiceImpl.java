package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 返修收货
 */
@Service
@Transactional
public class RepairinServiceImpl extends BaseServiceImpl<Repairin, String>
		implements RepairinService {
	@Resource
	private RepairinDao repairinDao;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private RepairinService repairinService;

	@Resource
	public void setBaseDao(RepairinDao repairinDao) {
		super.setBaseDao(repairinDao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairinDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		repairinDao.updateisdel(ids, oper);
	}

	@Override
	public void updateState(String[] ids, WorkingBill workingbill,
			Repairin repairin, Admin admin) {
		for (int i = 0; i < ids.length; i++) {
			repairin = repairinService.load(ids[i]);
			if (!"1".equals(repairin.getState())) {
				repairin.setState("1");
				repairin.setConfirmUser(admin);
				workingbill
						.setTotalRepairinAmount(workingbill
								.getTotalRepairinAmount()
								+ repairin.getReceiveAmount());
				repairinService.update(repairin);
			}
		}
		workingBillService.update(workingbill);
	}

	@Override
	public void updateStates(String[] ids, WorkingBill workingbill,
			Repairin repairin, Admin admin) {
		for (int i = 0; i < ids.length; i++) {
			repairin = repairinService.load(ids[i]);
			repairin.setConfirmUser(admin);
			if ("1".equals(repairin.getState())) {
				workingbill
				.setTotalRepairinAmount(workingbill
						.getTotalRepairinAmount()
						- repairin.getReceiveAmount());
			}
			repairin.setState("3");
			repairinService.update(repairin);
		}
		workingBillService.update(workingbill);
	}
}
