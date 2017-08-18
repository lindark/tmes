package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.RepairDetail;

/**
 * Service接口 - 
 */

public interface RepairDetailService extends BaseService<RepairDetail, String>{


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