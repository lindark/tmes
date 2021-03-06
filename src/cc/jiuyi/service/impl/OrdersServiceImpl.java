package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.dao.AreaDao;
import cc.jiuyi.dao.OrdersDao;
import cc.jiuyi.entity.Area;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.service.AreaService;
import cc.jiuyi.service.OrdersService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 生产订单
 */

@Service
public class OrdersServiceImpl extends BaseServiceImpl<Orders, String> implements OrdersService {
	
	@Resource
	private OrdersDao orderdao;

	@Resource
	public void setBaseDao(OrdersDao orderdao) {
		super.setBaseDao(orderdao);
	}

	public Orders findOrders(String productDate,Integer version,String workcenter,String ordersid){
		return orderdao.findOrders(productDate,version,workcenter,ordersid);
	}
	public List<Orders> findOrders(String productDate){
		return orderdao.findOrders(productDate);
	}
	
	public List<Object[]> findOrders(){
		return orderdao.findOrders();
	}
}