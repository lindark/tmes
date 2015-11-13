package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ProductsDao;
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
		String wheresql = productspagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Products.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
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

	public String productspagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() ==  true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp()) + " ";
				ishead = 1;
			}

		}
		System.out.println("wheresql:" + wheresql);
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Products products=super.load(id);
			products.setIsDel(oper);//标记删除
			super.update(products);
		}
		
	}

	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
}