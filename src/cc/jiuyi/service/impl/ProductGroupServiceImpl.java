package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.dao.ProductGroupDao;
import cc.jiuyi.entity.ProductGroup;
import cc.jiuyi.service.ProductGroupService;

/**
 * Service实现类 产品组管理
 */
@Service
@Transactional
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroup, String> implements ProductGroupService {
	@Resource
	private ProductGroupDao productGroupDao;
	@Resource
	public void setBaseDao(ProductGroupDao productGroupDao) {
		super.setBaseDao(productGroupDao);
	}
	public Pager getProductGroupPager(Pager pager, HashMap<String, String> map) {
		return productGroupDao.getProductGroupPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		productGroupDao.updateisdel(ids, oper);
	}
}
