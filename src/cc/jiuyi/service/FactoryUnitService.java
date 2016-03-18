package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Products;

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
	
	public boolean isExistByFactoryUnitCode(String factoryUnitCode);
	
	public List<Products> getAllProducts();

	/**
	 *  获取单元中的成本中心
	 * @param pager
	 * @return
	 */
	public Pager getCostCenter(Pager pager,String type);
	
	/**
	 * 根据工作中心取单元对象
	 */
	public FactoryUnit getUnitByWorkCenter(String workCenter);

	//根据ip获取单元
	public FactoryUnit getById(String ip);
	
	/**
	 * 获取team数据,已启用的
	 * @param pager
	 * @param map查询条件
	 * @return
	 */
	public Pager getAllList(Pager pager, HashMap<String, String> map);

}