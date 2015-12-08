package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ScrapBugDao;
import cc.jiuyi.entity.ScrapBug;

/**
 * 报废--缺陷记录
 * @author lenovo
 *
 */
@Repository
public class ScrapBugDaoImpl extends BaseDaoImpl<ScrapBug, String> implements ScrapBugDao
{

	/**
	 * 根据报废信息ID和缺陷表ID查询报废原因
	 */
	@SuppressWarnings("unchecked")
	public ScrapBug getBySmidAndCid(String smid, String cid)
	{
		String hql="form ScrapBug where scrapMessage_id=? and causeId=?";
		List<ScrapBug>list=this.getSession().createQuery(hql).setParameter(0, smid).setParameter(1, cid).list();
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

}
