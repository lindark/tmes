package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.RepairDetail;

/**
 * Dao接口 -
 * 
 *
 */

public interface RepairDetailDao extends BaseDao<RepairDetail,String> {
	
	
	/**
	 * 取出所有对象
	 * @return
	 */
	public List<RepairDetail> getPickList();
	
	/**
	 * 根据条件取出所有对象
	 */
	public List<RepairDetail> getPickList(String processids);
	
	public Pager getRepairDetailPager(Pager pager, HashMap<String, String> map);
	
}
