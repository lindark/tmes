package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.ReturnProduct;

/**
 * Dao接口 - 成品入库
 */
public interface ReturnProductDao extends BaseDao<ReturnProduct, String> {

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	public List<ReturnProduct> historyExcelExport(HashMap<String,String>map);
}
