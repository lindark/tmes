package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;


/**
 * Dao实现类 - 故障原因
 */

@Repository
public class FaultReasonDaoImpl extends BaseDaoImpl<FaultReason, String> implements FaultReasonDao {

	public Pager getFaultReasonPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(FaultReason.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		
		if (map.size() > 0) {
			if(map.get("reasonName")!=null){
			    detachedCriteria.add(Restrictions.like("reasonName", "%"+map.get("reasonName")+"%"));
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
			FaultReason faultReason=super.load(id);
			faultReason.setIsDel(oper);//标记删除
			super.update(faultReason);
		}
   }

	@SuppressWarnings("unchecked")
	public List<FaultReason> getAllFaultReason() {
		String hql = "from FaultReason faultReason where lower(faultReason.isDel) = lower(?)";
		List<FaultReason> faultReasonList=getSession().createQuery(hql).setParameter(0, "N").list();
		return faultReasonList;
	}
	
}
