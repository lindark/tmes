package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.WorkingBill;



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

	@Override
	public List getAllByHql() {
		String hql ="from Department where isDel = 'N'";
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * 递归获得所有下机部门
	 */
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

	
}