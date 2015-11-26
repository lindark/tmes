package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProductsDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;

/**
 * Dao实现类 - products
 */

@Repository
public class ProductsDaoImpl extends BaseDaoImpl<Products, String> implements
		ProductsDao {

	@Override
	public void delete(String id) {
		Products products = load(id);
		this.delete(products);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Products> getProductsList() {
		String hql = "From Products products order by products.id asc products.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getProductsPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Products.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if (map.size() > 0) {
			if(map.get("productsCode")!=null){
			    detachedCriteria.add(Restrictions.like("productsCode", "%"+map.get("productsCode")+"%"));
			}		
			if(map.get("productsName")!=null){
				detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("productsName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public Pager getProductsPager2(Pager pager, HashMap<String, String> map,String id) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Products.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if (map.size() > 0) {
			if(map.get("productsCode")!=null){
			    detachedCriteria.add(Restrictions.like("productsCode", "%"+map.get("productsCode")+"%"));
			}		
			if(map.get("productsName")!=null){
				detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("productsName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		
		if(!existAlias(detachedCriteria, "process", "process"))
			detachedCriteria.createAlias("process", "process");
		detachedCriteria.add(Restrictions.eq("process.id", id));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Products products=super.load(id);
			products.setIsDel(oper);//标记删除
			super.update(products);
		}
		
	}

	public boolean isExistByProductsCode(String productsCode) {
		String hql="from Products products where lower(products.productsCode)=lower(?)";
		Products products=(Products)getSession().createQuery(hql).setParameter(0, productsCode).uniqueResult();
		System.out.println(hql);
		if(products !=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isExistByMaterialGroup(String materialGroup) {
		String hql="from Products products where lower(products.materialGroup)=lower(?)";
		Products products=(Products)getSession().createQuery(hql).setParameter(0, materialGroup).uniqueResult();
		System.out.println(hql);
		if(products !=null){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Process> getAllProcess() {
		String hql = "from Process as a where a.isDel='N'";
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> getAllMaterial() {
		String hql = "from Material as a where a.isDel='N'";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Products getProducts(String matnr) {
		String hql="from Products where productsCode= ?";
		return (Products) getSession().createQuery(hql).setParameter(0, matnr).uniqueResult();
	}
}