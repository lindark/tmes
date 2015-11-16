package cc.jiuyi.dao.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.QualityDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Dao实现类 - 质量
 */

@Repository
public class QualityDaoImpl extends BaseDaoImpl<Quality, String> implements QualityDao {
    
	public Pager getQualityPager(Pager pager, HashMap<String, String> map) {
		String wheresql = qualitypagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Quality.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if (map.size() > 0) {
			if(map.get("team")!=null){
			    detachedCriteria.add(Restrictions.like("team", "%"+map.get("team")+"%"));
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

	public String qualitypagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp(), null) + " ";
				ishead = 1;
			}

		}
		return wheresql;
	}
	
}
