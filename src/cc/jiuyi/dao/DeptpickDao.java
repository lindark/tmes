package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.EndProduct;

/**
 * Dao接口 - 部门领料
 */
public interface DeptpickDao extends BaseDao<Deptpick, String> {

	public Pager findByPager(Pager pager,Admin admin);
	
    public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Deptpick> historyExcelExport(HashMap<String,String> map);
	
}
