package cc.jiuyi.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.SampleRecordDao;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.service.SampleRecordService;

/**
 * 抽检单缺陷记录
 * @author gaoyf
 *
 */
@Service
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

	/**
	 * 根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
	 */
	public SampleRecord getBySidAndCid(String sid, String cid)
	{
		return this.srDao.getBySidAndCid(sid, cid);
	}

	/**根据抽检单id和标记查询该缺陷记录*/
	public List<SampleRecord> getBySidAndMark(String sid, String mark)
	{
		return this.srDao.getBySidAndMark(sid, mark);
	}
	
}
