package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Craft;

/**
 * Dao接口 - 工模
 */
public interface CraftDao extends BaseDao<Craft, String> {

	public Pager getCraftPager(Pager pager,HashMap<String,String>map);
}
