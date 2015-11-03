package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.WorkShop;

/**
 * Service接口 - 车间管理
 */

public interface WorkShopService extends BaseService<WorkShop, String> {

	/**
	 * 取出所有WorkShop对象
	 * 
	 * @return
	 */
	public List<WorkShop> getWorkShopList();

	public Pager getWorkShopPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}