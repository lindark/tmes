package cc.jiuyi.service;

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
	
	public Pager getDictPager(Pager pager);
	
	
}