package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Resources;
import cc.jiuyi.entity.Role;

/**
 * Service接口 - 资源
 */

public interface ResourcesService extends BaseService<Resources, String> {
	/**
	 * 根据角色清单和路径获取权限
	 * @param roleid 角色清单
	 * @param path 路径
	 * @return
	 */
	public Integer getListByadmin(List<String> roleid,String path);
	
}
