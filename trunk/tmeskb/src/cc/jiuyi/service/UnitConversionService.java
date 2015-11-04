package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.UnitConversion;

/**
 * Service接口 - 单位转换
 */

public interface UnitConversionService extends BaseService<UnitConversion, String> {

	/**
	 * 取出所有UnitConversion对象
	 * 
	 * @return
	 */
	public List<UnitConversion> getUnitConversionList();

	public Pager getUnitConversionPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}