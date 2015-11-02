package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Repair;

/**
 * Dao接口
 * 返修
 */
public interface RepairDao extends BaseDao<Repair, String>{
	public Pager getRepairPager(Pager pager,HashMap<String,String> map);
	
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
