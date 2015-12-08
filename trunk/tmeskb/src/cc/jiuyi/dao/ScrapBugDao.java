package cc.jiuyi.dao;

import cc.jiuyi.entity.ScrapBug;

/**
 * 报废--缺陷记录
 * @author gaoyf
 *
 */
public interface ScrapBugDao extends BaseDao<ScrapBug, String>
{

	/**
	 * 根据报废信息ID和缺陷表ID查询报废原因
	 * @param id
	 * @param string
	 * @return
	 */
	public ScrapBug getBySmidAndCid(String id, String string);

}
