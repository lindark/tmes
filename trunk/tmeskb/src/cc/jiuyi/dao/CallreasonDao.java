package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Callreason;

/**
 * Dao接口
 * 呼叫原因
 */
public interface CallreasonDao extends BaseDao<Callreason, String> {
	public Pager getCallreasonPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
