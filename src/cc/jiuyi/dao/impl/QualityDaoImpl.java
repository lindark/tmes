package cc.jiuyi.dao.impl;
import java.util.HashMap;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.QualityDao;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Dao实现类 - 质量
 */

@Repository
public class QualityDaoImpl extends BaseDaoImpl<Quality, String> implements QualityDao {
    
	public Pager getQualityPager(Pager pager,HashMap<String,String> map){
		String wheresql = super.pagerSqlByjqGrid(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Quality.class);
		if(!wheresql.equals("")){
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map != null && map.size()>0){
			if(!map.get("start").equals("") || !map.get("end").equals("")){//生产日期范围
				String start = map.get("start").equals("")? ThinkWayUtil.SystemDate() : map.get("start").toString();
				String end = map.get("end").equals("")? ThinkWayUtil.SystemDate() : map.get("end").toString();
				detachedCriteria.add(Restrictions.between("createDate", start, end));
			}
			if(!map.get("state").equals("")){
				detachedCriteria.add(Restrictions.like("state","%"+map.get("state").toString()+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
}
