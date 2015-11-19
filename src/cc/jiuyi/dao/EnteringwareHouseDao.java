package cc.jiuyi.dao;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.WorkingBill;

/**
 * Dao接口 - 随工单
 */

public interface EnteringwareHouseDao extends BaseDao<EnteringwareHouse, String> {
	
	/**
	 * 分页查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,Map map, String workingbillId);
	
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据单位描述与转换单位，查询兑换比例
	 * @param unitDescription 单位描述
	 * @param convertUnit 转换单位
	 * @return
	 */
	public Integer getSingleConversationRatio(String unitDescription,String convertUnit);
}