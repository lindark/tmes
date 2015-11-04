package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.CardManagement;

/**
 * Dao接口 -刷卡设备管理
 * 
 *
 */

public interface CardManagementDao extends BaseDao<CardManagement,String> {
	
	
	/**
	 * 取出所有刷卡设备对象
	 * @return
	 */
	public List<CardManagement> getCardManagementList();
	
	public Pager getCardManagementPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
