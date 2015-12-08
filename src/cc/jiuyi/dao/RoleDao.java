package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.Role;

/**
 * Dao接口 - 角色
 */

public interface RoleDao extends BaseDao<Role, String> {
	/**
	 * 根据resourceid 查询
	 * @param resourceid
	 * @return
	 */
	public List<Role> getList(String resourceid);
}