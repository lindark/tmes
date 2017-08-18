package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDetailDao;
import cc.jiuyi.entity.RepairDetail;

/**
 * Dao接口 - 交接主表
 */
@Repository
public class RepairDetailDaoImpl extends BaseDaoImpl<RepairDetail, String> implements  RepairDetailDao {

	@Override
	public List<RepairDetail> getPickList() {
		String hql = "From RepairDetail repairDetail order by pick.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<RepairDetail> getPickList(String processids) {
		String[] s = processids.split(",");
		String hql = "From RepairDetail repairDetail where process.id in (?) order by pick.crateDate desc";
		return getSession().createQuery(hql).setParameter(0, s).list();
	}

	@Override
	public Pager getRepairDetailPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(RepairDetail.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.get("name") != null) {
			detachedCriteria.add(Restrictions.like(
					"repirName",
					"%" + map.get("name") + "%"));
		}	
		detachedCriteria.add(Restrictions.eq("state", "1"));
		return super.findByPager(pager, detachedCriteria);
	}

}
