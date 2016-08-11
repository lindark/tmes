package cc.jiuyi.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverAllDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
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

	@Override
	public List<ProcessHandoverAll> getListOfAllProcess(String productDate,
			String shift, String factoryId) { 
		String hql = "From ProcessHandoverAll where productDate=? and shift=? and factoryUnitId=? and state='2'";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).setParameter(2, factoryId).list();
	}
}
