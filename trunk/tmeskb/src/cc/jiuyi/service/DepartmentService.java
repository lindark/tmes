package cc.jiuyi.service;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.WorkingBill;

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
	 * 查询所有，查询标记未删除的数据
	 * @return
	 */
	public List getAllByHql();
}