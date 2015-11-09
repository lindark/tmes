package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Pick;

/**
 * Dao接口 -领/退料主表
 * 
 *
 */

public interface PickDao extends BaseDao<Pick,String> {
	
	
	/**
	 * 取出所有领退料对象
	 * @return
	 */
	public List<Pick> getPickList();
	
	public Pager getPickPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	
}
