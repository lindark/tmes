package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.EnteringwareHouse;

/**
 * Service接口 - 入库
 */

public interface EnteringwareHouseService extends
		BaseService<EnteringwareHouse, String> {
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
	 * 根据单位描述与转换单位，查询兑换比例
	 * 
	 * @param unitDescription
	 *            单位描述
	 * @param convertUnit
	 *            转换单位
	 * @return
	 */

	public List<EnteringwareHouse> getByBill(String workingBillId);

	public void updateState(List<EnteringwareHouse> list, String statu,
			String workingbillid,Integer ratio);
}