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
import cc.jiuyi.dao.LocatHandOverHeaderDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.LocatHandOverHeader;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.ReturnProduct;
/**
 * Dao接口 - 线边仓交接 主表
 */
@Repository
public class LocatHandOverHeaderDaoImpl extends BaseDaoImpl<LocatHandOverHeader, String> implements
		LocatHandOverHeaderDao {

	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LocatHandOverHeader.class);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("productDate",admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift",admin.getShift()));
		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null)
		detachedCriteria.add(Restrictions.eq("factoryUnitCode",admin.getTeam().getFactoryUnit().getFactoryUnitCode()));
		
		pagerSqlByjqGrid(pager,detachedCriteria);
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(LocatHandOver.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (!existAlias(detachedCriteria, "locatHandOverHeader", "locatHandOverHeader")) {
			detachedCriteria.createAlias("locatHandOverHeader", "locatHandOverHeader");
		}
		if (map.size() > 0) {
			if (map.get("locationCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"locationCode",
						"%" + map.get("locationCode") + "%"));
			}
			if (map.get("lgpla") != null) {
				detachedCriteria.add(Restrictions.like(
						"lgpla",
						"%" + map.get("lgpla") + "%"));
			}	
			if (map.get("start") != null && map.get("end") != null) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					Date start=sd.parse(map.get("start"));
					Date end=sd.parse(map.get("end"));
					end = DateUtils.addDays(end,1);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.eq(
						"locatHandOverHeader.state",
						map.get("state")));
			}	
			
		}		
		return super.findByPager(pager, detachedCriteria);
	}
	
	
	
	
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from LocatHandOver model join model.locatHandOverHeader model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("locationCode").equals("")) {
				if(ishead==0){
					hql+=" where model.locationCode like '%"+map.get("locationCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model.locationCode like '%"+map.get("locationCode")+"%'";
				}
			}	
			if (!map.get("lgpla").equals("")) {
				if(ishead==0){
					hql+=" where model.lgpla like '%"+map.get("lgpla")+"%'";
					ishead=1;
				}else{
					hql+=" and model.lgpla like '%"+map.get("lgpla")+"%'";
				}
			}	
			if (!map.get("state").equals("")) {
				if(ishead==0){
					hql+=" where model1.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.state like '%"+map.get("state")+"%'";
				}
			}	
			if((!map.get("start").equals("")) && (!map.get("end").equals(""))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{					
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					end=DateUtils.addDays(end, 1); 
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
