package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DumpDetail;

public interface DumpDetailDao  extends BaseDao<DumpDetail, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);
}
