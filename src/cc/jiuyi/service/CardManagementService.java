package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.CardManagement;

/**
 * Service接口 - 刷卡设备管理
 */

public interface CardManagementService extends BaseService<CardManagement, String> {

	/**
	 * 取出所有CardManagement对象
	 * 
	 * @return
	 */
	public List<CardManagement> getCardManagementList();

	public Pager getCardManagementPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}