package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;

/**
 * Dao接口 - 单元
 * 
 *
 */

public interface FactoryUnitDao extends BaseDao<FactoryUnit,String> {
	
	
	/**
	 * 取出所有单元对象
	 * @return
	 */
	public List<FactoryUnit> getFactoryUnitList();
	
	public Pager getFactoryUnitPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public boolean isExistByFactoryUnitCode(String factoryUnitCode);

	/**
	 * 分页条件查询单元
	 */
	public Pager getFuPager(Pager pager, HashMap<String, String> map);
}
