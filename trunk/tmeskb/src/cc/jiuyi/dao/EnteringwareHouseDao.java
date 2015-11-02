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
	public Pager findPagerByjqGrid(Pager pager,Map map);
	
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	

}