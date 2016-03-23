package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.LocatHandOverHeaderDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.LocatHandOverHeader;
import cc.jiuyi.entity.ReturnProduct;
/**
 * Dao接口 - 线边仓交接 主表
 */
@Repository
public class LocatHandOverHeaderDaoImpl extends BaseDaoImpl<LocatHandOverHeader, String> implements
		LocatHandOverHeaderDao {

	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LocatHandOverHeader.class);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("productDate",admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift",admin.getShift()));
		pagerSqlByjqGrid(pager,detachedCriteria);
		return super.findByPager(pager,detachedCriteria);
	}
	
}
