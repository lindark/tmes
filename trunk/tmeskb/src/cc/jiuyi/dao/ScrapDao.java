 package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.WorkingBill;

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
	public List<Scrap> getUnCheckList();
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Scrap> getScrapList(WorkingBill workingBill);
}



