package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 管理员
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map,List list){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "department", "department"))
			detachedCriteria.createAlias("department", "department");
		detachedCriteria.add(Restrictions.in("department.id", list));//取出未子部门
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	public Pager getAdminPager(Pager pager,Map map){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "department", "department"))
			detachedCriteria.createAlias("department", "department");
		if (map.size() > 0) {
			if(map.get("adminName")!=null){
			    detachedCriteria.add(Restrictions.like("name", "%"+map.get("adminName")+"%"));
			}		
			if(map.get("departid")!=null){
				detachedCriteria.add(Restrictions.like("department.id", "%"+map.get("departid")+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	/**
	 * 根据班组ID获取该班组下的所有员工
	 */
	@SuppressWarnings("unchecked")
	public List<Admin>getByTeamId(String tid)
	{
		String hql="from Admin a inner join fetch a.department b inner join fetch b.team c where c.id=?";
		return this.getSession().createQuery(hql).setParameter(0, tid).list();
	}

	/**
	 * jqgrid分页条件查询--考勤
	 */
	public Pager getEmpPager(Pager pager, HashMap<String, String> map,Admin admin)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		//技能
		if(!super.existAlias(detachedCriteria, "post", "post"))
		{
			detachedCriteria.createAlias("post", "post");
		}
		//部门
		if(!super.existAlias(detachedCriteria, "department", "department"))
		{
			detachedCriteria.createAlias("department", "department");
		}
		//班组
		if(!super.existAlias(detachedCriteria, "department.team", "team"))
		{
			detachedCriteria.createAlias("department.team", "team");
		}
		if(map.size()>0)
		{
			//班组
			if(map.get("team")!=null)
			{
			    detachedCriteria.add(Restrictions.like("team.teamName","%"+ map.get("team")+"%"));
			}
			//班次
			if(map.get("shift")!=null)
			{
				detachedCriteria.add(Restrictions.eq("shift", map.get("shift")));
			}
			//姓名
			if(map.get("name")!=null)
			{
				detachedCriteria.add(Restrictions.like("name", "%"+map.get("name")+"%"));
			}
			//技能
			if(map.get("skill")!=null)
			{
				detachedCriteria.add(Restrictions.like("post.postName", "%"+map.get("skill")+"%"));
			}
		}
		detachedCriteria.add(Restrictions.ne("team.teamName", admin.getDepartment().getTeam().getTeamName()));
		detachedCriteria.add(Restrictions.eq("workstate", "1"));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	/**
	 * 根据员工卡号查询员工
	 */
	public Admin getByCardnum(String cardNum)
	{
		String hql="from Admin where cardNumber=?";
		return (Admin) this.getSession().createQuery(hql).setParameter(0, cardNum).uniqueResult();
	}
}