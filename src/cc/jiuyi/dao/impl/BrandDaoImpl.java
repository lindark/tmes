package cc.jiuyi.dao.impl;

import java.util.Set;

import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Product;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 品牌
 */

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Product> productSet = brand.getProductSet();
		if (productSet != null) {
			for (Product product : productSet) {
				product.setBrand(null);
			}
		}
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}