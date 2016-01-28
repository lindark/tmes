package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.DumpDetail;

public interface DumpDetailService extends BaseService<DumpDetail, String> {
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,String dumpId);

	/**
	 * 根据条件查询
	 * @param pager
	 * @param mapcheck
	 * @return
	 */
	public Pager findDumpDetailByPager(Pager pager,HashMap<String,String> mapcheck);
}
