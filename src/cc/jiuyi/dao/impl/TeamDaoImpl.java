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
import cc.jiuyi.dao.TeamDao;
import cc.jiuyi.entity.Team;

/**
 * Dao实现类 - Team
 */

@Repository
public class TeamDaoImpl extends BaseDaoImpl<Team, String> implements
		TeamDao {

	@Override
	public void delete(String id) {
		Team team = load(id);
		this.delete(team);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Team> getTeamList() {
		String hql = "From Team team order by team.id asc team.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getTeamPager(Pager pager, HashMap<String, String> map) 
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Team.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!existAlias(detachedCriteria, "factoryUnit", "factoryUnit"))
		{
			detachedCriteria.createAlias("factoryUnit", "factoryUnit");
		}
		if(!existAlias(detachedCriteria, "factoryUnit.workShop", "workShop"))
		{
			detachedCriteria.createAlias("factoryUnit.workShop", "workShop");
		}
		if(!existAlias(detachedCriteria, "factoryUnit.workShop.factory", "factory"))
		{
			detachedCriteria.createAlias("factoryUnit.workShop.factory", "factory");
		}
		if (map.size() > 0)
		{
			//班组编码
			if(map.get("teamCode")!=null&&!"".equals(map.get("teamCode")))
			{
			    detachedCriteria.add(Restrictions.like("teamCode", "%"+map.get("teamCode")+"%"));
			}
			//班组名称
			if(map.get("teamName")!=null&&!"".equals(map.get("teamName")))
			{
				detachedCriteria.add(Restrictions.like("teamName", "%"+map.get("teamName")+"%"));
			}
			//状态
			if(map.get("state")!=null&&!"".equals(map.get("state")))
			{
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			//单元名称
			if(map.get("xfactoryUnitName")!=null&&!"".equals(map.get("xfactoryUnitName")))
			{
				detachedCriteria.add(Restrictions.like("factoryUnit.factoryUnitName", "%"+map.get("xfactoryUnitName")+"%"));
			}
			//车间名称
			if(map.get("xworkShopName")!=null&&!"".equals(map.get("xworkShopName")))
			{
				detachedCriteria.add(Restrictions.like("workShop.workShopName", "%"+map.get("xworkShopName")+"%"));
			}
			//工厂名称
			if(map.get("xfactoryName")!=null&&!"".equals(map.get("xfactoryName")))
			{
				detachedCriteria.add(Restrictions.like("factory.factoryName", "%"+map.get("xfactoryName")+"%"));
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Team team=super.load(id);
			team.setIsDel(oper);//标记删除
			super.update(team);
		}
		
	}
}