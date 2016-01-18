package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.EndProduct;
/**
 * Service接口 - 成品入库
 */
public interface EndProductService extends BaseService<EndProduct, String> {
	void saveEndProduct(List<EndProduct> endProductList);
}
