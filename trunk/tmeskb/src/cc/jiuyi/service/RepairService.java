package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 返修
 */
public interface RepairService extends BaseService<Repair, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);

	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	public void updateState(List<Repair> list, String statu,
			String workingbillid,String cardnumber);

	/**
	 * 与SAP交互   退料262  905
	 * list 主表数据   wbid随工单id
	 * @return
	 * @author gyf
	 */
	public List<Map<Object, Object>> getSAPMap(List<Repair> list, WorkingBill wb,String cardnumber);
	
	/**
	 * 与SAP交互没有问题,更新本地数据库
	 */
	public void updateMyData(Map<Object, Object> m, String cardnumber);
}
