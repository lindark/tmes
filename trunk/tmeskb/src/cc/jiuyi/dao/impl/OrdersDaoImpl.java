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

	public Orders findOrders(String productDate,Integer version,String steus,String workcenter) {
		String hql="select a from Orders a join a.processrouteSet b where a.gstrp <= ? and a.gltrp >= ? and b.version = ? and b.steus = ? and b.workCenter = ? and b.effectiveDate <= ?";
		return (Orders) getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, productDate)
				.setParameter(2, version).setParameter(3, steus).setParameter(4, workcenter).setParameter(5, productDate).setMaxResults(1).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orders> findOrders(String productDate){
		String hql="from Orders a where a.gstrp <= ? and a.gltrp >= ?";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, productDate).list();
	}
	
}