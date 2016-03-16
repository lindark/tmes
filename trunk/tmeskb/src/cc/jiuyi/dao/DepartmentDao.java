package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
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
	 * 查询所有，查询标记未删除的数据,已启用的
	 */
	public List<Department> getAllByHql(List<String>list_str);
	
	public List<Department> getChildrenById(String id,List<Department> temp);

	/**
	 * ajlist查询所有
	 * @param pager
	 * @return
	 */
	public Pager getAllDept(Pager pager,List<String>list_str);

	/**
	 * 查询所有未删除的部门
	 */
	public List<Department> getAllDept();

	/**
	 * 根据部门编码查询部门
	 */
	public Department getByCode(String deptcode);
	
	
}