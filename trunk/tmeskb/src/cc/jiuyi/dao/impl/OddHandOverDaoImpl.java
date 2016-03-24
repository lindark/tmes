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
import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.entity.OddHandOver;
/**
 * Dao实现类 - 零头交接
 */

@Repository
public class OddHandOverDaoImpl extends BaseDaoImpl<OddHandOver, String> implements
		OddHandOverDao {

	public OddHandOver findHandOver(String workingBillCode){
		String hql="from OddHandOver a where a.afterWorkingCode = ?";
		return (OddHandOver) getSession().createQuery(hql).setParameter(0, workingBillCode).setMaxResults(1).uniqueResult();
		
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(OddHandOver.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (!existAlias(detachedCriteria, "workingBill", "workingBill")) {
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		if (map.size() > 0) {
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"materialCode",
						"%" + map.get("materialCode") + "%"));
			}
			if (map.get("workingBillCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingBill.workingBillCode",
						"%" + map.get("workingBillCode") + "%"));
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
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from OddHandOver model join model.workingBill model1";
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
			if (!map.get("workingBillCode").equals("")) {
				if(ishead==0){
					hql+=" where model1.workingBillCode like '%"+map.get("workingBillCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.workingBillCode like '%"+map.get("workingBillCode")+"%'";
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

}
