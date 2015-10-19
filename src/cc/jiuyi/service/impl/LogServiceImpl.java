package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.LogDao;
import cc.jiuyi.entity.Log;
import cc.jiuyi.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 日志
 */

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}
