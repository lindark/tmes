package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.PositionManagement;


public interface PositionManagementDao extends BaseDao<PositionManagement, String>{
	
	public List<PositionManagement> getPositionManagementList(PositionManagement positionManagement);
	
	public Pager getPositionManagementPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<String> getPositionList(PositionManagement positionManagement);
	
	public List<String> getPositionList1(String warehouse,PositionManagement positionManagement);
}
