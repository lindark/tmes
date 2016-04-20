package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ReturnProduct;

/**
 * Dao接口 - 成品入库
 */
public interface ReturnProductDao extends BaseDao<ReturnProduct, String> {
	public Pager jqGrid(Pager pager,Admin admin);
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	public List<ReturnProduct> historyExcelExport(HashMap<String,String>map);
	
}
