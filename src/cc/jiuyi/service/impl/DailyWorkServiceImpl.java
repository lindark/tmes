package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 - 报工
 */

@Service
public class DailyWorkServiceImpl extends BaseServiceImpl<DailyWork, String>
		implements DailyWorkService {
	@Resource
	private DailyWorkDao dailyWorkDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(DailyWorkDao dailyWork) {
		super.setBaseDao(dailyWork);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return dailyWorkDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public synchronized void updateState(List<DailyWork> list, String statu,
			String workingbillid) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getDailyWorkTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			DailyWork dailyWork = list.get(i);
			if (statu.equals("1")) {
				totalamount = dailyWork.getEnterAmount() + totalamount;
			}
			if (statu.equals("3") && dailyWork.getState().equals("1")) {
				totalamount -= dailyWork.getEnterAmount();
			}
			dailyWork.setConfirmUser(admin);
			dailyWork.setState(statu);
			dailyWorkDao.update(dailyWork);
		}
		workingbill.setDailyWorkTotalAmount(totalamount);
		workingbillService.update(workingbill);

	}

}