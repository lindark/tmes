package cc.jiuyi.dao;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Scrap;

/**
 * 报废
 * @author gaoyf
 *
 */
public interface ScrapDao extends BaseDao<Scrap, String>
{

	/**
	 * jqGrid查询
	 */
	public Pager getScrapPager(Pager pager, String wbId);
	
}
