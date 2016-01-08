package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Resources;
import cc.jiuyi.entity.Role;

/**
 * Service接口 - 角色
 */

public interface RoleService extends BaseService<Role, String> {

	public String getRoleSetString(String resourcesid);
}