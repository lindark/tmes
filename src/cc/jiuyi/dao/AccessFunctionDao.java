package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;



/**
 * Dao接口 - 
 */

public interface AccessFunctionDao extends BaseDao<AccessFunction, String> {
	/**
	 * 根据路径 + 角色清单 获取 AccessFunction,AccessObject 对象集合
	 * @param path 路径
	 * @param roleList  角色ID List
	 * @return
	 */
	public List<Object[]> getAccessFunctionList(String path,List<String> roleList);
}