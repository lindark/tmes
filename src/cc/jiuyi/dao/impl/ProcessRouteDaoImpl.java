package cc.jiuyi.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.ProcessRoute;

/**
 * Dao实现类 - 工艺路线
 */

@Repository
public class ProcessRouteDaoImpl extends BaseDaoImpl<ProcessRoute, String>
		implements ProcessRouteDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ProcessRoute.class);
//		pagerSqlByjqGrid(pager, detachedCriteria);
//		if (map.size() > 0) {
//			if(!existAlias(detachedCriteria, "process", "process")){
//				detachedCriteria.createAlias("process", "process");
//			}
//			if(!existAlias(detachedCriteria, "products", "products")){
//				detachedCriteria.createAlias("products", "products");
//			}
//			if (map.get("processCode") != null) {
//				detachedCriteria.add(Restrictions.like("process.processCode",
//						"%" + map.get("processCode") + "%"));
//			}
//			if (map.get("processName") != null) {
//				detachedCriteria.add(Restrictions.like("process.processName",
//						"%" + map.get("processName") + "%"));
//			}
//			if (map.get("productsCode") != null) {
//				detachedCriteria.add(Restrictions.like("products.productsCode",
//						"%" + map.get("productsCode") + "%"));
//			}
//			if (map.get("productsName") != null) {
//				detachedCriteria.add(Restrictions.like("products.productsName",
//						"%" + map.get("productsName") + "%"));
//			}
//		}
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<ProcessRoute> getProcessRouteList(String aufnr, Integer version) {
		String hql="from ProcessRoute where orders.aufnr = ? and version = ?";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).list();
	}
	
	public Integer getMaxVersion(String matnr,String productDate){
		String hql="select max(a.version) from ProcessRoute a where a.orders.matnr = ? and a.effectiveDate <= ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, matnr).setParameter(1, productDate).uniqueResult();
	}
	
	public Integer getMaxVersion(String aufnr){
		String hql="select max(a.version) from ProcessRoute a where a.orders.aufnr = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, aufnr).uniqueResult();
	}


}
