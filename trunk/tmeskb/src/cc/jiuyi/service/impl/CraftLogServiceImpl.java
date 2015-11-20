package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.CraftLogDao;
import cc.jiuyi.dao.UnusualLogDao;
import cc.jiuyi.entity.CraftLog;
import cc.jiuyi.service.CraftLogService;

/**
 * Service实现类 - 工艺问题日志
 */

@Service
public class CraftLogServiceImpl extends BaseServiceImpl<CraftLog, String> implements CraftLogService {

	@Resource
	private CraftLogDao craftLogDao;
	
	@Resource
	public void setBaseDao(CraftLogDao craftLogDao) {
		super.setBaseDao(craftLogDao);
	}
}
