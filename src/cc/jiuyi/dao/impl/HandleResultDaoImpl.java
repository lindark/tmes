package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandleResultDao;
import cc.jiuyi.entity.HandlemeansResults;

/**
 * Dao实现类 - 故障处理方法与结果
 */

@Repository
public class HandleResultDaoImpl extends BaseDaoImpl<HandlemeansResults, String> implements HandleResultDao {

	public Pager getHandlePager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(HandlemeansResults.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("handleName")!=null){
			    detachedCriteria.add(Restrictions.like("handleName", "%"+map.get("handleName")+"%"));
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
			HandlemeansResults handleResult=super.load(id);
			handleResult.setIsDel(oper);//标记删除
			super.update(handleResult);
		}
   }

	@SuppressWarnings("unchecked")
	public List<HandlemeansResults> getAllHandle() {
		String hql = "from HandlemeansResults handlemeansResults where lower(handlemeansResults.isDel) = lower(?)";
		List<HandlemeansResults> handleList=getSession().createQuery(hql).setParameter(0, "N").list();
		return handleList;
	}
	
}
