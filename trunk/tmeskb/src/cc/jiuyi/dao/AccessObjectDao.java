package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;



/**
 * Dao接口 - 权限对象
 */

public interface AccessObjectDao extends BaseDao<AccessObject, String> {
	/**
	 * 根据 type 的值获取list 集合
	 * @param value
	 * @return
	 */
	public List<AccessObject> findTypeList(String value);
	
	/**
	 * 根据Resource的 id集合 获取资源对象
	 * @return
	 */
	public List<AccessObject> getAccessObjectList(String path,Object[] rolelist);
	
	/**
	 * 根据条件分页查询
	 * @param pager
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public Pager findByPager(Pager pager,String[] propertyName,Object[] propertyValue);
	
	/**
	 * 根据accessResourceid 获取AccessObject对象
	 * @param accessResourceid
	 * @return
	 */
	public List<AccessObject> getAccessObjectList(String accessResourceid);
}