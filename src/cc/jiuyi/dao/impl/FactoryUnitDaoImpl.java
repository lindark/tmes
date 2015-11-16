package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.FactoryUnitDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.WorkShop;

/**
 * Dao实现类 - factoryUnit
 */

@Repository
public class FactoryUnitDaoImpl extends BaseDaoImpl<FactoryUnit, String> implements
		FactoryUnitDao {

	@Override
	public void delete(String id) {
		FactoryUnit factoryUnit = load(id);
		this.delete(factoryUnit);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FactoryUnit> getFactoryUnitList() {
		String hql = "From FactoryUnit factoryUnit order by factoryUnit.id asc factoryUnit.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getFactoryUnitPager(Pager pager, HashMap<String, String> map) {
		//String wheresql = factoryUnitpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(FactoryUnit.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "workshop", "workshop"))
			detachedCriteria.createAlias("workshop", "workshop");//表名，别名
		/*
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}*/
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("factoryUnitCode")!=null){
			    detachedCriteria.add(Restrictions.like("factoryUnitCode", "%"+map.get("factoryUnitCode")+"%"));
			}		
			if(map.get("factoryUnitName")!=null){
				detachedCriteria.add(Restrictions.like("factoryUnitName", "%"+map.get("factoryUnitName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
            if(map.get("workShopName")!=null){
				
				//	detachedCriteria.add(Restrictions.in("factory.factoryName", new Object[]{factoryName}));
				detachedCriteria.add(Restrictions.like("workshop.workShopName", "%"+map.get("workShopName")+"%"));
			}
            if(map.get("factoryName")!=null){
				
				//	detachedCriteria.add(Restrictions.in("factory.factoryName", new Object[]{factoryName}));
				detachedCriteria.add(Restrictions.like("workshop.factory.factoryName", "%"+map.get("factoryName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	/*
	public String factoryUnitpagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp(), null) + " ";
				ishead = 1;
			}

		}
		wheresql=wheresql.replace("state='启用'", "state='1'");
		wheresql=wheresql.replace("state='未启用'", "state='2'");
		return wheresql;
	}*/

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			FactoryUnit factoryUnit=super.load(id);
			factoryUnit.setIsDel(oper);//标记删除
			super.update(factoryUnit);
		}
		
	}

	@Override
	public boolean isExistByFactoryUnitCode(String factoryUnitCode) {
		String hql = "from FactoryUnit factoryUnit where lower(factoryUnit.factoryUnitCode) = lower(?)";
		FactoryUnit factoryUnit = (FactoryUnit) getSession().createQuery(hql).setParameter(0, factoryUnitCode).uniqueResult();
		if (factoryUnit != null) {
			return true;
		} else {
			return false;
		}
	}
}