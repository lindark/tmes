package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 管理员
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
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
	public boolean isExistByCardNumber(String cardNumber) {
		String hql = "from Admin admin where lower(admin.cardNumber) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, cardNumber).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
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
		//String hql="from Admin a inner join fetch a.department b inner join fetch b.team c where c.id=? or a.isdaiban=? order by a.modifyDate desc";
		String hql="from Admin where department.team.id=? or isdaiban=? order by modifyDate desc";
		return this.getSession().createQuery(hql).setParameter(0, tid).setParameter(1, tid).list();
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
		detachedCriteria.add(Restrictions.ne("team.id", admin.getDepartment().getTeam().getId()));
		detachedCriteria.add(Restrictions.ne("isdaiban", admin.getDepartment().getTeam().getId()));
		detachedCriteria.add(Restrictions.eq("workstate", "1"));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	/**
	 * 根据卡号查询员工
	 */
	public Admin getByCardnum(String cardNumber)
	{
		String hql="from Admin where cardNumber=?";
		return (Admin) this.getSession().createQuery(hql).setParameter(0, cardNumber).uniqueResult();
	}
	
	
	/**
	 * 根据员工部门id获取部长
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getByAdminId(String id)
	{
		String hql="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		List<Admin> adminList1=getSession().createQuery(hql).setParameter(0, id).setParameter(1, "ROLE_BZ").list();
		if(adminList1==null){
			getParentById(id,null);
		}
		return adminList1;
	}
	
	
	/**
	 * 递归获取部长
	 */
	public List<Admin> getParentById(String id,List<Admin> temp){
		if (temp == null) {
			temp = new ArrayList<Admin>();
		}
		String hql = "from  Department where childDept.id=?";	
		Department  dept = (Department) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		String hql1="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		temp=getSession().createQuery(hql1).setParameter(0, id).setParameter(1, "ROLE_BZ").list();
		
		if(temp==null){
			getParentById(dept.getId(),temp);
		}
		return temp;
	}
	
	/**
	 * 根据卡号 班组ID查询
	 */
	public Admin getByCardnumAndTeamid(String cardNumber,String teamid)
	{
		String hql="from Admin a inner join fetch a.department b inner join fetch b.team c where a.cardNumber=? and (c.id=? or a.isdaiban=?)";
		return (Admin) this.getSession().createQuery(hql).setParameter(0, cardNumber).setParameter(1, teamid).setParameter(2, teamid).uniqueResult();
	}
	
	
	/**
	 * 根据员工部门id获取副总
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getManagerByDeptId(String id)
	{
		String hql="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		List<Admin> adminList1=getSession().createQuery(hql).setParameter(0, id).setParameter(1, "ROLE_FZ").list();
		if(adminList1==null){
			getParentById1(id,null);
		}
		return adminList1;
	}
	
	
	/**
	 * 递归获取副总
	 */
	public List<Admin> getParentById1(String id,List<Admin> temp){
		if (temp == null) {
			temp = new ArrayList<Admin>();
		}
		String hql = "from  Department where childDept.id=?";	
		Department  dept = (Department) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		String hql1="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		temp=getSession().createQuery(hql1).setParameter(0, id).setParameter(1, "ROLE_FZ").list();
		
		if(temp==null){
			getParentById1(dept.getId(),temp);
		}
		return temp;
	}
	
	
	
	/**
	 * 根据员工部门id获取主任
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getDirectorByDeptId(String id)
	{
		String hql="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		List<Admin> adminList1=getSession().createQuery(hql).setParameter(0, id).setParameter(1, "ROLE_ZR").list();
		if(adminList1==null){
			getParentById2(id,null);
		}
		return adminList1;
	}
	
	
	/**
	 * 递归获取主任
	 */
	public List<Admin> getParentById2(String id,List<Admin> temp){
		if (temp == null) {
			temp = new ArrayList<Admin>();
		}
		String hql = "from  Department where childDept.id=?";	
		Department  dept = (Department) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		String hql1="select a from Admin a join a.department b join a.roleSet c where b.id=? and c.value=?";
		temp=getSession().createQuery(hql1).setParameter(0, id).setParameter(1, "ROLE_ZR").list();
		
		if(temp==null){
			getParentById2(dept.getId(),temp);
		}
		return temp;
	}
	
	/**
	 * 根据id获取直接上级
	 * 
	 */
	public Admin getAdminById(String id){
		
		String hql="select b from Admin a join a.parentAdmin b where a.id=?";
		return (Admin) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	/**
	 * 查询本班组的员工及在本班代班的员工
	 */
	public Pager getEmpAjlist(Pager pager, String tid)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
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
		detachedCriteria.add(Restrictions.or(Restrictions.eq("team.id", tid),Restrictions.eq("isdaiban", tid)));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
}