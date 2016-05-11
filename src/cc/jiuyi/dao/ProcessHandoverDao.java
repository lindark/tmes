package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;

/**
 * Dao接口 - 工序交接
 */
public interface ProcessHandoverDao extends BaseDao<ProcessHandover, String> {
	/**
	 * 获取未删除的数据
	 * @param pager
	 * @return
	 */
	public Pager jqGrid(Pager pager);
	
	public Pager jqGrid(Pager pager,Admin admin);
	
//	public List<ProcessHandover> getProcessHandoverList(String[] propertyNames,String[] propertyValues);
}
