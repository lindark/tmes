package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;






import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryUnitDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Team;

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
            	if(!super.existAlias(detachedCriteria, "workShop", "workShop"))
        			detachedCriteria.createAlias("workShop", "workShop");//表名，别名
				//	detachedCriteria.add(Restrictions.in("factory.factoryName", new Object[]{factoryName}));
				detachedCriteria.add(Restrictions.like("workShop.workShopName", "%"+map.get("workShopName")+"%"));
			}
            if(map.get("factoryName")!=null){
            	if(!super.existAlias(detachedCriteria, "workShop", "workShop"))
        			detachedCriteria.createAlias("workShop", "workShop");//表名，别名
            	if(!super.existAlias(detachedCriteria, "workShop.factory", "factory"))
        			detachedCriteria.createAlias("workShop.factory", "factory");
				//	detachedCriteria.add(Restrictions.in("factory.factoryName", new Object[]{factoryName}));
				detachedCriteria.add(Restrictions.like("factory.factoryName", "%"+map.get("factoryName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			FactoryUnit factoryUnit=super.load(id);
			factoryUnit.setIsDel(oper);//标记删除
			factoryUnit.setState("2");
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

	/**
	 * 分页条件查询单元
	 */
	@Override
	public Pager getFuPager(Pager pager, HashMap<String, String> map)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FactoryUnit.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) 
		{
			if(map.get("factoryUnitCode")!=null)
			{
				detachedCriteria.add(Restrictions.like("factoryUnitCode", "%"+map.get("factoryUnitCode")+"%"));
			}		
			if(map.get("factoryUnitName")!=null)
			{
				detachedCriteria.add(Restrictions.like("factoryUnitName", "%"+map.get("factoryUnitName")+"%"));
			}
		    if(map.get("workShopName")!=null)
		    {
		    	if(!super.existAlias(detachedCriteria, "workShop", "workShop"))
				{
					detachedCriteria.createAlias("workShop", "workShop");//表名，别名
				}
				detachedCriteria.add(Restrictions.like("workShop.workShopName", "%"+map.get("workShopName")+"%"));
			}
		    if(map.get("factoryName")!=null)
		    {
		    	if(!super.existAlias(detachedCriteria, "workShop", "workShop"))
				{
					detachedCriteria.createAlias("workShop", "workShop");//表名，别名
				}
		    	if(!super.existAlias(detachedCriteria, "workShop.factory", "factory"))
				{
					detachedCriteria.createAlias("workShop.factory", "factory");
				}
				detachedCriteria.add(Restrictions.like("factory.factoryName", "%"+map.get("factoryName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> getAllProducts() {
		String hql = "from Products as a where a.isDel='N'";
		return getSession().createQuery(hql).list();
	}

	/**
	 *  获取单元中的成本中心
	 */
	public Pager getCostCenter(Pager pager,String type)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FactoryUnit.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(type!=null)
		{
			detachedCriteria.add(Restrictions.eq("CXORJC", type));//成型/挤出
		}
		detachedCriteria.add(Restrictions.eq("iscanrepair", "Y"));//可以返修/返修收货
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public FactoryUnit getUnitByWorkCenter(String workCenter) {
		// TODO Auto-generated method stub
		String hql = "from FactoryUnit where workCenter =?";		
		return (FactoryUnit) getSession().createQuery(hql).setParameter(0, workCenter).uniqueResult();
	}

	@Override
	public Pager getAllList(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FactoryUnit.class);
		//pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0)
		{
			//单元编码
			if(map.get("factoryUnitCode")!=null&&!"".equals(map.get("factoryUnitCode")))
			{
				detachedCriteria.add(Restrictions.like("factoryUnitCode", "%"+map.get("teamName")+"%"));
			}
			//单元名称
			if(map.get("factoryUnitName")!=null&&!"".equals(map.get("factoryUnitName")))
			{
				detachedCriteria.add(Restrictions.like("factoryUnitName", "%"+map.get("factoryUnitName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("state", "1"));//已启用的
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public FactoryUnit get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from FactoryUnit as model where model."
				+ propertyName + " = ? and isDel=?";
		return (FactoryUnit) getSession().createQuery(hql).setParameter(0, value).setParameter(1, "N").uniqueResult();
	}
}