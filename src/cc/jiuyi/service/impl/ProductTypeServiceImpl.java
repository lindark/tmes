package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.ProductTypeDao;
import cc.jiuyi.entity.ProductType;
import cc.jiuyi.service.ProductTypeService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 商品类型
 */

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements
		ProductTypeService {

	@Resource
	public void setBaseDao(ProductTypeDao productTypeDao) {
		super.setBaseDao(productTypeDao);
	}

}
