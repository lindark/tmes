package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.PollingtestRecord;

/**
 * Service接口 巡检从表
 */
public interface PollingtestRecordService extends BaseService<PollingtestRecord, String> {
	/**
	 * 根据巡检单id获取巡检从表
	 * @param id 巡检单id
	 * @return
	 */
	public List<PollingtestRecord> findByPollingtestId(String id);
}
