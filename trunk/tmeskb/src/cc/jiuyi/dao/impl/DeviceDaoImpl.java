package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DeviceDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.Quality;

/**
 * Dao实现类 - 设备
 */

@Repository
public class DeviceDaoImpl extends BaseDaoImpl<Device, String> implements DeviceDao {

	public Pager getDevicePager(Pager pager, HashMap<String, String> map,String id,String team) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Device.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if(!super.existAlias(detachedCriteria, "workShop", "workShop")){
			detachedCriteria.createAlias("workShop", "workShop");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "team", "team")){
			detachedCriteria.createAlias("team", "team");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "disposalWorkers", "disposalWorkers")){
			detachedCriteria.createAlias("disposalWorkers", "disposalWorkers");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "workshopLinkman", "workshopLinkman")){
			detachedCriteria.createAlias("workshopLinkman", "workshopLinkman");//表名，别名*/							
		}

		if (map.size() > 0) {			
			
			if(map.get("workShopName1")!=null){
			    detachedCriteria.add(Restrictions.like("workShop.workShopName", "%"+map.get("workShopName1")+"%"));
			}
			
			if(map.get("repairPerson")!=null){
			    detachedCriteria.add(Restrictions.like("disposalWorkers.name", "%"+map.get("repairPerson")+"%"));
			}		
		}	
		
		Disjunction disjunction = Restrictions.disjunction();  
		disjunction.add(Restrictions.eq("workshopLinkman.id", id));
		disjunction.add(Restrictions.eq("disposalWorkers.id", id));
		disjunction.add(Restrictions.eq("team.id", team));
		detachedCriteria.add(disjunction);
		
	//	detachedCriteria.add(Restrictions.or(Restrictions.eq("workshopLinkman.id", id), Restrictions.eq("disposalWorkers.id", id)));

		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Device device=super.load(id);
			device.setIsDel(oper);//标记删除
			super.update(device);
		}
   }

	@Override
	public Pager findByPager(Pager pager, HashMap<String, String> map,String id) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Device.class);	
		
        pagerSqlByjqGrid(pager,detachedCriteria);
		
		if(!super.existAlias(detachedCriteria, "workShop", "workShop")){
			detachedCriteria.createAlias("workShop", "workShop");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "disposalWorkers", "disposalWorkers")){
			detachedCriteria.createAlias("disposalWorkers", "disposalWorkers");//表名，别名*/							
		}
		if(!super.existAlias(detachedCriteria, "abnormal", "abnormal")){
		detachedCriteria.createAlias("abnormal", "abnormal");						
	    }
		
        if (map.size() > 0) {			
			
			if(map.get("workShopName1")!=null){
			    detachedCriteria.add(Restrictions.like("workShop.workShopName", "%"+map.get("workShopName1")+"%"));
			}
			
			if(map.get("repairPerson")!=null){
			    detachedCriteria.add(Restrictions.like("disposalWorkers.name", "%"+map.get("repairPerson")+"%"));
			}		
		}	
		detachedCriteria.add(Restrictions.eq("abnormal.id", id));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

}
