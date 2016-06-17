package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AbnormalService;

/**
 * Service实现类 - 异常
 */

@Service
public class AbnormalServiceImpl extends BaseServiceImpl<Abnormal, String> implements AbnormalService {

	@Resource
	private AbnormalDao abnormalDao;
	
	@Resource
	public void setBaseDao(AbnormalDao abnormalDao) {
		super.setBaseDao(abnormalDao);
	}

	@Override
	public Pager getAbnormalPager(Pager pager, HashMap<String, String> map,Admin admin2) {
	
		return abnormalDao.getAbnormalPager(pager, map,admin2);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		abnormalDao.updateisdel(ids, oper);
	}

	@Override
	public Pager getAbnormalAllPager(Pager pager, HashMap<String, String> map,
			String id) {
		return abnormalDao.getAbnormalAllPager(pager, map,id);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return abnormalDao.historyExcelExport(map);
	}
}
