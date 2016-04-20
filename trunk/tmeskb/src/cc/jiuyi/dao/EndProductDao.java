package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.EndProduct;

/**
 * Dao接口 - 成品入库
 */
public interface EndProductDao extends BaseDao<EndProduct, String> {

	public Pager getProductsPager(Pager pager,String productDate, String shift,String factoryCode);

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	
	/**
	 * 取出所有已经确认且生产日期和班次匹配的对象
	 * @author Reece
	 * @return
	 */
	public List<EndProduct> getListChecked(String productDate, String shift);
	
	public List<EndProduct> historyExcelExport(HashMap<String,String>map);
}
