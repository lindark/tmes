package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
/**
 * Dao接口
 * 返修收获
 */
public interface RepairinDao extends BaseDao<Repairin, String>{
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String,String> map, String workingbillId);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	public void updateState(String[] ids,WorkingBill workingbill,Repairin repairin,Admin admin);
	public void updateStates(String[] ids,WorkingBill workingbill,Repairin repairin,Admin admin);
}
