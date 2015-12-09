package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.Resource;
import cc.jiuyi.entity.Role;

/**
 * Dao接口 - 资源
 */

public interface ResourceDao extends BaseDao<Resource, String> {

	/**
	 * 根据角色清单和路径获取权限
	 * @param roleid 角色清单
	 * @param path 路径
	 * @return
	 */
	public Integer getListByadmin(List<String> roleid,String path);
}
