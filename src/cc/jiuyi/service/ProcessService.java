package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;
/**
 * Service接口 - 工序管理
 */

public interface ProcessService extends BaseService<Process, String> {

	/**
	 * 取出所有Process对象
	 * @return
	 */
	public List<Process> getProcessList();
	
	public Pager getProcessPager(Pager pager);
	
	
	
}