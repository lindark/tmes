package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;
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
	
	public List<PositionManagement> getPositionManagementList();
	
	public List<String> getPositionList(PositionManagement positionManagement);
	
//	public List<String> getPositionList(String warehouse);

	public List<String> getPositionList1(String warehouse,PositionManagement PositionManagement);
}
