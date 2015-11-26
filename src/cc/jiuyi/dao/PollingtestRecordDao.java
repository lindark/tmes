package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.PollingtestRecord;

/**
 * Dao接口
 * 巡检从表
 */
public interface PollingtestRecordDao extends BaseDao<PollingtestRecord, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);
	
}
