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
	public Pager getSamplePager(Pager pager)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Sample.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));		
		return super.findByPager(pager,detachedCriteria);
	}
	
}
