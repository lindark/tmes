package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import cc.jiuyi.entity.Log;
import cc.jiuyi.service.LogService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Log log;

	@Resource
	private LogService logService;

	// 列表
	public String list() {
		pager = logService.findByPager(pager);
		return LIST;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}