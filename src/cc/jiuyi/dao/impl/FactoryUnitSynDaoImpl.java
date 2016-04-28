package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryUnitSynDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.FactoryUnitSyn;
/**
 * Dao实现类 - factoryUnit
 */

@Repository
public class FactoryUnitSynDaoImpl extends BaseDaoImpl<FactoryUnitSyn, String> implements
		FactoryUnitSynDao {

	
	@Override
	public FactoryUnitSyn get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from FactoryUnitSyn as model where model."
				+ propertyName + " = ? and isDel=?";
		return (FactoryUnitSyn) getSession().createQuery(hql).setParameter(0, value).setParameter(1, "N").uniqueResult();
	}

	@Override
	public Pager getFactoryUnitSynPager(Pager pager, HashMap<String, String> map) {

		//String wheresql = factoryUnitpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(FactoryUnitSyn.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		/*
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}*/
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("factoryUnitCode")!=null){
			    detachedCriteria.add(Restrictions.like("factoryUnitCode", "%"+map.get("factoryUnitCode")+"%"));
			}		
			if(map.get("factoryUnitName")!=null){
				detachedCriteria.add(Restrictions.like("factoryUnitName", "%"+map.get("factoryUnitName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	/*@Override
	public List<FactoryUnitSyn> getAll() {
		String hql = "from FactoryUnitSyn where isDel=? ";
		return (List<FactoryUnitSyn>)getSession().createQuery(hql).setParameter(0, "N").list();
	}*/
}
