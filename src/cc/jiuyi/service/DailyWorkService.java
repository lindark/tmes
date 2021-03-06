package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.CustomerException;

/**
 * Service接口 - 报工
 */

public interface DailyWorkService extends BaseService<DailyWork, String>,WorkingInoutCalculateBase<DailyWork> {
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

	// 刷卡确认
	public void updateState(List<DailyWork> list, String state,String workingbillid,
			String cardnumber) throws IOException, CustomerException;

	// 刷卡撤销
	public void updateState2(List<DailyWork> list,String state,String workingbillid,
			String cardnumber) throws IOException, CustomerException;
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}