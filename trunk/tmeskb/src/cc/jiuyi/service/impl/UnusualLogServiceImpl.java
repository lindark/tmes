package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.UnusualLogDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.UnusualLogService;

/**
 * Service实现类 - 异常日志
 */

@Service
public class UnusualLogServiceImpl extends BaseServiceImpl<UnusualLog, String> implements UnusualLogService {

	@Resource
	private UnusualLogDao unusualLogDao;
	
	@Resource
	public void setBaseDao(UnusualLogDao unusualLogDao) {
		super.setBaseDao(unusualLogDao);
	}
}
