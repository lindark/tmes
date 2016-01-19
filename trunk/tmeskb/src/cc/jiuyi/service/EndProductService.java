package cc.jiuyi.service;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EndProduct;
/**
 * Service接口 - 成品入库
 */
public interface EndProductService extends BaseService<EndProduct, String> {
	void saveEndProduct(List<EndProduct> endProductList,String info,Admin admin);
	void updateApprovalEndProduct(String[] ids,Admin admin);
	void updateEidtEndProduct(String id,Admin admin,EndProduct endProduct,String info);
	//public Pager getPickPager(Admin admin,Pager pager, HashMap<String, String> map);
}
