package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.ProcessRoute;

/**
 * Dao实现类 - 工艺路线
 */

@Repository
public class ProcessRouteDaoImpl  extends BaseDaoImpl<ProcessRoute, String> implements ProcessRouteDao {

	
	public Integer getMaxVersion(String productid){
		String hql="select max(a.version) from ProcessRoute a where a.products.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productid).uniqueResult();
	}

	@Override
	public Integer getMaxVersionByCode(String productCode) {
		String hql="select max(a.version) from ProcessRoute a where a.products.productsCode = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productCode).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessRoute> getProcessRouteByProductCode(String productCode) {
		int version = getMaxVersionByCode(productCode);
		String hql = "from ProcessRoute a where a.version="+version+" and a.products.productsCode=?";
		return getSession().createQuery(hql).setParameter(0, productCode).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessRoute> getAllProcessRouteByProductCode(String productCode) {
		String hql = "from ProcessRoute a where a.products.productsCode=?";
		return getSession().createQuery(hql).setParameter(0, productCode).list();
	}
}
