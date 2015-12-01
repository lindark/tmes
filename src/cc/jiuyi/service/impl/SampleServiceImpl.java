package cc.jiuyi.service.impl;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.SampleDao;
import cc.jiuyi.dao.SampleRecordDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.service.AdminService;
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
	private AdminService adminService;
	@Resource
	public void setBaseDao(SampleDao sampleDao) {
		super.setBaseDao(sampleDao);
	}
	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager,String wbId)
	{
		return this.sampleDao.getSamplePager(pager,wbId);
	}

	/**
	 * 新增
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 */
	@Override
	public void saveInfo(Sample sample, String info, String info2, String my_id)
	{
		Admin admin=this.adminService.getLoginAdmin();
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
			if(qxids.length>0)
			{
				for(int i=0;i<qxids.length;i++)
				{
					SampleRecord sr=new SampleRecord();
					Cause cause=this.causeDao.get(qxids[i]);//根据缺陷ID查询
					sr.setCreateDate(new Date());//初始化创建日期
					sr.setModifyDate(new Date());//初始化修改日期
					sr.setSampleId(sampleId);//抽检单对象
					sr.setRecordDescription(cause.getCauseName());
					sr.setRecordNum(qxnums[i]);//缺陷数量
					sr.setCauseId(qxids[i]);//缺陷ID
					this.sampleRecordDao.save(sr);
				}
			}
		}
	}

	/**
	 * 确认/撤销
	 */
	@Override
	public void updateState(List<Sample> list, String newstate)
	{
		Admin admin=this.adminService.getLoginAdmin();
		for(int i=0;i<list.size();i++)
		{
			Sample sample=list.get(i);
			sample.setState(newstate);
			sample.setComfirmation(admin);
			this.sampleDao.update(sample);
		}
	}
	
	/**
	 * 
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 * a.根据抽检单id查询缺陷记录表
	 * b.根据抽检单id和缺陷表id查询缺陷记录表存在更新，不存在新增
	 * 删除操作：a中有和数组中不匹配的则假删除
	 */
	public void updateInfo(Sample sample, String info, String info2, String my_id)
	{
		Admin admin=this.adminService.getLoginAdmin();
		Sample s=this.sampleDao.load(sample.getId());
		BeanUtils.copyProperties(sample, s, new String[] { "id" });// 除了id不修改，其他都修改，自动完成设值操作
		s.setSampler(admin);//抽检人
		s.setState("1");
		if("2".equals(my_id))
		{
			s.setComfirmation(admin);
			s.setState("2");
		}
		s.setModifyDate(new Date());
		this.sampleDao.update(s);//修改操作
		/**缺陷记录更新*/
		String[]qxids=info.split(",");//缺陷IDS
		String[]qxnums=info2.split(",");//缺陷nums
		if(qxids.length>0)
		{
			for(int i=0;i<qxids.length;i++)
			{
				Cause cause=this.causeDao.get(qxids[i]);//根据缺陷ID查询
				//根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
				SampleRecord sr1=this.sampleRecordDao.getBySidAndCid(sample.getId(),qxids[i]);
				if(sr1!=null)
				{
					//if(qxnums[i]==null)
					//修改
					SampleRecord sr2=this.sampleRecordDao.get(sr1.getId());
					sr2.setModifyDate(new Date());//更新日期
					sr2.setRecordDescription(cause.getCauseName());
					sr2.setRecordNum(qxnums[i]);//缺陷数量
					sr2.setIsDel("N");
					sr2.setIstoDel("N");
					this.sampleRecordDao.update(sr2);
				}
				else
				{
					//新增
					SampleRecord sr=new SampleRecord();
					sr.setCreateDate(new Date());//初始化创建日期
					sr.setModifyDate(new Date());//初始化修改日期
					sr.setSampleId(sample.getId());//抽检单id
					sr.setRecordDescription(cause.getCauseName());
					sr.setRecordNum(qxnums[i]);//缺陷数量
					sr.setCauseId(qxids[i]);//缺陷ID
					sr.setIstoDel("N");
					this.sampleRecordDao.save(sr);
				}
			}
		}
		/**删除操作*/
		List<SampleRecord> list_sr=this.sampleRecordDao.getBySidAndMark(sample.getId(),"Y");//根据抽检单id和标记查询该缺陷记录
		if(list_sr.size()>0)
		{
			for(int i=0;i<list_sr.size();i++)
			{
				SampleRecord sr=list_sr.get(i);
				sr=this.sampleRecordDao.load(sr.getId());
				sr.setIsDel("Y");
				this.sampleRecordDao.update(sr);
			}
		}
		List<SampleRecord> list_sr2=this.sampleRecordDao.getBySidAndMark(sample.getId(),"N");
		if(list_sr2.size()>0)
		{
			for(int i=0;i<list_sr2.size();i++)
			{
				SampleRecord sr=list_sr2.get(i);
				sr=this.sampleRecordDao.load(sr.getId());
				sr.setIstoDel("Y");
				this.sampleRecordDao.update(sr);
			}
		}
	}
}
