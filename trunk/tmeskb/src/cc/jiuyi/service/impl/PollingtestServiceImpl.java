package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PollingtestDao;
import cc.jiuyi.entity.Pollingtest;
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

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return pollingtestDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		pollingtestDao.updateisdel(ids, oper);

	}

}
