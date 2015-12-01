package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EquipmentDao;
import cc.jiuyi.entity.Equipments;
import cc.jiuyi.entity.Factory;

/**
 * Dao实现类 - 设备
 */

@Repository
public class EquipmentDaoImpl extends BaseDaoImpl<Equipments, String> implements EquipmentDao {

	public Pager getEquipmentPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Equipments.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("deviceNo")!=null){
			    detachedCriteria.add(Restrictions.like("equipmentNo", "%"+map.get("deviceNo")+"%"));
			}		
			if(map.get("deviceName")!=null){
				detachedCriteria.add(Restrictions.like("equipmentName", "%"+map.get("deviceName")+"%"));
			}			
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Equipments equipment=super.load(id);
			equipment.setIsDel(oper);//标记删除
			super.update(equipment);
		}
   }

	@SuppressWarnings("unchecked")
	public boolean isExistByEquipmentNo(String equipmentNo) {
		String hql = "from Equipment equipment where lower(equipment.equipmentNo) = lower(?)";
		Equipments equipment = (Equipments) getSession().createQuery(hql).setParameter(0,equipmentNo).uniqueResult();
		if (equipment != null) {
			return true;
		} else {
			return false;
		}
	}
}
