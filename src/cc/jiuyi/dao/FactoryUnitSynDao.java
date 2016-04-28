package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnitSyn;
/**
 * Dao接口 - 单元同步
 * 
 *
 */
public interface FactoryUnitSynDao extends BaseDao<FactoryUnitSyn, String> {
	public Pager getFactoryUnitSynPager(Pager pager, HashMap<String, String> map);
}
