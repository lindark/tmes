package cc.jiuyi.service.impl;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.SampleDao;
import cc.jiuyi.dao.SampleRecordDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.service.SampleService;

/**
 * 抽检
 * @author gaoyf
 *
 */
@Repository
public class SampleServiceImpl extends BaseServiceImpl<Sample, String> implements SampleService
{
	@Resource
	private SampleDao sampleDao;
	@Resource
	private SampleRecordDao sampleRecordDao;
	@Resource
	private CauseDao causeDao;
	@Resource
	private AdminDao adminDao;

	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager)
	{
		return this.sampleDao.getSamplePager(pager);
	}

	/**
	 * 新增
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 */
	@Override
	public void saveInfo(Sample sample, String info, String info2, String my_id,Admin admin)
	{
		if(sample!=null)
		{
			sample.setSampler(admin);//抽检人
			sample.setState("1");
			if("2".equals(my_id))
			{
				sample.setComfirmation(admin);
				sample.setState("2");
			}
			String sampleId=this.sampleDao.save(sample);
			String[]qxids=info.split(",");//缺陷IDS
			String[]qxnums=info2.split(",");//缺陷nums
			Sample sample2=this.sampleDao.load(sampleId);
			for(int i=0;i<qxids.length;i++)
			{
				if(qxids[i]!=null&&!"".equals(qxids[i]))
				{
					SampleRecord sr=new SampleRecord();
					Cause cause=this.causeDao.get(qxids[i]);//根据缺陷ID查询
					sr.setCreateDate(new Date());//初始化创建日期
					sr.setModifyDate(new Date());//初始化修改日期
					sr.setSample(sample2);//抽检单对象
					sr.setRecordDescription(cause.getCauseName());
					sr.setRecordNum(qxnums[i]);//缺陷数量
					this.sampleRecordDao.save(sr);
				}
			}
		}
	}
}
