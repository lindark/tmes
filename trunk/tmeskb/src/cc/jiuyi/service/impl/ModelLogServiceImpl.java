package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ModelLogDao;
import cc.jiuyi.entity.ModelLog;
import cc.jiuyi.service.ModelLogService;

/**
 * Service实现类 - 工模问题日志
 */

@Service
public class ModelLogServiceImpl extends BaseServiceImpl<ModelLog, String> implements ModelLogService {

	@Resource
	private ModelLogDao modelLogDao;
	
	@Resource
	public void setBaseDao(ModelLogDao modelLogDao) {
		super.setBaseDao(modelLogDao);
	}
}
