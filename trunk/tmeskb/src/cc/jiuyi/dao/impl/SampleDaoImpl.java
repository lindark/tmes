package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.SampleDao;
import cc.jiuyi.entity.Sample;
/**
 * 抽检
 * @author gaoyf
 *
 */
@Repository
public class SampleDaoImpl extends BaseDaoImpl<Sample, String> implements SampleDao
{
	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager,String wbId)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Sample.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "workingBill", "workingBill"))
		{
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("workingBill.id", wbId));
		return super.findByPager(pager,detachedCriteria);
	}
	
}
