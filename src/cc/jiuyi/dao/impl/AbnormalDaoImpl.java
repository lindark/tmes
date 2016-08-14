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
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;

/**
 * Dao实现类 - 异常
 */

@Repository
public class AbnormalDaoImpl  extends BaseDaoImpl<Abnormal, String> implements AbnormalDao {

	public Pager getAbnormalPager(Pager pager, HashMap<String, String> map,Admin admin2) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Abnormal.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		if(!super.existAlias(detachedCriteria, "iniitiator", "iniitiator")){
			detachedCriteria.createAlias("iniitiator", "iniitiator");//表名，别名*/							
		}
		if(!super.existAlias(detachedCriteria, "iniitiator.team", "team")){
			detachedCriteria.createAlias("iniitiator.team", "team");//表名，别名*/							
		}
//		if(!super.existAlias(detachedCriteria, "responsorSet", "admin1")){
//			detachedCriteria.createAlias("responsorSet", "admin1");//表名，别名*/			
//		}
		
		//detachedCriteria.add(Restrictions.or(Restrictions.eq("iniitiator.id", admin2.getId()), Restrictions.eq("responsorSet.id", admin2.getId())));
		//detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);//去重
		/*detachedCriteria.add(Restrictions.eq("classtime", admin2.getShift()));//班次
		detachedCriteria.add(Restrictions.eq("productdate", admin2.getProductDate()));//生产日期
*/		
//		if(admin2.getTeam()!=null&&admin2.getTeam().getFactoryUnit()!=null)
//		detachedCriteria.add(Restrictions.or(Restrictions.or(Restrictions.eq("state", "0"),Restrictions.eq("state", "2")),Restrictions.and(Restrictions.eq("classtime", admin2.getShift()), Restrictions.eq("productdate", admin2.getProductDate()))));
		if(admin2.getTeam()!=null&&admin2.getTeam().getFactoryUnit()!=null)
		detachedCriteria.add(
		Restrictions.or(
				Restrictions.and(Restrictions.or(
						Restrictions.eq("state", "0"),Restrictions.eq("state", "2")),
						Restrictions.eq("team.factoryUnit", admin2.getTeam().getFactoryUnit())), 
					Restrictions.and(Restrictions.or(
							Restrictions.eq("state", "3"),Restrictions.eq("state", "4")),
								Restrictions.and(Restrictions.eq("classtime", admin2.getShift()), Restrictions.eq("productdate", admin2.getProductDate())))));
		
		
		/*Disjunction disjunction = Restrictions.disjunction();  
		disjunction.add(Restrictions.eq("state", "0"));
		disjunction.add(Restrictions.eq("state", "1"));
		disjunction.add(Restrictions.eq("state", "2"));
		detachedCriteria.add(disjunction);*/
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Abnormal abnormal=super.load(id);
			abnormal.setIsDel(oper);//标记删除
			super.update(abnormal);
		}
   }
	
	public Pager getAbnormalAllPager(Pager pager, HashMap<String, String> map,String id) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Abnormal.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		if(!super.existAlias(detachedCriteria, "iniitiator", "admin")){
			detachedCriteria.createAlias("iniitiator", "admin");//表名，别名							
		}
		
		if (map.size() > 0) {
			if (map.get("originator")!=null) {
				detachedCriteria.add(Restrictions.like("admin.name", "%"
						+ map.get("originator").toString() + "%"));
			}
			
			if(map.get("start")!=null||map.get("end")!=null){
 				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {

		String hql="from Abnormal model join model.iniitiator model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("originator").equals("")) {
				hql+=" where model1.name like '%"+map.get("originator")+"%'";
				ishead=1;
			}	
			if(!map.get("start").equals("") && !map.get("end").equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
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
