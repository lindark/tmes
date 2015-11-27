package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.IpRecord;

/**
 * Dao接口
 * 半成品巡检缺陷记录表
 */
public interface IpRecordDao extends BaseDao<IpRecord, String> {
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
