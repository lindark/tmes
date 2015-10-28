package cc.jiuyi.service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Locationonside;

/**
 * Service接口 - 管理员 线边仓
 */
public interface LocationonsideService extends
		BaseService<Locationonside, String> {
	public Pager getLocationPager(Pager pager);
}
