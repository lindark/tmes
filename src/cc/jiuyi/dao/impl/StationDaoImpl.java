package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.StationDao;
import cc.jiuyi.entity.Station;
/**
 * Dao接口 - 工位
 */
@Repository
public class StationDaoImpl extends BaseDaoImpl<Station, String> implements StationDao
{

	/**
	 * jqgrid查询
	 */
	public Pager getByPager(Pager pager, HashMap<String, String> map)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Station.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "posts", "posts"))
		{
			detachedCriteria.createAlias("posts", "posts");
		}
		if(map!=null&&map.size()>0)
		{
			//工位编码
			if(map.get("stationcode")!=null)
			{
				detachedCriteria.add(Restrictions.like("code", "%"+map.get("stationcode")+"%"));
			}
			//工位名称
			if(map.get("stationname")!=null)
			{
				detachedCriteria.add(Restrictions.like("name", "%"+map.get("stationname")+"%"));
			}
			//岗位名称
			if(map.get("postname")!=null)
			{
				detachedCriteria.add(Restrictions.like("posts.postName", "%"+map.get("postname")+"%"));
			}
			//是否启用
			if(map.get("isWork")!=null)
			{
				detachedCriteria.add(Restrictions.eq("isWork", map.get("isWork")));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//未删除的
		return super.findByPager(pager, detachedCriteria);
	}

	/**
	 * 根据编码查询
	 */
	@SuppressWarnings("unchecked")
	public Station getByCode(String stationcode)
	{
		String hql="from Station where code=?";
		List<Station>list=this.getSession().createQuery(hql).setParameter(0, stationcode).list();
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据岗位ID获取对应的工位
	 */
	@SuppressWarnings("unchecked")
	public List<Station> getStationsByPostid(String postid)
	{
		String hql="from Station where isDel='N' and isWork='Y' and posts.id=?";
		return this.getSession().createQuery(hql).setParameter(0, postid).list();
	}

}
