package cc.jiuyi.dao.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Dao接口 - 返修收货
 */
@Repository
public class RepairinDaoImpl extends BaseDaoImpl<Repairin, String> implements
		RepairinDao {
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private RepairinService repairinService;
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Repairin.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Repairin repairin = super.load(id);
			repairin.setIsDel(oper);// 标记删除
			super.update(repairin);
		}
	}

	@Override
	public void updateState(String[] ids, WorkingBill workingbill,Repairin repairin,Admin admin) {
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
