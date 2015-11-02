package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Repairin;
/**
 * Dao接口
 * 返修收获
 */
public interface RepairinDao extends BaseDao<Repairin, String>{
	public Pager getRepairinPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
