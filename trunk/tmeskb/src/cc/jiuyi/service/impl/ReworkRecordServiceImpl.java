package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReworkRecordDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ReworkRecord;
import cc.jiuyi.service.ReworkRecordService;

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
	public Pager getReworkRecordPager(Pager pager, HashMap<String, String> map,String workingbillId) {
		// TODO Auto-generated method stub
		return reworkReworkDao.getReworkRecordPager(pager, map, workingbillId);
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


	
}
