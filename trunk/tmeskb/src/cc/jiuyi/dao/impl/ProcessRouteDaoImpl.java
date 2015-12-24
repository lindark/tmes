package cc.jiuyi.dao.impl;

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

	public Integer getMaxVersion(String productid) {
		String hql = "select max(a.version) from ProcessRoute a where a.products.id = ?";
		return (Integer) getSession().createQuery(hql)
				.setParameter(0, productid).uniqueResult();
	}

	@Override
	public Integer getMaxVersionByCode(String productCode) {
		String hql = "select max(a.version) from ProcessRoute a where a.products.productsCode = ?";
		return (Integer) getSession().createQuery(hql)
				.setParameter(0, productCode).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessRoute> getProcessRouteByProductCode(String productCode) {
		int version = getMaxVersionByCode(productCode);
		String hql = "from ProcessRoute a where a.version=" + version
				+ " and a.products.productsCode=?";
		return getSession().createQuery(hql).setParameter(0, productCode)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessRoute> getAllProcessRouteByProductCode(String productCode) {
		String hql = "from ProcessRoute a where a.products.productsCode=?";
		return getSession().createQuery(hql).setParameter(0, productCode)
				.list();
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ProcessRoute.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if (map.get("processCode") != null) {
				detachedCriteria.add(Restrictions.like("process.processCode",
						"%" + map.get("processCode") + "%"));
			}
			if (map.get("processName") != null) {
				detachedCriteria.add(Restrictions.like("process.processName",
						"%" + map.get("processName") + "%"));
			}
			if (map.get("productsCode") != null) {
				detachedCriteria.add(Restrictions.like("products.productsCode",
						"%" + map.get("productsCode") + "%"));
			}
			if (map.get("productsName") != null) {
				detachedCriteria.add(Restrictions.like("products.productsName",
						"%" + map.get("productsName") + "%"));
			}
		}
		return super.findByPager(pager, detachedCriteria);
	}

	/**
	 * 根据版本号和产品编号获取相关工序
	 * @param version
	 * @param productCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessRoute> getProcessRouteByVersionAndCode(Integer version,
			String productCode) {
		String hql = "from ProcessRoute a where a.version=? a.products.productsCode=?";
		return getSession().createQuery(hql).setParameter(0, version).setParameter(1, productCode).list();
	}
}
