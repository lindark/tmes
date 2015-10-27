package cc.jiuyi.dao;

import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;

/**
 * Dao接口 - 字典
 */

public interface DictDao extends BaseDao<Dict, String> {
	/**
	 * 根据dictname,dictkey获取dict对象
	 * 
	 */
	public Dict getDict(String dictname,String dictkey);
	public void ceshi();
}