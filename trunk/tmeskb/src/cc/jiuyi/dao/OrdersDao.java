package cc.jiuyi.dao;

import java.util.List;

import cc.jiuyi.entity.Orders;

/**
 * Dao接口 - 生产订单
 */

public interface OrdersDao extends BaseDao<Orders, String> {


	/**
	 * 获取生产订单
	 * @param productDate
	 * @param version
	 * @param steus
	 * @param workcenter
	 * @return
	 */
	public Orders findOrders(String productDate,Integer version,String workcenter,String ordersid);
	
	public List<Orders> findOrders(String productDate);
	public List<Object[]> findOrders();
}