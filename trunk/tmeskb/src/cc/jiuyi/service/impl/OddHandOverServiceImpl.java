package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.service.OddHandOverService;
/**
 * Service实现类 - 零头数交接
 */

@Service
public class OddHandOverServiceImpl extends BaseServiceImpl<OddHandOver, String> implements
		OddHandOverService {
	@Resource
	private OddHandOverDao oddHandOverDao;
	
	@Resource
	public void setBaseDao(OddHandOverDao oddHandOverDao) {
		super.setBaseDao(oddHandOverDao);
	}

	public OddHandOver findHandOver(String workingBillCode) {
		return oddHandOverDao.findHandOver(workingBillCode);
	}
}
