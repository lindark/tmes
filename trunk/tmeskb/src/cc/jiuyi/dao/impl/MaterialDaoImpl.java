package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
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
		return getSession().createQuery(hql).list();
	}

	public Pager getMaterialPager(Pager pager, HashMap<String, String> map,String productsName) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Material.class);
		detachedCriteria.createAlias("products", "products");
		pagerSqlByjqGrid(pager,detachedCriteria);
	
		if (map.size() > 0) {
			if(map.get("materialCode")!=null){
			    detachedCriteria.add(Restrictions.like("materialCode", "%"+map.get("materialCode")+"%"));
			}		
			if(map.get("materialName")!=null){
				detachedCriteria.add(Restrictions.like("materialName", "%"+map.get("materialName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("productsName1")!=null){
				detachedCriteria.add(Restrictions.like("products.productsName", "%"+map.get("productsName1")+"%"));
			}
//			if(map.get("start")!=null||map.get("end")!=null){
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				try{
//					Date start=sdf.parse(map.get("start"));
//					Date end=sdf.parse(map.get("end"));
//					detachedCriteria.add(Restrictions.between("createDate", start, end));
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		//detachedCriteria.createAlias("products", "products");
		return super.findByPager(pager, detachedCriteria);
	}


	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Material material=super.load(id);
			material.setIsDel(oper);//标记删除
			super.update(material);
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isExistByMaterialCode(String materialCode) {
		String hql="from Material material where lower(material.materialCode)=lower(?)";
		Material material=(Material) getSession().createQuery(hql).setParameter(0, materialCode).uniqueResult();
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
		return getSession().createQuery(hql).list();
	}

	

}