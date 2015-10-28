package cc.jiuyi.service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Dump;

/**
 * Service接口 - 管理员
 * 转储管理
 */
public interface DumpService extends BaseService<Dump, String>{
	public Pager getDumpPager(Pager pager);
}
