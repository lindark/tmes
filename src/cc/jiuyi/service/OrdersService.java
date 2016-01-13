package cc.jiuyi.service;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Role;

/**
 * Service接口 - 生产订单
 */

public interface OrdersService extends BaseService<Orders, String> {
	

	/**
	 * 获取生产订单的集合
	 * @param productDate
	 * @param maxversion
	 * @param steus
	 * @param workcenter
	 * @return
	 */
	public Orders findOrders(String productDate,Integer maxversion,String steus,String workcenter);
	
	/**
	 * 获取生产订单的集合
	 * @param productDate
	 * @return
	 */
	public List<Orders> findOrders(String productDate);
}