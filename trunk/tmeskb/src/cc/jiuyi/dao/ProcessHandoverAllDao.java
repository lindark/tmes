package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.ProcessHandoverSon;
/**
 * Dao接口 - 总体工序交接
 */
public interface ProcessHandoverAllDao extends BaseDao<ProcessHandoverAll, String>{
	
	public Pager jqGrid(Pager pager,Admin admin);
	
	public List<ProcessHandoverAll> getListOfAllProcess(String productDate,String shift,String factoryId);

}
