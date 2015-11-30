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
	List<SampleRecord> getBySampleId(String info);

}
