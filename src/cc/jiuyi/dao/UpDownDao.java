package cc.jiuyi.dao;


import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.UpDown;

/**
 * Dao接口 - 上架/下架
 */

public interface UpDownDao extends BaseDao<UpDown, String> {
	public Pager findByPager(Pager pager,Admin admin,List<String> list);
    
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
		
    public List<UpDown> historyExcelExport(HashMap<String,String> map);
	
}
