package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.WorkShop;

/**
 * Dao接口 车间管理
 * 
 *
 */

public interface WorkShopDao extends BaseDao<WorkShop,String> {
	
	
	/**
	 * 取出所有车间对象
	 * @return
	 */
	public List<WorkShop> getWorkShopList();
	
	public Pager getWorkShopPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
