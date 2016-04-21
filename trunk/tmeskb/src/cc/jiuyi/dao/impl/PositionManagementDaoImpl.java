package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PositionManagementDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.PositionManagement;
@Repository
public class PositionManagementDaoImpl extends BaseDaoImpl<PositionManagement, String> implements PositionManagementDao{

	@SuppressWarnings("unchecked")
	public List<PositionManagement> getPositionManagementList() {
		String hql = "From PositionManagement positionManagement order by positionManagement.id asc positionManagement.crateDate desc where positionManagement.isDel='N'";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Pager getPositionManagementPager(Pager pager,HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PositionManagement.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!existAlias(detachedCriteria, "factoryUnit", "factoryUnit")){
			detachedCriteria.createAlias("factoryUnit", "factoryUnit");
		}
		if (map.size() > 0) {
			if (map.get("xfactoryUnitName") != null) {
				detachedCriteria.add(Restrictions.like("factoryUnit.factoryUnitName","%" + map.get("xfactoryUnitName") + "%"));
			}
		if(map.get("warehouse")!=null){
			detachedCriteria.add(Restrictions.like("warehouse", "%"+map.get("warehouse")+"%"));
		}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			PositionManagement xpositionManagement=super.load(id);
			xpositionManagement.setIsDel(oper);//标记删除
			super.update(xpositionManagement);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<String> getPositionList(PositionManagement positionManagement) {
		String hql="Select distinct supermarketWarehouse From PositionManagement positionManagement where positionManagement.factoryUnit=? and positionManagement.isDel='N'";
		return getSession().createQuery(hql).setParameter(0, positionManagement.getFactoryUnit()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPositionList1(String warehouse,PositionManagement positionManagement){
	//	String hql="Select distinct trimWareHouse from PositionManagement positionManagement where ((positionManagement.factoryUnit=?) and positionManagement.supermarketWarehouse=? )and positionManagement.isDel='N'";
		String hql="Select distinct trimWareHouse From PositionManagement positionManagement where (positionManagement.factoryUnit=? and positionManagement.isDel='N') and positionManagement.supermarketWarehouse=?";
		return getSession().createQuery(hql).setParameter(0, positionManagement.getFactoryUnit()).setParameter(1,warehouse).list();
	}

	
}
