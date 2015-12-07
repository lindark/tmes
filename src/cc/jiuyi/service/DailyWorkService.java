package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.util.CustomerException;

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
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);

	public void updateState(List<DailyWork> list, String statu,
			String workingbillid) throws IOException, CustomerException;
}