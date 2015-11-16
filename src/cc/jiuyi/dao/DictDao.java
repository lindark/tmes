package cc.jiuyi.dao;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
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
	
	public Pager getDictPager(Pager pager,Map map);
	
	/**
	 * 
	 * 根据dictName=dictName,keyValue=keyValue取出描述
	 */
	public String getDictValueByDictKey(Object dictname, Object dictkey);

	//获取dict的html标签,状态
	public List<Dict> getSate(String dictname);
}