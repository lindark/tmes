package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverAllDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;

/**
 * Dao实现类 - 工序交接子类
 */
@Repository
public class ProcessHandoverAllDaoImpl extends BaseDaoImpl<ProcessHandoverAll, String> implements
	ProcessHandoverAllDao{

	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProcessHandoverAll.class);
		detachedCriteria.add(Restrictions.eq("isdel", "N"));
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
//		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit() !=null){
//			detachedCriteria.add(Restrictions.eq("factoryUnitCode", admin.getTeam().getFactoryUnit().getFactoryUnitCode()));
//		}
		pagerSqlByjqGrid(pager,detachedCriteria);
		return super.findByPager(pager, detachedCriteria);
		
	}
}
