package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.Department;


/**
 * Dao接口 - 部门
 */

public interface DepartmentDao extends BaseDao<Department, String> {
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 查询全部，只查询标记为未删除的信息
	 */
	public List getAllByHql();
	
	public List<Department> getChildrenById(String id,List<Department> temp);
}