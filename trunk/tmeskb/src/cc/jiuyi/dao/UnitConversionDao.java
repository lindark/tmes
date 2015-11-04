package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.UnitConversion;

/**
 * Dao接口 - 单位转换
 * 
 *
 */

public interface UnitConversionDao extends BaseDao<UnitConversion,String> {
	
	
	/**
	 * 取出所有工序对象
	 * @return
	 */
	public List<UnitConversion> getUnitConversionList();
	
	public Pager getUnitConversionPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
