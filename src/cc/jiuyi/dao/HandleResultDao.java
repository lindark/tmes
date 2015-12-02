package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.HandlemeansResults;

/**
 * Dao接口 - 故障处理方法与结果
 */
public interface HandleResultDao extends BaseDao<HandlemeansResults, String> {

    public Pager getHandlePager(Pager pager,HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<HandlemeansResults> getAllHandle();
}
