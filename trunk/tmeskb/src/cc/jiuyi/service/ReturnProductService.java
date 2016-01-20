package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.ReturnProduct;
/**
 * Service接口 - 成品入库
 */
public interface ReturnProductService extends BaseService<ReturnProduct, String> {
	void saveReturnProduct(List<ReturnProduct> returnProductList,String info,Admin admin);
	void updateApprovalReturnProduct(String[] ids,Admin admin);
	void updateEidtReturnProduct(String id,Admin admin,ReturnProduct returnProduct,String info);
}
