package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapDao;
import cc.jiuyi.entity.Scrap;

/**
 * 报废
 * @author gaoyf
 *
 */
@Repository
public class ScrapDaoImpl extends BaseDaoImpl<Scrap, String> implements ScrapDao
{

	/**
	 * jqGrid查询
	 */
	public Pager getScrapPager(Pager pager,String wbId)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Scrap.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "workingBill", "workingBill"))
		{
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("workingBill.id", wbId));
		return super.findByPager(pager, detachedCriteria);
	}
}
