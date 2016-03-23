package cc.jiuyi.dao;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.LocatHandOverHeader;
/**
 * Dao接口 线边仓交接 主表
 */
public interface LocatHandOverHeaderDao extends BaseDao<LocatHandOverHeader, String> {
	public Pager jqGrid(Pager pager,Admin admin);
}
