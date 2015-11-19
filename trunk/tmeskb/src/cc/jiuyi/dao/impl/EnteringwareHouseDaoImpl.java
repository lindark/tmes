package cc.jiuyi.dao.impl;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.entity.EnteringwareHouse;

/**
 * Dao实现类 - 随工单
 */

@Repository
public class EnteringwareHouseDaoImpl extends BaseDaoImpl<EnteringwareHouse, String> implements EnteringwareHouseDao {
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map, String workingbillId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EnteringwareHouse.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager,detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			EnteringwareHouse enteringwareHourse = super.load(id);
			enteringwareHourse.setIsdel(oper);//标记删除
			super.update(enteringwareHourse);
		}
		
		
	}



	@Override
	public Integer getSingleConversationRatio(String unitDescription,
			String convertUnit) {
		String hql = "select conversationRatio from unitConversion where unitDescription=? and convertUnit=?";
		return (Integer) getSession().createQuery(hql).setParameter(0, unitDescription).setParameter(1, convertUnit).uniqueResult();
	}


	
}