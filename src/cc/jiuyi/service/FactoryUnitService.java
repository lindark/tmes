package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;

/**
 * Service接口 - 单元管理
 */

public interface FactoryUnitService extends BaseService<FactoryUnit, String> {

	/**
	 * 取出所有FactoryUnit对象
	 * 
	 * @return
	 */
	public List<FactoryUnit> getFactoryUnitList();

	public Pager getFactoryUnitPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}