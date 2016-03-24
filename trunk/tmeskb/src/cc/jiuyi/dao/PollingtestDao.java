package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Pollingtest;

/**
 * Dao接口 巡检
 */
public interface PollingtestDao extends BaseDao<Pollingtest, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);
	
	
	/**
	 * 取出所有未确认的巡检单
	 * @return
	 */
	public List<Pollingtest> getUncheckList();

	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
