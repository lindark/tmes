package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Department;

/**
 * Dao实现类 - 随工单
 */

@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department, String> implements DepartmentDao {
	
	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			Department department = super.load(id);
			department.setIsDel(oper);
			super.update(department);
		}
	}

	/**
	 * 查询所有，查询标记未删除的数据,已启用的
	 */
	@SuppressWarnings("unchecked")
	public List<Department> getAllByHql(List<String>list_str) {
		/*String hql="";
		if(list_str.size()>0)
		{
			hql ="from Department where isDel = 'N' and isWork='Y' and id not in (?)";
			return getSession().createQuery(hql).setParameter(0, list_str).list();
		}
		else
		{
			hql ="from Department where isDel = 'N' and isWork='Y'";
			return getSession().createQuery(hql).list();
		}*/
		Pager pager=new Pager();
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Department.class);
		if(list_str.size()>0)
		{
			detachedCriteria.add(Restrictions.not(Restrictions.in("id", list_str)));
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("isWork", "Y"));
		return super.findByPager(pager,detachedCriteria).getList();
	}
	public List getAllByHql() {
		String hql ="from Department where isDel = 'N'";
		return getSession().createQuery(hql).list();
	}
	/**
	 * 递归获得所有下机部门
	 */
	@SuppressWarnings("unchecked")
	public List<Department> getChildrenById(String id,List<Department> temp){
		if (temp == null) {
			temp = new ArrayList<Department>();
		}
		String hql = "from  Department where parentDept.id in (?)";
		List<Department>  deptList = getSession().createQuery(hql).setParameter(0, id).list();
		for(Department dept:deptList){
			temp.add(dept);
			getChildrenById(dept.getId(),temp);
		}
		return temp;
	}

	/**
	 * ajlist查询所有
	 */
	public Pager getAllDept(Pager pager,List<String>list_str)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Department.class);
		if(list_str.size()>0)
		{
			detachedCriteria.add(Restrictions.in("id", list_str));
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		return super.findByPager(pager,detachedCriteria);
	}

	/**
	 * 查询所有未删除的部门
	 */
	@SuppressWarnings("unchecked")
	public List<Department> getAllDept()
	{
		String hql="from Department where isDel='N'";
		return this.getSession().createQuery(hql).list();
	}

	/**
	 * 根据部门编码查询部门
	 */
	public Department getByCode(String deptcode)
	{
		String hql="from Department where deptCode=?";
		@SuppressWarnings("unchecked")
		List<Department>list=this.getSession().createQuery(hql).setParameter(0, deptcode).list();
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
}