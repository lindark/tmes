package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReworkRecordDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.ReworkRecord;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ReworkRecordService;
import cc.jiuyi.service.ReworkService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 -返工记录
 * @author Reece
 *
 */

@Service
public class ReworkRecordServiceImpl extends BaseServiceImpl<ReworkRecord, String>implements ReworkRecordService{

	
	@Resource
	private ReworkRecordDao reworkReworkDao;
	@Resource
	private ReworkService reworkService;
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService workingBillService;
	
	@Resource
	public void setBaseDao(ReworkRecordDao reworkReworkDao){
		super.setBaseDao(reworkReworkDao);
	}
	
	@Override
	public void delete(String id) {
		ReworkRecord reworkRecord = reworkReworkDao.load(id);
		this.delete(reworkRecord);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<ReworkRecord> getReworkRecordList() {		
		return reworkReworkDao.getReworkRecordList();
	}

	@Override
	public Pager getReworkRecordPager(Pager pager, HashMap<String, String> map,String id) {
		// TODO Auto-generated method stub
		return reworkReworkDao.getReworkRecordPager(pager, map,id);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		reworkReworkDao.updateisdel(ids, oper);
		
	}

	@Override
	public void saveRepeal(List<ReworkRecord> list, Admin admin, String stu) {
		for (int i = 0; i < list.size(); i++) {
			ReworkRecord reworkRecord=list.get(i);
			reworkRecord.setState(stu);
			reworkRecord.setConfirmUser(admin);
			reworkReworkDao.update(reworkRecord);	
		}
		
	}

	@Override
	public String saveSubmit(String cardnumber, String workingBillId,
			String reworkId, Integer reworkCount, ReworkRecord reworkRecord) {
		Admin admin = adminService.getByCardnum(cardnumber);
		WorkingBill workingbill = workingBillService.get(workingBillId);

		/** 判断主表ID有没有,没有的话就添加一个主表对象，有的话就更新主表状态 **/
		if (reworkId.equals("")) {
			/** 先增加主表对象 **/
			Rework rework = new Rework();
			rework.setWorkingbill(workingbill);
			rework.setCreateUser(admin);
			rework.setDuty(reworkRecord.getDuty());
			rework.setState("1");// 主表对象状态为未确认
			reworkId = reworkService.save(rework);

		} else {
			Rework rework = reworkService.get(reworkId);
			/** 如果从表不合格，主表状态为未确认 如果从表为合格，主表状态更新为已确认 **/
			if (reworkRecord.getIsQualified().equals("N")) {
				rework.setState("1");
			} else {
				rework.setState("2");
			}
			reworkService.update(rework);
		}

		/** 第一次为0翻包次数是1,如果不是第一次即不是第一次翻包次数+1 **/
		if (reworkCount == 0) {
			reworkCount = 1;
		} else {
			reworkCount = reworkCount + 1;
		}

		Rework rework = reworkService.get(reworkId);
		/** 增加从表对象 **/
		reworkRecord.setReworkCount(reworkCount);
		reworkRecord.setCreateUser(admin);
		reworkRecord.setModifyUser(admin);
		reworkRecord.setRework(rework);
		reworkReworkDao.save(reworkRecord);// 保存从表 状态为未确认

		/** 修改主表翻包数量 **/
		rework.setReworkCount(reworkCount);
		reworkService.update(rework);

		return reworkId;
	}

	@Override
	public String saveApproval(String cardnumber, ReworkRecord reworkRecord) {
		Admin admin = adminService.getByCardnum(cardnumber);
		reworkRecord.setConfirmUser(admin);
		reworkRecord.setModifyUser(admin);	
		reworkRecord.setState("2");//状态变已确认
		reworkReworkDao.save(reworkRecord);//修改从表
		
		String reworkId = reworkRecord.getRework().getId();
		
		/**如果从表全部确认了，主表状态改成已确认**/
		List<ReworkRecord> reworkRecordList = new ArrayList<ReworkRecord>(reworkService.get(reworkId).getReworkRecord());
		for (int i = 0; i < reworkRecordList.size(); i++) {
			ReworkRecord reworkRecord1 = reworkRecordList.get(i);
			if(reworkRecord1.getState().equals("2")){
				Rework rework = reworkService.get(reworkId);
				rework.setState("2");
				rework.setConfirmUser(admin);
				reworkService.update(rework);
			}
		}
		return reworkId;
	}
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return reworkReworkDao.historyjqGrid(pager, map);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		return reworkReworkDao.historyExcelExport(map);
	}

	@Override
	public List<ReworkRecord> getReworkRecord(String id) {
		return reworkReworkDao.getReworkRecord(id);
	}
	
	

}
