package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.PollingtestRecord;
import cc.jiuyi.entity.SampleRecord;

/**
 * Dao接口 巡检从表
 */
public interface PollingtestRecordDao extends
		BaseDao<PollingtestRecord, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);

	/**
	 * 根据巡检单id获取巡检从表
	 * 
	 * @param id
	 *            巡检单id
	 * @return
	 */
	public List<PollingtestRecord> findByPollingtestId(String id);

	/**
	 * 根据巡检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
	 */
	PollingtestRecord getBySidAndCid(String sid, String cid);
	/**
	 * 根据巡检单id和标记查询该缺陷记录
	 */
	List<PollingtestRecord> getBySidAndMark(String id, String string);

}
