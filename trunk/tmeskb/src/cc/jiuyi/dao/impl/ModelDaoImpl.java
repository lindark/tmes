package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ModelDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;

/**
 * Dao实现类 - 工模
 */

@Repository
public class ModelDaoImpl extends BaseDaoImpl<Model, String> implements ModelDao {

	public Pager getModelPager(Pager pager, HashMap<String, String> map,String id,String team) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Model.class);
		pagerSqlByjqGrid(pager,detachedCriteria);

		if(!super.existAlias(detachedCriteria, "equipments", "equipments")){
			detachedCriteria.createAlias("equipments", "equipments");//表名，别名*/							
		}
//		
//		if(!super.existAlias(detachedCriteria, "teamId", "team")){
//			detachedCriteria.createAlias("teamId", "team");//表名，别名*/							
//		}
//		
//		if(!super.existAlias(detachedCriteria, "initiator", "initiator")){
//			detachedCriteria.createAlias("initiator", "initiator");//表名，别名*/							
//		}
//		
//		if(!super.existAlias(detachedCriteria, "insepector", "insepector")){
//			detachedCriteria.createAlias("insepector", "insepector");//表名，别名*/							
//		}
//		
		if(!super.existAlias(detachedCriteria, "fixer", "fixer")){
			detachedCriteria.createAlias("fixer", "fixer");//表名，别名*/							
		}
		
		if (map.size() > 0) {

			if(map.get("repairName")!=null){
			    detachedCriteria.add(Restrictions.like("fixer.name", "%"+map.get("repairName")+"%"));
			}
			
			if(map.get("equipmentName")!=null){
			    detachedCriteria.add(Restrictions.like("equipments.equipmentName", "%"+map.get("equipmentName")+"%"));
			}
			
		}
		
		Disjunction disjunction = Restrictions.disjunction();  
		if(team!=null && !"".equals(team)){
			disjunction.add(Restrictions.eq("teamId.id", team));
		}
		disjunction.add(Restrictions.eq("insepector.id", id));
		disjunction.add(Restrictions.eq("fixer.id", id));
		detachedCriteria.add(disjunction);
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Model model=super.load(id);
			model.setIsDel(oper);//标记删除
			super.update(model);
		}
   }

	@Override
	public Pager findByPager(Pager pager, HashMap<String, String> map,String id) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Model.class);	
		pagerSqlByjqGrid(pager,detachedCriteria);

		if(!super.existAlias(detachedCriteria, "equipments", "equipments")){
			detachedCriteria.createAlias("equipments", "equipments");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "fixer", "fixer")){
			detachedCriteria.createAlias("fixer", "fixer");//表名，别名*/							
		}
//		if(!super.existAlias(detachedCriteria, "abnormal", "abnormal")){
//		detachedCriteria.createAlias("abnormal", "abnormal");						
//	    }
		
		if (map.size() > 0) {

			if(map.get("repairName")!=null){
			    detachedCriteria.add(Restrictions.like("fixer.name", "%"+map.get("repairName")+"%"));
			}
			
			if(map.get("equipmentName")!=null){
			    detachedCriteria.add(Restrictions.like("equipments.equipmentName", "%"+map.get("equipmentName")+"%"));
			}
			
		}
		detachedCriteria.add(Restrictions.eq("abnormal.id", id));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map,
			String id, String teamid) {
		
		String hql="from Model model join model.fixer model1 join model.equipments model2";
		
		Integer ishead=0;
		if (map.size() > 0) {
			if (!map.get("repairName").equals("")) {
				hql+=" where model1.name like '%"+map.get("repairName")+"%'";
				ishead=1;
			}	
			if(!map.get("equipmentName").equals("")){
					if(ishead==0){
						hql+=" where model2.equipmentName like '%"+map.get("equipmentName")+"%'";
						ishead=1;
					}else{
						hql+=" and  model2.equipmentName like '%"+map.get("equipmentName")+"%'";
					}
			}
		}
		if(ishead==0){
			if(teamid!=null){
				hql+=" where model.insepector.id = '"+id+"' or model1.id='"+id+"' or model.teamId.id='"+teamid+"'";
			}else{
				hql+=" where model.insepector.id = '"+id+"' or model1.id='"+id+"'";
			}
		}else{
			if(teamid!=null){
				hql+=" and (model.insepector.id = '"+id+"' or model1.id='"+id+"' or model.teamId.id='"+teamid+"')";
			}else{
				hql+=" and (model.insepector.id = '"+id+"' or model1.id='"+id+"')";
			}
		
		}

		return getSession().createQuery(hql).list();
	
	}
  
}
