package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ScrapLater;

/**
 * 报废产出后记录
 * @author gaoyf
 *
 */
public interface ScrapLaterService extends BaseService<ScrapLater, String>
{

	/**
	 * 根据主表id获取产出表数据
	 * @param sid
	 * @return
	 */
	List<ScrapLater> getSlBySid(String sid);
	
	public Pager getLaterPager(Pager pager,HashMap<String,String>map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);

}
