package cc.jiuyi.service;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;

/**
 * Service接口 - 
 */

public interface AccessFunctionService extends BaseService<AccessFunction, String> {
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public AccessFunction get(String[] propertyNames, Object[] propertyValues);
	
	public void update(AccessFunction entity);
	
	/**
	 * 根据路径 + 角色清单 获取 AccessFunction,AccessObject 对象集合
	 * @param path 路径
	 * @param roleList  角色ID List
	 * @return
	 */
	public List<Object[]> getAccessFunctionList(String path,List<String> roleList);
}