package cc.jiuyi.dao.impl;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ScrapMessageDao;
import cc.jiuyi.entity.ScrapMessage;

/**
 * 报废信息
 * @author gaoyf
 *
 */
@Repository
public class ScrapMessageDaoImpl extends BaseDaoImpl<ScrapMessage, String> implements ScrapMessageDao
{

	/**
	 * 根据scrap表id和物料表id查询
	 */
	public ScrapMessage getBySidAndMid(String sid, String mid)
	{
		String hql="from ScrapMessage where isDel='N' and scrap_id=? and materialId=?";
		return (ScrapMessage) this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, mid).uniqueResult();
	}

}
