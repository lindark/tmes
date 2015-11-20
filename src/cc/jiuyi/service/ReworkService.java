package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Rework;

/**
 * Service接口 - 返工
 */

public interface ReworkService extends BaseService<Rework, String> {

	/**
	 * 取出所有Rework对象
	 * 
	 * @return
	 */
	public List<Rework> getReworkList();

	public Pager getReworkPager(Pager pager, HashMap<String, String> map,String workingbillId);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}