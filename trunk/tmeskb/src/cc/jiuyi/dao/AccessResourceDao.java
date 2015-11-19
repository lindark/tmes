package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Role;




/**
 * Dao接口 - 权限资源对象
 */

public interface AccessResourceDao extends BaseDao<AccessResource, String> {
	
	/**
	 * 根据path路径 和人员名称 获得能够操作的权限对象
	 * @param path
	 * @return
	 */
	public List<AccessResource> findListBypath(String path,String loginid);
	
	public List<AccessResource> findAccessByRoles(Object[] roleids);
}