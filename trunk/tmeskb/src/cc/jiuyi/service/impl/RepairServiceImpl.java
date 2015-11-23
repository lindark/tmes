package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 返修
 */
@Service
@Transactional
public class RepairServiceImpl extends BaseServiceImpl<Repair, String>
		implements RepairService {
	@Resource
	private RepairDao repairDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(RepairDao repairDao) {
		super.setBaseDao(repairDao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		repairDao.updateisdel(ids, oper);
	}

	@Override
	/**
	 * 考虑线程同步
	 */
	public synchronized void updateState(List<Repair> list, String statu,
			String workingbillid) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getTotalRepairAmount();
		for (int i = 0; i < list.size(); i++) {
			Repair repair = list.get(i);
			if (statu.equals("1")) {
				totalamount = repair.getRepairAmount() + totalamount;
			}
			if (statu.equals("3") && repair.getState().equals("1")) {
				totalamount -= repair.getRepairAmount();
			}
			repair.setConfirmUser(admin);
			repair.setState(statu);
			repairDao.update(repair);
		}
		workingbill.setTotalRepairAmount(totalamount);
		workingbillService.update(workingbill);
	}
}
