package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ItermediateTestDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.IpRecord;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.IpRecordService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -半成品巡检主表Service实现类
 * @author Reece
 *
 */

@Service
public class ItermediateTestServiceImpl extends BaseServiceImpl<ItermediateTest, String>implements ItermediateTestService{

	@Resource
	private ItermediateTestDao itermediateTestDao;
	@Resource
	private ItermediateTestDetailService itdService;
	@Resource
	private CauseService causeService;
	@Resource
	private IpRecordService ipRecordService;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	
	@Resource
	public void setBaseDao(ItermediateTestDao itermediateTestDao){
		super.setBaseDao(itermediateTestDao);
	}
	
	@Override
	public void delete(String id) {
		ItermediateTest itermediateTest = itermediateTestDao.load(id);
		this.delete(itermediateTest);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<ItermediateTest> getItermediateTestList() {		
		return itermediateTestDao.getItermediateTestList();
	}

	@Override
	public Pager getItermediateTestPager(String workingBillId,Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return itermediateTestDao.getItermediateTestPager(workingBillId,pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		itermediateTestDao.updateisdel(ids, oper);
		
	}
	
	/**
	 * 增加缺陷和半成品巡检从表
	 */
	public void myadd(List<ItermediateTestDetail> list_itmesg,
			List<IpRecord> list_itbug, ItermediateTest itermediateTest, int i) {
		try {
			// 不合格信息表
			ItermediateTestDetail it = list_itmesg.get(i);
			if (it.getTestAmount()!=null&&!"".equals(it.getTestAmount())) {
				it.setCreateDate(new Date());
				it.setModifyDate(new Date());
				it.setItermediateTest(itermediateTest);
				String DetailId = this.itdService.save(it);
				ItermediateTestDetail it2 = this.itdService.load(DetailId);

				if (it.getFailReason() != null && !"".equals(it.getFailReason())) {
					IpRecord ip1 = list_itbug.get(i);
					String[] bugids = ip1.getXbugids().split(",");// 缺陷id
					String[] bugnums = ip1.getXbugnums().split(",");// 缺陷描述
					for (int j = 0; j < bugids.length; j++) {
						if (bugids[j] != null && !"".equals(bugids[j])) {
							IpRecord ipRecord = new IpRecord();
							Cause cause = this.causeService.get(bugids[j]);
							ipRecord.setCreateDate(new Date());
							ipRecord.setCauseId(bugids[j]);// 缺陷ID
							ipRecord.setRecordNum(bugnums[j]);// 缺陷数量
							ipRecord.setRecordDescription(cause.getCauseName());// 缺陷描述
							ipRecord.setItermediateTestDetail(it2);// 巡检从表对象
							ipRecord.setIsDel("Y");
							this.ipRecordService.save(ipRecord);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 确认或撤销
	 */
	public void updateState(List<ItermediateTest> list, String stus,String cardnumber) {
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		for (int i = 0; i < list.size(); i++) {
			ItermediateTest it=list.get(i);
			it.setState(stus);
			it.setConfirmUser(admin);
			this.itermediateTestDao.update(it);
		}
	}

	@Override
	public void saveSubmit(ItermediateTest itermediateTest,
			List<ItermediateTestDetail> list_itmesg, List<IpRecord> list_itbug,String my_id,String cardnumber) {
		Admin admin = this.adminservice.getByCardnum(cardnumber);
		// 半成品巡检主表
		itermediateTest.setState("1");
		itermediateTest.setCreateUser(admin);
		itermediateTest.setCreateDate(new Date());
		itermediateTest.setShift(admin.getShift());
		itermediateTest.setProductDate(admin.getProductDate());
		itermediateTest.setModifyDate(new Date());
		if("2".equals(my_id)){
			itermediateTest.setState("2");
			itermediateTest.setConfirmUser(admin);
		}
		String itId = this.itermediateTestDao.save(itermediateTest);
		ItermediateTest it = this.itermediateTestDao.load(itId);
		if (list_itmesg != null) {
			for (int i = 0; i < list_itmesg.size(); i++) {
				myadd(list_itmesg, list_itbug, it, i);
			}
		}
	}


	@Override
	public void updateAll(ItermediateTest itermediateTest,
			List<ItermediateTestDetail> list_itmesg, List<IpRecord> list_itbug,String my_id,String cardnumber) {
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		ItermediateTest it=this.itermediateTestDao.get(itermediateTest.getId());
		it.setMoudle(itermediateTest.getMoudle());
		it.setModifyDate(new Date());
		if("2".equals(my_id)){
			it.setState("2");//状态
			it.setConfirmUser(admin);//确认人
		}
		this.itermediateTestDao.update(it);
		
		if(list_itmesg!=null){
			for (int i = 0; i < list_itmesg.size(); i++) {
				ItermediateTestDetail newdit=list_itmesg.get(i);
				if(newdit.getId()!=null&&!"".equals(newdit.getId())){
					ItermediateTestDetail dit1=this.itdService.get(newdit.getId());
					List<IpRecord> l_sbug=new ArrayList<IpRecord>(dit1.getIpRecord());
					for(int i2=0;i2<l_sbug.size();i2++){						
						IpRecord s=l_sbug.get(i2);
						if(s!=null){
							this.ipRecordService.delete(s.getId());
						}
					}
					if(newdit.getTestAmount()!=null&&!"".equals(newdit.getTestAmount())){						
						//修改半成品巡检从表
						dit1.setTestAmount(newdit.getTestAmount());
						dit1.setFailReason(newdit.getFailReason());
						dit1.setFailAmount(newdit.getFailAmount());
						dit1.setGoodsSzie1(newdit.getGoodsSzie1());
						dit1.setGoodsSzie2(newdit.getGoodsSzie2());
						dit1.setGoodsSzie3(newdit.getGoodsSzie3());
						dit1.setGoodsSzie4(newdit.getGoodsSzie4());
						dit1.setGoodsSzie5(newdit.getGoodsSzie5());
						dit1.setModifyDate(new Date());
						this.itdService.update(dit1);
						
						if(newdit.getFailReason()!=null&&!"".equals(newdit.getFailReason())){
							IpRecord newip=list_itbug.get(i);
							String[] newsbids=newip.getXbugids().split(",");//缺陷id
							String[] newsbnums=newip.getXbugnums().split(",");//缺陷描述
							for (int j = 0; j < newsbids.length; j++) {
								 if(newsbids[j]!=null&&!"".equals(newsbids[j])){
									 Cause cause=this.causeService.get(newsbids[j]);
									 //新增报废原因
									 IpRecord ip2=new IpRecord();
									 ip2.setCreateDate(new Date());
									 ip2.setCauseId(newsbids[j]);
									 ip2.setRecordNum(newsbnums[j]);
									 ip2.setRecordDescription(cause.getCauseName());
									 ip2.setItermediateTestDetail(dit1);
									 this.ipRecordService.save(ip2);
								 }
							}	
						}							
					}
					else{
						//删除从表信息
						this.itdService.delete(dit1.getId());
					}	
				  }
				else{
					//新增从表信息
					myadd(list_itmesg,list_itbug,it,i);
				}
			}
		}
		
		
	}
}
	

