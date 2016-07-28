package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.QualityProblemDescriptionDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.QualityProblemDescription;


/**
 * Dao实现类 - 故障原因
 */

@Repository
public class QualityProblemDescriptionDaoImpl extends BaseDaoImpl<QualityProblemDescription, String> implements QualityProblemDescriptionDao {

	public Pager getQualityProblemDescriptionPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(QualityProblemDescription.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("problemDescription") != null){
			    detachedCriteria.add(Restrictions.like("problemDescription", "%"+map.get("problemDescription")+"%"));
			    //System.out.println(map.get("problemDescription"));
			}		
		
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			//System.out.println(map.get("problemDescription"));
			//System.out.println(map.get("state"));
		}
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}  				
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			QualityProblemDescription qpd=super.load(id);
			qpd.setIsDel(oper);//标记删除
			super.update(qpd);
		}
   }

	@SuppressWarnings("unchecked")
	public List<QualityProblemDescription> getAllQualityProblemDescription() {
		String hql = "from QualityProblemDescription qpd where lower(qpd.isDel) = lower(?)";
		List<QualityProblemDescription> qpdList=getSession().createQuery(hql).setParameter(0, "N").list();
		return qpdList;
	}
	
}
