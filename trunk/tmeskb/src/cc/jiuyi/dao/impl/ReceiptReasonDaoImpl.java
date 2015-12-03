package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReceiptReasonDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.ReceiptReason;

/**
 * Dao实现类 - 单据原因
 */

@Repository
public class ReceiptReasonDaoImpl extends BaseDaoImpl<ReceiptReason, String> implements ReceiptReasonDao {

	public Pager getReceiptReasonPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ReceiptReason.class);
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
			ReceiptReason receiptReason=super.load(id);
			receiptReason.setIsDel(oper);//标记删除
			super.update(receiptReason);
		}
   }

	@Override
	public List<ReceiptReason> getReceiptReasonByType(String type) {
		String hql = "from ReceiptReason receiptReason where lower(receiptReason.type) = lower(?)";
		List<ReceiptReason> receiptReasonList=getSession().createQuery(hql).setParameter(0,type).list();
		return receiptReasonList;
	}

}
