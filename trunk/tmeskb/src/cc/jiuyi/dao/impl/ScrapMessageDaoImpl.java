package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapMessageDao;
import cc.jiuyi.entity.Factory;
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
		String hql="from ScrapMessage where isDel='N' and scrap_id=? and smmatterNum=?";
		return (ScrapMessage) this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, mid).uniqueResult();
	}

	@Override
	public Pager getMessagePager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ScrapMessage.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		
		if (map.size() > 0) {
			/*if(map.get("maktx")!=null){
			    detachedCriteria.add(Restrictions.like("factoryCode", "%"+map.get("maktx")+"%"));
			}	*/	
			
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
