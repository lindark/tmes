package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Repair;

/**
 * Service接口
 * 返修
 */
public interface RepairService extends BaseService<Repair, String>{
	public Pager getRepairPager(Pager pager,HashMap<String,String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
