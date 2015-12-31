package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.HandOverDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.HandOverService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 品牌
 */

@Service
public class HandOverServiceImpl extends BaseServiceImpl<HandOver, String> implements HandOverService {

	@Resource
	public void setBaseDao(HandOverDao handoverdao) {
		super.setBaseDao(handoverdao);
	}

}