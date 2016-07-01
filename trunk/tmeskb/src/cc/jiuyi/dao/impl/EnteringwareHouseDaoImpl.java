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
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.entity.EnteringwareHouse;

/**
 * Dao实现类 - 入库
 */

@Repository
public class EnteringwareHouseDaoImpl extends BaseDaoImpl<EnteringwareHouse, String> implements EnteringwareHouseDao {
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map, String workingbillId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EnteringwareHouse.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager,detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			EnteringwareHouse enteringwareHourse = super.load(id);
			enteringwareHourse.setIsdel(oper);//标记删除
			super.update(enteringwareHourse);
		}
		
		
	}

	@Override
	public List<EnteringwareHouse> getByBill(String workingBillId) {
		String hql = "from EnteringwareHouse as a where a.state='1' and a.workingbill.id=?";
		return getSession().createQuery(hql).setParameter(0, workingBillId).list();
	}



	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EnteringwareHouse.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
				detachedCriteria.createAlias("workingbill", "workingbill");
			}
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
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}



	@Override
	// @author Reece
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from EnteringwareHouse model join model.workingbill model1";
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