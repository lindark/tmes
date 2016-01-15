package cc.jiuyi.service;

import java.util.List;

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

}
