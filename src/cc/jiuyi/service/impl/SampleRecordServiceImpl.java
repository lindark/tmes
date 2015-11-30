package cc.jiuyi.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.SampleDao;
import cc.jiuyi.dao.SampleRecordDao;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.service.SampleRecordService;

/**
 * 抽检单缺陷记录
 * @author gaoyf
 *
 */
@Repository
public class SampleRecordServiceImpl extends BaseServiceImpl<SampleRecord, String> implements SampleRecordService
{
	@Resource
	private SampleRecordDao srDao;
	@Resource
	public void setBaseDao(SampleRecordDao sampleRcordDao) {
		super.setBaseDao(sampleRcordDao);
	}
	
	/**
	 * 根据抽检id获取缺陷记录
	 */
	@Override
	public List<SampleRecord> getBySampleId(String id)
	{
		return this.srDao.getBySampleId(id);
	}
	
}
