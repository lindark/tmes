package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.dao.ProductDao;
import cc.jiuyi.dao.ReceiverDao;
import cc.jiuyi.entity.Product;
import cc.jiuyi.entity.ProductCategory;
import cc.jiuyi.entity.Receiver;
import cc.jiuyi.service.ReceiverService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 收货地址
 */

@Service
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, String> implements ReceiverService {
	
	@Resource
	public void setBaseDao(ReceiverDao receiverDao) {
		super.setBaseDao(receiverDao);
	}

}