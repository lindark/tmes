package cc.jiuyi.service;
import java.util.List;

import cc.jiuyi.entity.SampleRecord;

/**
 * 抽检
 * @author gaoyf
 *
 */
public interface SampleRecordService extends BaseService<SampleRecord, String>
{

	/**
	 * 根据抽检id获取缺陷记录
	 */
	public List<SampleRecord> getBySampleId(String info);

	/**
	 * 根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
	 * @param id
	 * @param string
	 * @return
	 */
	public SampleRecord getBySidAndCid(String sid, String cid);

	/**根据抽检单id和标记查询该缺陷记录*/
	public List<SampleRecord> getBySidAndMark(String sid, String mark);

}
