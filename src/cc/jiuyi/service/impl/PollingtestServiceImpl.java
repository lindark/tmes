package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
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
import cc.jiuyi.service.AdminService;
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
	private AdminService adminService;

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
			String my_id) {
		Admin admin = this.adminService.getLoginAdmin();
		if (pollingtest != null) {
			pollingtest.setPollingtestUser(admin);// 巡检人
			if ("2".equals(my_id)) {
				pollingtest.setConfirmUser(admin);
				pollingtest.setState("1");
			}
			String pollingtestId = this.pollingtestDao.save(pollingtest);
			String[] qxids = info.split(",");// 缺陷IDS
			String[] qxnums = info2.split(",");// 缺陷nums
			Pollingtest pollingtest2 = this.pollingtestDao.load(pollingtestId);
			for(int i=0;i<qxids.length;i++)
			{
				if(qxids[i]!=null&&!"".equals(qxids[i]))
				{
					PollingtestRecord pr = new PollingtestRecord();
					Cause cause = this.causeDao.get(qxids[i]);// 根据缺陷ID查询
					pr.setCreateDate(new Date());// 初始化创建日期
					pr.setModifyDate(new Date());// 初始化修改日期
					pr.setPollingtest(pollingtest2);// 巡检单对象
					pr.setRecordDescription(cause.getCauseName());
					pr.setRecordNum(qxnums[i]);// 缺陷数量
					pr.setCauseId(qxids[i]);//缺陷ID
					this.pollingtestRecordDao.save(pr);
				}
			}
		}

	}

	@Override
	public void confirm(List<Pollingtest> list, Admin admin, String stu) {
		for (int i = 0; i < list.size(); i++) {
			Pollingtest pollingtest = list.get(i);
			pollingtest.setState(stu);
			pollingtest.setConfirmUser(admin);
			pollingtestDao.update(pollingtest);
		}
	}

	@Override
	public void updateInfo(Pollingtest pollingtest, String info, String info2,
			String my_id) {
		Admin admin=this.adminService.getLoginAdmin();
		Pollingtest p=this.pollingtestDao.load(pollingtest.getId());
		BeanUtils.copyProperties(pollingtest, p, new String[] { "id" });// 除了id不修改，其他都修改，自动完成设值操作
		p.setPollingtestUser(admin);
		if("2".equals(my_id))
		{
			p.setConfirmUser(admin);
			p.setState("1");
		}
		p.setModifyDate(new Date());
		this.pollingtestDao.update(p);//修改操作
		/**缺陷记录更新*/
		String[]qxids=info.split(",");//缺陷IDS
		String[]qxnums=info2.split(",");//缺陷nums
		for(int i=0;i<qxids.length;i++)
		{
			if(qxids[i]!=null&&!"".equals(qxids[i]))
			{
				Cause cause=this.causeDao.get(qxids[i]);//根据缺陷ID查询
				//根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
				PollingtestRecord pr1=this.pollingtestRecordDao.getBySidAndCid(pollingtest.getId(),qxids[i]);
				if(pr1!=null)
				{
					//if(qxnums[i]==null)
					//修改
					PollingtestRecord pr2=this.pollingtestRecordDao.get(pr1.getId());
					pr2.setModifyDate(new Date());//更新日期
					pr2.setRecordDescription(cause.getCauseName());
					pr2.setRecordNum(qxnums[i]);//缺陷数量
					pr2.setIsDel("N");
					pr2.setIstoDel("N");
					this.pollingtestRecordDao.update(pr2);
				}
				else
				{
					//新增
					PollingtestRecord pr=new PollingtestRecord();
					pr.setModifyDate(new Date());//初始化修改日期
					pr.setPollingtest(pollingtestDao.get(pollingtest.getId()));
					pr.setRecordDescription(cause.getCauseName());
					pr.setRecordNum(qxnums[i]);//缺陷数量
					pr.setCauseId(qxids[i]);//缺陷ID
					pr.setIstoDel("N");
					this.pollingtestRecordDao.save(pr);
				}
			}
		}
		/**删除操作*/
		List<PollingtestRecord> list_pr=this.pollingtestRecordDao.getBySidAndMark(pollingtest.getId(),"Y");//根据抽检单id和标记查询该缺陷记录
		if(list_pr.size()>0)
		{
			for(int i=0;i<list_pr.size();i++)
			{
				PollingtestRecord pr=list_pr.get(i);
				pr=this.pollingtestRecordDao.load(pr.getId());
				pr.setIsDel("Y");
				this.pollingtestRecordDao.update(pr);
			}
		}
		List<PollingtestRecord> list_sr2=this.pollingtestRecordDao.getBySidAndMark(pollingtest.getId(),"N");
		if(list_sr2.size()>0)
		{
			for(int i=0;i<list_sr2.size();i++)
			{
				PollingtestRecord pr=list_sr2.get(i);
				pr=this.pollingtestRecordDao.load(pr.getId());
				pr.setIstoDel("Y");
				this.pollingtestRecordDao.update(pr);
			}
		}
		
	}

}
