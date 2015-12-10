package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	public List<EnteringwareHouse> getByBill(String workingBillId) {
		String hql = "from EnteringwareHouse as a where a.state='1' and a.workingbill.id=?";
		return getSession().createQuery(hql).setParameter(0, workingBillId).list();
	}



	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EnteringwareHouse.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if(map.get("workingbillCode")!=null){
			    detachedCriteria.add(Restrictions.eq("workingbillCode", map.get("workingbillCode")));
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
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
}