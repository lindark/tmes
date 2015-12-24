package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;

/**
 * Dao实现类 - 产品Bom
 */

@Repository
public class BomDaoImpl  extends BaseDaoImpl<Bom, String> implements BomDao {

	
	public Integer getMaxVersionByid(String productid){
		String hql="select max(a.version) from Bom a where a.products.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productid).uniqueResult();
	}
	
	public Integer getMaxVersionBycode(String productCode){
		String hql="select max(a.version) from Bom a where a.products.productsCode = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productCode).uniqueResult();
	}

	@Override
	public List<Bom> getBomByProductCode(String productCode,String materialCode,Integer version) {
		String hql="from Bom where products.productsCode=? and materialCode=? and version = ?";
		return getSession().createQuery(hql).setParameter(0, productCode).setParameter(1, materialCode).setParameter(2, version).list();
	}

	@Override
	public List<Bom> getListByid(String productid, Integer version) {
		String hql="from Bom where products.id = ? and version = ?";
		return getSession().createQuery(hql).setParameter(0, productid).setParameter(1, version).list();
	}
	
	@Override
	public List<Bom> getListBycode(String productcode, Integer version) {
		String hql="from Bom where products.productsCode = ? and version = ?";
		return getSession().createQuery(hql).setParameter(0, productcode).setParameter(1, version).list();
	}
	
	

}
