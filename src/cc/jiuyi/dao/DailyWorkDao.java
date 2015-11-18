package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DailyWork;

/**
 * Dao接口 - 报工
 */

public interface DailyWorkDao extends BaseDao<DailyWork, String> {

	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String,String> map, String workingbillId);

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

}