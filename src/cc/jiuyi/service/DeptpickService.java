package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;

/**
 * Service接口 - 部门领料
 */
public interface DeptpickService extends BaseService<Deptpick, String> {
	
	
	public void saveDeptpickList(List<Deptpick> deptpickList);
	
	public void updateDeptpickList(List<Deptpick> deptpickList,Admin admin);
	
	public Pager findByPager(Pager pager,Admin admin);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
		
	public List<Deptpick> historyExcelExport(HashMap<String,String> map);
}
