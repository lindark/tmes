package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Role;


/**
 * Service接口 - 权限资源对象
 */

public interface AccessResourceService extends BaseService<AccessResource, String> {

	public List<AccessResource> findAccessByRoles(List<Role> rolelist);
}