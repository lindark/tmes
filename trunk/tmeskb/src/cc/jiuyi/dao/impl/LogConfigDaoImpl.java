package cc.jiuyi.dao.impl;

import java.util.List;

import cc.jiuyi.dao.LogConfigDao;
import cc.jiuyi.entity.LogConfig;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 日志设置
 */

@Repository
public class LogConfigDaoImpl extends BaseDaoImpl<LogConfig, String> implements LogConfigDao {

	@SuppressWarnings("unchecked")
	public List<LogConfig> getLogConfigList(String actionClassName) {
		String hql = "from LogConfig as logConfig where logConfig.actionClassName = ?";
		return getSession().createQuery(hql).setParameter(0, actionClassName).list();
	}

}
