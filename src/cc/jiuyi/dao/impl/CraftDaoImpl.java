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
import cc.jiuyi.dao.CraftDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.WorkShop;

/**
 * Dao实现类 - 工模
 */

@Repository
public class CraftDaoImpl extends BaseDaoImpl<Craft, String> implements CraftDao {

	public Pager getCraftPager(Pager pager, HashMap<String, String> map,String id,String team) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Craft.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if(!super.existAlias(detachedCriteria, "products", "products")){
			detachedCriteria.createAlias("products", "products");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "team", "team")){
			detachedCriteria.createAlias("team", "team");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "repairName", "repairName")){
			detachedCriteria.createAlias("repairName", "repairName");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "creater", "creater")){
			detachedCriteria.createAlias("creater", "creater");//表名，别名*/							
		}

		if (map.size() > 0) {			
			
			if(map.get("repair")!=null){
			    detachedCriteria.add(Restrictions.like("repairName.name", "%"+map.get("repair")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("products.productsName", "%"+map.get("productName")+"%"));
			}			
		}
		
		Disjunction disjunction = Restrictions.disjunction();  
		disjunction.add(Restrictions.eq("creater.id", id));
		disjunction.add(Restrictions.eq("repairName.id", id));
		if(team!=null)
		disjunction.add(Restrictions.eq("team.id", team));
		detachedCriteria.add(disjunction);
		
		//detachedCriteria.add(Restrictions.or(Restrictions.eq("repairName.id", id), Restrictions.eq("creater.id", id)));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Craft craft=super.load(id);
			craft.setIsDel(oper);//标记删除
			super.update(craft);
		}
   }

	@Override
	public Pager findByPager(Pager pager, HashMap<String, String> map,String id) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Craft.class);		
		
        pagerSqlByjqGrid(pager,detachedCriteria);
		
		if(!super.existAlias(detachedCriteria, "products", "products")){
			detachedCriteria.createAlias("products", "products");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "team", "team")){
			detachedCriteria.createAlias("team", "team");//表名，别名*/							
		}
		if(!super.existAlias(detachedCriteria, "abnormal", "abnormal")){
		detachedCriteria.createAlias("abnormal", "abnormal");						
	    }
		
		if(!super.existAlias(detachedCriteria, "repairName", "repairName")){
			detachedCriteria.createAlias("repairName", "repairName");//表名，别名*/							
		}
		
        if (map.size() > 0) {			
			
			if(map.get("repair")!=null){
			    detachedCriteria.add(Restrictions.like("repairName.name", "%"+map.get("repair")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("products.productsName", "%"+map.get("productName")+"%"));
			}			
		}
		detachedCriteria.add(Restrictions.eq("abnormal.id", id));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
			
}
