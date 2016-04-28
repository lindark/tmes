package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnitSyn;

/**
 * Service接口 - 单元同步
 */

public interface FactoryUnitSynService extends BaseService<FactoryUnitSyn, String> {

	public Pager getFactoryUnitSynPager(Pager pager, HashMap<String, String> map);
	
	
}
