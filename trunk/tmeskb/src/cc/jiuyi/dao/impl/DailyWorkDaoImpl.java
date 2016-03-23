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
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.DailyWork;

/**
 * Dao实现类 - 报工
 */

@Repository
public class DailyWorkDaoImpl extends BaseDaoImpl<DailyWork, String> implements
		DailyWorkDao {

	public Pager findPagerByjqGrid(Pager pager, HashMap<String,String> map, String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DailyWork.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			DailyWork dailyWork = super.load(id);
			dailyWork.setIsDel(oper);
			super.update(dailyWork);
		}

	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DailyWork.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
			detachedCriteria.createAlias("workingbill", "workingbill");
		}
		if (map.size() > 0) {
			if (map.get("matnr") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.matnr",
						"%" + map.get("matnr") + "%"));
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("maktx") + "%"));
			}	
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"state",
						"%" + map.get("state") + "%"));
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from DailyWork model join model.workingbill model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("matnr").equals("")) {
				if(ishead==0){
					hql+=" where model1.matnr like '%"+map.get("matnr")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.matnr like '%"+map.get("matnr")+"%'";
				}
			}	
			if (!map.get("maktx").equals("")) {
				if(ishead==0){
					hql+=" where model1.maktx like '%"+map.get("maktx")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.maktx like '%"+map.get("maktx")+"%'";
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