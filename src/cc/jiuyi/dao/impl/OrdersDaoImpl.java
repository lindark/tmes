package cc.jiuyi.dao.impl;


import cc.jiuyi.dao.OrdersDao;
import cc.jiuyi.entity.Orders;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 生产订单
 */

@Repository
public class OrdersDaoImpl extends BaseDaoImpl<Orders, String> implements OrdersDao {
	
}