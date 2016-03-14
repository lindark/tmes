package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Dao实现类 - 异常
 */

@Repository
public class AbnormalDaoImpl  extends BaseDaoImpl<Abnormal, String> implements AbnormalDao {

	public Pager getAbnormalPager(Pager pager, HashMap<String, String> map,Admin admin2) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Abnormal.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		if(!super.existAlias(detachedCriteria, "iniitiator", "admin")){
			detachedCriteria.createAlias("iniitiator", "admin");//表名，别名*/							
		}
		if(!super.existAlias(detachedCriteria, "responsorSet", "admin1")){
			detachedCriteria.createAlias("responsorSet", "admin1");//表名，别名*/			
		}
		
		detachedCriteria.add(Restrictions.or(Restrictions.eq("admin.id", admin2.getId()), Restrictions.eq("admin1.id", admin2.getId())));
		detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);//去重
		/*detachedCriteria.add(Restrictions.eq("classtime", admin2.getShift()));//班次
		detachedCriteria.add(Restrictions.eq("productdate", admin2.getProductDate()));//生产日期
*/		
		detachedCriteria.add(Restrictions.or(Restrictions.or(Restrictions.eq("state", "0"),Restrictions.eq("state", "2")),Restrictions.and(Restrictions.eq("classtime", admin2.getShift()), Restrictions.eq("productdate", admin2.getProductDate()))));
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
		/*if(!super.existAlias(detachedCriteria, "responsorSet", "admin1")){
			detachedCriteria.createAlias("responsorSet", "admin1");//表名，别名			
		}*/
		
		if (map != null && map.size() > 0) {
			
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("productdate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			if (!map.get("originator").equals("")) {
				detachedCriteria.add(Restrictions.like("admin.name", "%"
						+ map.get("originator").toString() + "%"));
			}
		}
		/*detachedCriteria.add(Restrictions.or(Restrictions.eq("admin.id", id), Restrictions.eq("admin1.id", id)));
		detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);//去重
*/		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		
		return super.findByPager(pager, detachedCriteria);
	} 
}
