package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ScrapLaterDao;
import cc.jiuyi.entity.ScrapLater;

/**
 * 报废后产出记录
 * @author gaoyf
 *
 */
@Repository
public class ScrapLaterDaoImpl extends BaseDaoImpl<ScrapLater, String> implements ScrapLaterDao
{

	/**
	 * 根据主表id获取产出表数据
	 */
	@SuppressWarnings("unchecked")
	public List<ScrapLater> getSlBySid(String sid)
	{
		String hql="from ScrapLater where scrap.id=?";
		return this.getSession().createQuery(hql).setParameter(0, sid).list();
	}

}
