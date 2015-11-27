package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.PollingtestDao;
import cc.jiuyi.dao.PollingtestRecordDao;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.PollingtestRecord;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.service.PollingtestService;

/**
 * Service实现类 巡检
 */
@Service
@Transactional
public class PollingtestServiceImpl extends
		BaseServiceImpl<Pollingtest, String> implements PollingtestService {
	@Resource
	private PollingtestDao pollingtestDao;
	@Resource
	private PollingtestRecordDao pollingtestRecordDao;
	@Resource
	private CauseDao causeDao;
	@Resource
	private AdminDao adminDao;
	
	@Resource
	public void setBaseDao(PollingtestDao pollingtestDao) {
		super.setBaseDao(pollingtestDao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return pollingtestDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		pollingtestDao.updateisdel(ids, oper);

	}

	@Override
	public void saveInfo(Pollingtest pollingtest, String info, String info2,
			String my_id, Admin admin) {
		if(pollingtest!=null)
		{
			pollingtest.setPollingtestUser(admin);//巡检人
			if("2".equals(my_id))
			{
				pollingtest.setConfirmUser(admin);
				pollingtest.setState("1");
			}
			String pollingtestId=this.pollingtestDao.save(pollingtest);
			String[] qxids=info.split(",");//缺陷IDS
			String[] qxnums=info2.split(",");//缺陷nums
			Pollingtest pollingtest2=this.pollingtestDao.load(pollingtestId);
			for(int i=0;i<qxids.length;i++)
			{
				if(qxids[i]!=null&&!"".equals(qxids[i]))
				{
					PollingtestRecord pr=new PollingtestRecord();
					Cause cause=this.causeDao.get(qxids[i]);//根据缺陷ID查询
					pr.setCreateDate(new Date());//初始化创建日期
					pr.setModifyDate(new Date());//初始化修改日期
					pr.setPollingtest(pollingtest2);//巡检单对象
					pr.setRecordDescription(cause.getCauseName());
					pr.setRecordNum(qxnums[i]);//缺陷数量
					this.pollingtestRecordDao.save(pr);
				}
			}
		}
		
	}

	@Override
	public void confirm(List<Pollingtest> list,Admin admin,String stu) {
		for (int i = 0; i < list.size(); i++) {
			Pollingtest pollingtest = list.get(i);
			pollingtest.setState(stu);				
			pollingtest.setConfirmUser(admin);
			pollingtestDao.update(pollingtest);
		}
	}

}
