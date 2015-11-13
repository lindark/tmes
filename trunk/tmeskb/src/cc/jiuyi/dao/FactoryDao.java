package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Factory;

/**
 * Dao接口 -工厂
 * 
 *
 */

public interface FactoryDao extends BaseDao<Factory,String> {
	
	
	/**
	 * 取出所有工厂对象
	 * @return
	 */
	public List<Factory> getFactoryList();
	
	public Pager getFactoryPager(Pager pager,HashMap<String,String>map);
		
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public boolean isExistByFactoryCode(String factoryCode);
}
