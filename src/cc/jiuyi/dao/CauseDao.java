package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

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
	
	/**
	 * 获取缺陷表中关于抽检的内容
	 */
	public List<Cause> getBySample(String type);
}
