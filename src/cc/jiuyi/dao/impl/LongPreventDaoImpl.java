package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.LongPreventDao;
import cc.jiuyi.entity.LongtimePreventstep;

/**
 * Dao实现类 - 长期预防措施
 */

@Repository
public class LongPreventDaoImpl extends BaseDaoImpl<LongtimePreventstep, String> implements LongPreventDao {

	public Pager getLongPreventPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(LongtimePreventstep.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("type")!=null){
			    detachedCriteria.add(Restrictions.like("type", "%"+map.get("type")+"%"));
			}		
		
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}			
		}
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			LongtimePreventstep lp=super.load(id);
			lp.setIsDel(oper);//标记删除
			super.update(lp);
		}
   }

	@SuppressWarnings("unchecked")
	public List<LongtimePreventstep> getAllLongPrevent() {
		String hql = "from LongtimePreventstep longtimePreventstep where lower(longtimePreventstep.isDel) = lower(?)";
		List<LongtimePreventstep> longPreventList=getSession().createQuery(hql).setParameter(0, "N").list();
		return longPreventList;
	}
	
}
