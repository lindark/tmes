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
import cc.jiuyi.dao.CartonsonDao;
import cc.jiuyi.entity.CartonSon;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Repository
public class CartonsonDaoImpl extends BaseDaoImpl<CartonSon, String> implements CartonsonDao
{

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(CartonSon.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (!existAlias(detachedCriteria, "carton", "carton")) {
			detachedCriteria.createAlias("carton", "carton");
		}
		if (map.size() > 0) {
			if (map.get("MATNR") != null) {
				detachedCriteria.add(Restrictions.like(
						"MATNR",
						"%" + map.get("MATNR") + "%"));
			}
			if (map.get("productname") != null) {
				detachedCriteria.add(Restrictions.like(
						"productname",
						"%" + map.get("productname") + "%"));
			}
			if (map.get("bktxt") != null) {
				detachedCriteria.add(Restrictions.like(
						"carton.bktxt",
						"%" + map.get("bktxt") + "%"));
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"carton.state",
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
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
			String hql="from CartonSon model join model.carton model1";
			Integer ishead=0;
			Map<String,Object> parameters = new HashMap<String,Object>();
			if (map.size() > 0) {
				if (!map.get("MATNR").equals("")) {
					if(ishead==0){
						hql+=" where model.MATNR like '%"+map.get("MATNR")+"%'";
						ishead=1;
					}else{
						hql+=" and model.MATNR like '%"+map.get("MATNR")+"%'";
					}
				}	
				if (!map.get("productname").equals("")) {
					if(ishead==0){
						hql+=" where model.productname like '%"+map.get("productname")+"%'";
						ishead=1;
					}else{
						hql+=" and model.productname like '%"+map.get("productname")+"%'";
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
				if (!map.get("bktxt").equals("")) {
					if(ishead==0){
						hql+=" where model1.bktxt like '%"+map.get("bktxt")+"%'";
						ishead=1;
					}else{
						hql+=" and model1.bktxt like '%"+map.get("bktxt")+"%'";
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
