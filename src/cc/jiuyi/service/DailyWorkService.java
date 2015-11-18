package cc.jiuyi.service;

import java.util.HashMap;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DailyWork;

/**
 * Service接口 - 报工
 */

public interface DailyWorkService extends BaseService<DailyWork, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String,String> map, String workingbillId);

}