package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.service.DailyWorkService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 报工
 */

@Service
public class DailyWorkServiceImpl extends BaseServiceImpl<DailyWork, String>
		implements DailyWorkService {
	@Resource
	private DailyWorkDao dailyWorkDao;

	@Resource
	public void setBaseDao(DailyWorkDao dailyWork) {
		super.setBaseDao(dailyWork);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String,String> map, String workingbillId) {
		return dailyWorkDao.findPagerByjqGrid(pager, map, workingbillId);
	}

}