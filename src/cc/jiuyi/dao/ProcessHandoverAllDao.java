package cc.jiuyi.dao;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.ProcessHandoverSon;
/**
 * Dao接口 - 总体工序交接
 */
public interface ProcessHandoverAllDao extends BaseDao<ProcessHandoverAll, String>{
	
	public Pager jqGrid(Pager pager,Admin admin);

}
