package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.EnteringwareHouse;

/**
 * Dao接口 - 随工单
 */

public interface EnteringwareHouseDao extends
		BaseDao<EnteringwareHouse, String> {

	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, Map map, String workingbillId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	/**
	 * 根据单位描述与转换单位，查询兑换比例
	 * 
	 * @param unitDescription
	 *            单位描述
	 * @param convertUnit
	 *            转换单位
	 * @return
	 */
	public List<EnteringwareHouse> getByBill(String workingBillId);
	
	
	/**
	 * Excel导出
	 * @author Reece
	 * @param map
	 * @return
	 */
	public List<Object[]> historyExcelExport(HashMap<String, String> map);
	
	
}