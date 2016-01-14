package cc.jiuyi.dao.impl;


import java.util.List;

import cc.jiuyi.dao.OrdersDao;
import cc.jiuyi.entity.Orders;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 生产订单
 */

@Repository
public class OrdersDaoImpl extends BaseDaoImpl<Orders, String> implements OrdersDao {

	public Orders findOrders(String productDate,Integer version,String workcenter,String ordersid) {
		String hql="select a from Orders a join a.processrouteSet b where a.gstrp <= ? and a.gltrp >= ? and b.version = ? and b.workCenter = ? and b.effectiveDate <= ? and a.id = ? order by b.processCode asc";
		return (Orders) getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, productDate)
				.setParameter(2, version).setParameter(3, workcenter).setParameter(4, productDate).setParameter(5, ordersid).setMaxResults(1).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Orders> findOrders(String productDate){
		String hql="from Orders a where a.gstrp <= ? and a.gltrp >= ?";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, productDate).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findOrders(){
//		String hql="select max(b.version),a.id from Orders a join a.processrouteSet b group by a.id";
//		List<Object[]> obj = getSession().createQuery(hql).list();
		//return obj;
		return null;
	}
	
	
}