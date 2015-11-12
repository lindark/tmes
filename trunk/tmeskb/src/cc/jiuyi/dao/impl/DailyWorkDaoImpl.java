package cc.jiuyi.dao.impl;

import java.util.Map;


import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.DailyWork;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 报工
 */

@Repository
public class DailyWorkDaoImpl extends BaseDaoImpl<DailyWork, String> implements DailyWorkDao {
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map){
		String wheresql = super.pagerSqlByjqGrid(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DailyWork.class);
		if(!wheresql.equals("")){
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map != null && map.size()>0){
			
		}
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			DailyWork dailyWork = super.load(id);
			dailyWork.setIsDel(oper);
			super.update(dailyWork);
		}
		
		
	}


	
}