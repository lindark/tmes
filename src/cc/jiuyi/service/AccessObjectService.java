package cc.jiuyi.service;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;

/**
 * Service接口 - 权限对象
 */

public interface AccessObjectService extends BaseService<AccessObject, String> {
	/**
	 * 根据 type 获取 list 集合
	 * @param value
	 * @return
	 */
	public List<AccessObject> findTypeList(String value);
	
	/**
	 * 根据Resource的 id集合 获取资源对象
	 * @return
	 */
	public List<AccessObject> getAccessObjectList(String path,List<Role> roleList);
}