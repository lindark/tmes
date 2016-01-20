package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ScrapOut;

/**
 * Dao接口 -报废产出对照表
 * 
 *
 */

public interface ScrapOutDao extends BaseDao<ScrapOut,String> {
	
	/**
	 * 取出所有未删除的报废产出对象
	 * @return
	 */
	public List<ScrapOut> getExistScrapOutList();
	
	
	public Pager getScrapOutPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	
}
