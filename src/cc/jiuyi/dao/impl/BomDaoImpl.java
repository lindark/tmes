package cc.jiuyi.dao.impl;

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
	public List<Bom> getBomListByMaxVersion(Integer version) {
		String hql="from Bom where version = ?";
		return getSession().createQuery(hql).setParameter(0, version).list();
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

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if(!existAlias(detachedCriteria, "products", "products")){
				detachedCriteria.createAlias("products", "products");
			}
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like("materialCode",
						"%" + map.get("materialCode") + "%"));
			}
			if (map.get("materialName") != null) {
				detachedCriteria.add(Restrictions.like("materialName",
						"%" + map.get("materialName") + "%"));
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
	 * 根据产品id和随工单中的bom版本号查询bom表
	 */
	@SuppressWarnings("unchecked")
	public List<Bom>list_bom(String pid,String version)
	{
		String hql="from Bom where products.id=? and version=?";
		return this.getSession().createQuery(hql).setParameter(0, pid).setParameter(1, version).list();
	}

	/**
	 * 根据产品id和随工单中的bom版本号查询bom表
	 * @param pid
	 * @param version
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Bom> getByPidAndWversion(String pid, String version)
	{
		String hql="from Bom where products.id=? and version=?";
		return this.getSession().createQuery(hql).setParameter(0, pid).setParameter(1, version).list();
	}
}
