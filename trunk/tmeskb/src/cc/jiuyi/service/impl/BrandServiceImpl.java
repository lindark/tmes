package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.service.BrandService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 品牌
 */

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

	@Resource
	public void setBaseDao(BrandDao brandDao) {
		super.setBaseDao(brandDao);
	}

}