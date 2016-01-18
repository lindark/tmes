package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.EndProductDao;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.service.EndProductService;

/**
 * Service实现类 -成品入库
 */

@Service
public class EndProductServiceImpl extends BaseServiceImpl<EndProduct, String> implements EndProductService {
	@Resource
	 EndProductDao  endProductDao;
	
	@Resource
	public void setBaseDao(EndProductDao endProductDao) {
		super.setBaseDao(endProductDao);
	}

	@Override
	public void saveEndProduct(List<EndProduct> endProductList) {
		
	}
	
}
