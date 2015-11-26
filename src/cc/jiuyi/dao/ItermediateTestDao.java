package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ItermediateTest;;

/**
 * Dao接口 -半成品巡检主表Dao接口
 * 
 *
 */

public interface ItermediateTestDao extends BaseDao<ItermediateTest,String> {
	
	
	/**
	 * 取出所有半成品巡检对象
	 * @return
	 */
	public List<ItermediateTest> getItermediateTestList();
	
	public Pager getItermediateTestPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	
}
