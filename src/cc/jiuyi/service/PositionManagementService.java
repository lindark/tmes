package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Mouldmaterial;
import cc.jiuyi.entity.PositionManagement;

public interface PositionManagementService extends BaseService<PositionManagement, String> {
	/**
	 * 分页查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getPositionManagementPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
