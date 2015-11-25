package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.HandOverProcess;

/**
 * Service接口 - 工序交接
 */

public interface HandOverProcessService extends BaseService<HandOverProcess, String> {

	/**
	 * 取出所有HandOverProcess对象
	 * 
	 * @return
	 */
	public List<HandOverProcess> getHandOverProcessList();

	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	/**
	 * 保存集合对象
	 * @param handoverprocessList
	 * @return
	 */
	public void save(List<HandOverProcess> handoverprocessList);
}