package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.MaterialDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;

/**
 * Dao实现类 - Material
 */

@Repository
public class MaterialDaoImpl extends BaseDaoImpl<Material, String> implements
		MaterialDao {

	@Override
	public void delete(String id) {
		Material material = load(id);
		this.delete(material);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Material> getMaterialList() {
		String hql = "From Material material order by material.id asc material.crateDate desc";
		return getSession().createQuery(hql).setCacheable(true).list();
	}

	public Pager getMaterialPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Material.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
			if(!existAlias(detachedCriteria, "factoryunit", "factoryunit"))
			{
				detachedCriteria.createAlias("factoryunit", "factoryunit");
			}
		if (map.size() > 0) {
			if(map.get("materialCode")!=null){
			    detachedCriteria.add(Restrictions.like("materialCode", "%"+map.get("materialCode")+"%"));
			}		
			if(map.get("materialName")!=null){
				detachedCriteria.add(Restrictions.like("materialName", "%"+map.get("materialName")+"%"));
			}
			/*if(map.get("factoryunitId")!=null){
				detachedCriteria.add(Restrictions.eq("factoryunit.id", map.get("factoryunitId")));
			}*/
			if(map.get("factoryUnitName")!=null){
				detachedCriteria.add(Restrictions.eq("factoryunit.factoryUnitName", map.get("factoryUnitName")));
			}
		}		
		return super.findByPager(pager, detachedCriteria);
	}


	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Material material=super.load(id);
			//material.setIsDel(oper);//标记删除
			super.update(material);
		}
		
	}

	@Override
	public boolean isExistByMaterialCode(String materialCode) {
		String hql="from Material material where lower(material.materialCode)=lower(?)";
		Material material=(Material) getSession().createQuery(hql).setCacheable(true).setParameter(0, materialCode).uniqueResult();
		System.out.println(hql);
		if(material!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Products> getProductsList() {
		String hql = "From Products products order by products.id asc products.crateDate desc";
		return getSession().createQuery(hql).setCacheable(true).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Material> getMantrBom(String matnr) {
		String hql="select distinct a from Material a join a.products b where b.productsCode = ?";
		return getSession().createQuery(hql).setCacheable(true).setParameter(0, matnr).list();
		
	}

	@SuppressWarnings("unchecked")
	public List<Material> getMantrBom(Object[] matnrs) {
		String hql="select distinct a from Material a join a.products b where b.productsCode in (:list)";
		return getSession().createQuery(hql).setParameterList("list", matnrs).list();
	}

	/**
	 * 根据物料id查询是否存在
	 */
	@SuppressWarnings("unchecked")
	public boolean getByCode(String code)
	{
		String hql="from Material where materialCode=? and isRepair=?";
		List<Material>list=this.getSession().createQuery(hql).setParameter(0, code).setParameter(1, "1").list();
		if(list.size()>0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据物料编码查询
	 */
	@SuppressWarnings("unchecked")
	public Material getByNum(String materialCode)
	{
		String hql="from Material where materialCode=?";
		List<Material>list=this.getSession().createQuery(hql).setParameter(0, materialCode).list();
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
}