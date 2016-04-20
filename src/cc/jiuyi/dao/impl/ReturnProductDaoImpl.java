package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReturnProductDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ReturnProduct;
/**
 * Dao实现类 - 成品入库
 */
@Repository
public class ReturnProductDaoImpl extends BaseDaoImpl<ReturnProduct, String> implements
		ReturnProductDao {

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReturnProduct.class);
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
			if (map.get("start") != null && map.get("end")==null) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					Date start=sd.parse(map.get("start"));
					Date now=sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("start") == null && map.get("end")!=null ) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					Date end=sd.parse(map.get("end"));
					Date now=sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate", end, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"state",
						"%" + map.get("state") + "%"));
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
	public List<ReturnProduct> historyExcelExport(HashMap<String, String> map) {
		String hql="from ReturnProduct model";
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
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					System.out.println(map.get("start")); 
					if(ishead==0){
						hql+=" where mode.createDate between :start and :end";
						ishead=1;
					}else{
						hql+=" and mode.createDate between :start and :end";
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

	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReturnProduct.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(admin!=null){
			if(admin.getProductDate()!=null){
				detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
			}
			if(admin.getShift()!=null){
				detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
			}
			if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null && admin.getTeam().getFactoryUnit().getFactoryUnitCode()!=null){
				detachedCriteria.add(Restrictions.eq("factoryCode", admin.getTeam().getFactoryUnit().getFactoryUnitCode()));
			}
		}
		return super.findByPager(pager,detachedCriteria);
	}
	
}
