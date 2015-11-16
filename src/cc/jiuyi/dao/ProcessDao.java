package cc.jiuyi.dao;

import java.util.HashMap;
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
	
	public Pager getProcessPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据工序编码判断此工序是否存在
	 */
	public boolean isExistByProcessCode(String processCode);

	/**
	 * 查询一个带联表
	 */
	public Process getOne(String id);

	/**
	 * 检查工序编码是否存在
	 */
	public List<Process> getCk(String info);
}
