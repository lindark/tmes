package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.EndProduct;

/**
 * Service接口 - 成品入库
 */
public interface EndProductService extends BaseService<EndProduct, String> {
	void saveEndProduct(List<EndProduct> endProductList, String info,Admin admin,String productDate,String shift);

	void updateApprovalEndProduct(String[] ids, Admin admin);

	void updateEidtEndProduct(String id, Admin admin, EndProduct endProduct,
			String info,String productDate,String shift);
	public Pager getProductsPager(Pager pager,String productDate,String shift);
	
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	/**
	 * 刷卡撤销
	 * @author Reece
	 * @param list
	 * @param cardnumber
	 */
	public void updateCancel(List<EndProduct> list,String cardnumber);
	
	/**
	 * 取出所有已经确认且生产日期和班次匹配的对象
	 * @author Reece
	 * @return
	 */
	public List<EndProduct> getListChecked(String productDate, String shift);
	
	public List<EndProduct> historyExcelExport(HashMap<String,String>map);
}
