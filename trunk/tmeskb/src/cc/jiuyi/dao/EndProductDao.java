package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.EndProduct;

/**
 * Dao接口 - 成品入库
 */
public interface EndProductDao extends BaseDao<EndProduct, String> {

	public Pager getProductsPager(Pager pager);

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
}
