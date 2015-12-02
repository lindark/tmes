package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.HandlemeansResults;

/**
 * Service接口 - 
 */
public interface HandleResultService extends BaseService<HandlemeansResults, String> {

   public Pager getHandlePager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<HandlemeansResults> getAllHandle();
}
