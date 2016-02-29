package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapLaterDao;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;

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
	
	@Override
	public Pager getLaterPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ScrapLater.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if (map.size() > 0) {					
			/*if(map.get("factoryName")!=null){
				detachedCriteria.add(Restrictions.like("factoryName", "%"+map.get("factoryName")+"%"));
			}*/
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}	
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

}
