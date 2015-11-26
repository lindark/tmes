package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PollingtestDao;
import cc.jiuyi.entity.Pollingtest;

/**
 * Dao接口 - 巡检
 */
@Repository
public class PollingtestDaoImpl extends BaseDaoImpl<Pollingtest, String>
		implements PollingtestDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Pollingtest.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Pollingtest pollingtest = super.load(id);
			pollingtest.setIsDel(oper);// 标记删除
			super.update(pollingtest);
		}

	}

}
