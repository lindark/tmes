package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Products;

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
	/**
	 * 获取team数据,已启用的
	 */
	public Pager getAllList(Pager pager, HashMap<String, String> map);
}
