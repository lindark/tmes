package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Department;

/**
 * Service接口 - 部门
 */

public interface DepartmentService extends BaseService<Department, String> {
	/**
	 * 标记删除
	 * @param ids
	 * @param oper
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 查询所有，查询标记未删除的数据,已启用的
	 * @return
	 */
	public List<Department> getAllByHql(String id);

	/**
	 * ajlist查询所有
	 * @param pager
	 * @return
	 */
	public Pager getAllDept(Pager pager,String deptid);

	/**
	 * 查询所有未删除的部门
	 * @return
	 */
	public List<Department> getAllDept();

	/**
	 * 根据部门编码查询部门
	 * @param deptcode
	 * @return
	 */
	public Department getByCode(String deptcode);

	/**
	 * 新增部门
	 * @param department
	 * @param loginid
	 */
	public void saveInfo(Department department, String loginid);

	/**
	 * 修改部门
	 * @param department
	 */
	public void updateInfo(Department department);
	

}