package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Model;

/**
 * Dao接口 - 工模
 */
public interface ModelDao extends BaseDao<Model, String> {

	public Pager getModelPager(Pager pager, HashMap<String, String> map);
}
