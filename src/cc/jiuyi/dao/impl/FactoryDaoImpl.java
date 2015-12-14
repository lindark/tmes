package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.FactoryDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Process;

/**
 * Dao实现类 - Factory
 */

@Repository
public class FactoryDaoImpl extends BaseDaoImpl<Factory, String> implements FactoryDao {

	@Override
	public void delete(String id) {
		Factory factory = load(id);
		this.delete(factory);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Factory> getFactoryList() {
		String hql = "From Factory Factory order by Factory.id asc Factory.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	
	public Pager getFactoryPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Factory.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if (map.size() > 0) {
			if(map.get("factoryCode")!=null){
			    detachedCriteria.add(Restrictions.like("factoryCode", "%"+map.get("factoryCode")+"%"));
			}		
			if(map.get("factoryName")!=null){
				detachedCriteria.add(Restrictions.like("factoryName", "%"+map.get("factoryName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}			
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Factory factory=super.load(id);
			factory.setIsDel(oper);//标记删除
			super.update(factory);
		}
   }

	@SuppressWarnings("unchecked")
	public boolean isExistByFactoryCode(String factoryCode) {
		String hql = "from Factory factory where lower(factory.factoryCode) = lower(?)";
		Factory factory = (Factory) getSession().createQuery(hql).setParameter(0, factoryCode).uniqueResult();
		if (factory != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}