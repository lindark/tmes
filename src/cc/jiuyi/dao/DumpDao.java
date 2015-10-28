package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口 - 管理员
 * 转储管理
 */
public interface DumpDao extends BaseDao<Dump, String>{
	public Pager getDumpPager(Pager pager,HashMap<String,String> map);
}
