package cc.jiuyi.dao;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Locationonside;

/**
 * Dao接口 - 管理员 线边仓
 */
public interface LocationonsideDao extends BaseDao<Locationonside, String> {
	public Pager getLocationPager(Pager pager);
}
