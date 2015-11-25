package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.AbnormalLogDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;

/**
 * Service实现类 - 异常日志
 */

@Service
public class AbnormalLogServiceImpl extends BaseServiceImpl<AbnormalLog, String> implements AbnormalLogService{

	@Resource
	private AbnormalLogDao abnormalLogDao;
	
	@Resource
	public void setBaseDao(AbnormalLogDao abnormalLogDao) {
		super.setBaseDao(abnormalLogDao);
	}
}
