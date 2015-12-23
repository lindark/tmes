package cc.jiuyi.dao.impl;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;

/**
 * Dao实现类 - 产品Bom
 */

@Repository
public class BomDaoImpl  extends BaseDaoImpl<Bom, String> implements BomDao {

	
	public Integer getMaxVersion(String productid){
		String hql="select max(a.version) from Bom a where a.products.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, productid).uniqueResult();
	}
}
