package cc.jiuyi.dao;

import cc.jiuyi.bean.Pager;
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
}
