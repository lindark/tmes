package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ScrapMessage;

/**
 * 报废信息
 * @author gaoyf
 *
 */
public interface ScrapMessageDao extends BaseDao<ScrapMessage, String>
{

	/**
	 * 根据scrap表id和物料表id查询
	 */
	public ScrapMessage getBySidAndMid(String sid, String mid);
	
	public Pager getMessagePager(Pager pager, HashMap<String, String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
