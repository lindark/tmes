package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.ScrapLater;

/**
 * 报废后产出记录
 * @author lenovo
 *
 */
public interface ScrapLaterDao extends BaseDao<ScrapLater, String>
{

	/**
	 * 根据主表id获取产出表数据
	 */
	public List<ScrapLater> getSlBySid(String sid);

}
