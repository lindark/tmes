package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.dao.LogConfigDao;
import cc.jiuyi.entity.LogConfig;
import cc.jiuyi.service.LogConfigService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 日志设置
 */

@Service
public class LogConfigServiceImpl extends BaseServiceImpl<LogConfig, String> implements LogConfigService {
	
	@Resource
	private LogConfigDao logConfigDao;

	@Resource
	public void setBaseDao(LogConfigDao logConfigDao) {
		super.setBaseDao(logConfigDao);
	}
	
	public List<LogConfig> getLogConfigList(String actionClassName) {
		return logConfigDao.getLogConfigList(actionClassName);
	}
	
	@Override
	@Cacheable(modelId = "caching")
	public List<LogConfig> getAll() {
		List<LogConfig> allLogConfig = logConfigDao.getAll();
		if (allLogConfig != null) {
			for (LogConfig logConfig : allLogConfig) {
				Hibernate.initialize(logConfig);
			}
		}
		return allLogConfig;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(LogConfig entity) {
		logConfigDao.delete(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		logConfigDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		logConfigDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(LogConfig entity) {
		return logConfigDao.save(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(LogConfig entity) {
		logConfigDao.update(entity);
	}

}