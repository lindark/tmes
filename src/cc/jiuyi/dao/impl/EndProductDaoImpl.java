package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EndProductDao;
import cc.jiuyi.entity.EndProduct;
/**
 * Dao实现类 - 成品入库
 */
@Repository
public class EndProductDaoImpl extends BaseDaoImpl<EndProduct, String> implements
		EndProductDao {


	@Override
	public Pager getProductsPager(Pager pager,String productDate, String shift,String factoryCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(EndProduct.class);	
		pagerSqlByjqGrid(pager,detachedCriteria);	
		detachedCriteria.add(Restrictions.eq("productDate",productDate));
		detachedCriteria.add(Restrictions.eq("shift",shift));		
		if(factoryCode!=null && !"".equals(factoryCode)){
			detachedCriteria.add(Restrictions.eq("factoryCode",factoryCode));		
		}
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
			if (map.get("materialDesp") != null) {
				detachedCriteria.add(Restrictions.like(
						"materialDesp",
						"%" + map.get("materialDesp") + "%"));
			}	
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"state",
						"%" + map.get("state") + "%"));
			}
			if (map.get("start") != null && map.get("end")==null) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date start=sd.parse(map.get("start")+" 00:00:00");
						//Date now=sd.parse(sd.format(new Date()));
					//now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.ge("createDate", start));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("start") == null && map.get("end")!=null ) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date end=sd.parse(map.get("end")+" 23:59:59");
					//Date now=sd.parse(sd.format(new Date()));
					//now = DateUtils.addDays(now, 1);
//					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.le("createDate", end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(map.get("start")!=null && map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
//					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		System.out.println("---------------------");
		return super.findByPager(pager,detachedCriteria);
	}

	@Override
	public List<EndProduct> getListChecked(String productDate, String shift) {
		// TODO Auto-generated method stub
		String hql = "From EndProduct where state = '2' and  productDate = ? and shift = ?";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).list();
	}

	@Override
	public List<EndProduct> historyExcelExport(HashMap<String, String> map) {
		String hql="from EndProduct model";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("materialCode").equals("")) {
				if(ishead==0){
					hql+=" where model.materialCode like '%"+map.get("materialCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model.materialCode like '%"+map.get("materialCode")+"%'";
				}
			}	
			if (!map.get("materialDesp").equals("")) {
				if(ishead==0){
					hql+=" where model.materialDesp like '%"+map.get("materialDesp")+"%'";
					ishead=1;
				}else{
					hql+=" and model.materialDesp like '%"+map.get("materialDesp")+"%'";
				}
			}	
			if (!map.get("state").equals("")) {
				if(ishead==0){
					hql+=" where model.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model.state like '%"+map.get("state")+"%'";
				}
			}	
			if(!map.get("start").equals("") && !map.get("end").equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
					//System.out.println(map.get("start")); 
					if(ishead==0){
						hql+=" where model.createDate between :start and :end";
						ishead=1;
					}else{
						hql+=" and model.createDate between :start and :end";
					}
					parameters.put("start", start);
					parameters.put("end", end);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		Query query = getSession().createQuery(hql);
		
		if(parameters.get("start")!=null){
			query.setParameter("start", parameters.get("start"));
		}
		if(parameters.get("end") != null){
			query.setParameter("end", parameters.get("end"));
		}
		
		return query.list();
	}

	
}
