package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Permission;

/**
 * Service接口 - 权限管理
 */

public interface PermissionService extends BaseService<Permission, String> {

	/**
	 * 取出所有Permission对象
	 * 
	 * @return
	 */
	public List<Permission> getPermissionList();

	public Pager getPermissionPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}