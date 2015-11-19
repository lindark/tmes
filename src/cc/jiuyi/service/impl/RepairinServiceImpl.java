package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.service.RepairinService;

/**
 * Service实现类 返修收货
 */
@Service
@Transactional
public class RepairinServiceImpl extends BaseServiceImpl<Repairin, String>
		implements RepairinService {
	@Resource
	private RepairinDao repairinDao;

	@Resource
	public void setBaseDao(RepairinDao repairinDao) {
		super.setBaseDao(repairinDao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairinDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		repairinDao.updateisdel(ids, oper);
	}
}
