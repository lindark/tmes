package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.SampleRecord;

public interface SampleRecordDao extends BaseDao<SampleRecord, String>
{
	/**
	 * 根据抽检id获取缺陷记录
	 */
	List<SampleRecord> getBySampleId(String id);

	/**
	 * 根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
	 */
	SampleRecord getBySidAndCid(String sid, String cid);

	/**
	 * 根据抽检单id和标记查询该缺陷记录
	 */
	List<SampleRecord> getBySidAndMark(String id, String string);

}
