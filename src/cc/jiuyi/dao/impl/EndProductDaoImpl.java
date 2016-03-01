package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EndProductDao;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
/**
 * Dao实现类 - 成品入库
 */
@Repository
public class EndProductDaoImpl extends BaseDaoImpl<EndProduct, String> implements
		EndProductDao {


	@Override
	public Pager getProductsPager(Pager pager,String productDate, String shift) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(EndProduct.class);	
		pagerSqlByjqGrid(pager,detachedCriteria);	
		detachedCriteria.add(Restrictions.eq("productDate",productDate));
		detachedCriteria.add(Restrictions.eq("shift",shift));		
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EndProduct.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"materialCode",
						"%" + map.get("materialCode") + "%"));
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
		return super.findByPager(pager,detachedCriteria);
	}

	@Override
	public List<EndProduct> getListChecked(String productDate, String shift) {
		// TODO Auto-generated method stub
		String hql = "From EndProduct where state = '2' and  productDate = ? and shift = ?";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).list();
	}

	
}
