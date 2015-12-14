 package cc.jiuyi.dao;

import java.util.List;

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
	
   /**
    * 取出所有未确认的报废单
    */
	public List<Scrap> unCheckList();
}
