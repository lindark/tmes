package cc.jiuyi.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;
/**
 * Dao实现类 - 产品Bom
 */

@Repository
public class BomDaoImpl  extends BaseDaoImpl<Bom, String> implements BomDao {

	
	@SuppressWarnings("unchecked")
	public List<Bom> getBomList(String aufnr,Integer version){
		String hql="from Bom where orders.aufnr = ? and version = ?";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).list();
	}
	
	public Integer getMaxVersion(String matnr,String productDate){
		String hql="select max(a.version) from Bom a where a.orders.matnr = ? and a.effectiveDate <= ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, matnr).setParameter(1, productDate).uniqueResult();
	}
	
	
	
	
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Integer getMaxVersion(String aufnr) {
		String hql="select max(a.version) from Bom a where a.orders.aufnr = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, aufnr).uniqueResult();
	}
	

}
