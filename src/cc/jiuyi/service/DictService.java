package cc.jiuyi.service;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;

/**
 * Service接口 - 字典
 */

public interface DictService extends BaseService<Dict, String> {
	/**
	 * 根据dictname，dictkey更新相应的dictvalue;
	 * 
	 * @return void
	 * 
	 */
	public void updateDictValue(String dictname,String dictkey,String dictvalue);
	
	public Pager getDictPager(Pager pager,Map map);
	
	/**
	 * 根据dictName=dictName,keyValue=keyValue取出描述
	 *
	 */
	public String getDictValueByDictKey(Object dictName, Object keyValue);

	//获取dict的html标签,状态
	public List<Dict> getState(String dictname);
}