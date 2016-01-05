package cc.jiuyi.dao.impl;


import cc.jiuyi.dao.OrderDao;
import cc.jiuyi.entity.Order;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 生产订单
 */

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao {
	
}