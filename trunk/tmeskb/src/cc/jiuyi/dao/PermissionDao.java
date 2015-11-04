package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Permission;

/**
 * Dao接口 -权限管理
 * 
 *
 */

public interface PermissionDao extends BaseDao<Permission,String> {
	
	
	/**
	 * 取出所有权限对象
	 * @return
	 */
	public List<Permission> getPermissionList();
	
	public Pager getPermissionPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
