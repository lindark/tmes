package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;

/**
 * Dao接口 -工序管理
 * 
 *
 */

public interface ProcessDao extends BaseDao<Process,String> {
	
	
	/**
	 * 取出所有工序对象
	 * @return
	 */
	public List<Process> getProcessList();
	
	public Pager getProcessPager(Pager pager);
	
}
