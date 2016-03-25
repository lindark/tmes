package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.LocatHandOverHeader;
/**
 * Dao接口 线边仓交接 主表
 */
public interface LocatHandOverHeaderDao extends BaseDao<LocatHandOverHeader, String> {
	public Pager jqGrid(Pager pager,Admin admin);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
