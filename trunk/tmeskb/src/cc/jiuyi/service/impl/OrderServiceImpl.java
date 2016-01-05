package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.dao.AreaDao;
import cc.jiuyi.dao.OrderDao;
import cc.jiuyi.entity.Area;
import cc.jiuyi.entity.Order;
import cc.jiuyi.service.AreaService;
import cc.jiuyi.service.OrderService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 生产订单
 */

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
	
	@Resource
	private OrderDao orderdao;

	@Resource
	public void setBaseDao(OrderDao orderdao) {
		super.setBaseDao(orderdao);
	}


}