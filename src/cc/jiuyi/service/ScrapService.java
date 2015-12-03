package cc.jiuyi.service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Scrap;
/**
 * 报废
 * @author gaoyf
 *
 */
public interface ScrapService extends BaseService<Scrap, String>
{

	/**
	 * jqGrid查询
	 * @param pager
	 * @param wbId
	 * @return
	 */
	public Pager getScrapPager(Pager pager, String wbId);

}
