package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Product;
import cc.jiuyi.entity.Message.DeleteStatus;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 随工单
 */

@Repository
public class EnteringwareHouseDaoImpl extends BaseDaoImpl<EnteringwareHouse, String> implements EnteringwareHouseDao {
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map){
		String wheresql = super.pagerSqlByjqGrid(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EnteringwareHouse.class);
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
			EnteringwareHouse enteringwareHourse = super.load(id);
			enteringwareHourse.setIsdel(oper);//标记删除
			super.update(enteringwareHourse);
		}
		
		
	}


	
}