package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Cause;

/**
 * Dao接口
 * 缺陷代码
 */
public interface CauseDao extends BaseDao<Cause, String> {
	public Pager getCausePager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
