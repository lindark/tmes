package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pollingtest;

/**
 * Service接口 巡检
 */
public interface PollingtestService extends BaseService<Pollingtest, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);

	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	public void saveInfo(Pollingtest pollingtest, String info, String info2,
			String my_id, String cardnumber);

	public void confirm(List<Pollingtest> list, Admin admin, String stu);

	public void updateConfirm(List<Pollingtest> list, Admin admin, String stu);
	
	public void updateInfo(Pollingtest pollingtest, String info, String info2,
			String my_id, String cardnumber);

	/**
	 * 取出所有未确认的巡检单
	 * 
	 * @return
	 */
	public List<Pollingtest> getUncheckList();
	
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
