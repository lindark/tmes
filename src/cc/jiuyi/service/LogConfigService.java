package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.LogConfig;

/**
 * Service接口 - 日志设置
 */

public interface LogConfigService extends BaseService<LogConfig, String> {

	/**
	 * 根据Action类名称获取LogConfig对象集合.
	 * 
	 * @param actionClassName
	 *            Action类名称
	 * @return LogConfig对象集合
	 */
	public List<LogConfig> getLogConfigList(String actionClassName);

}