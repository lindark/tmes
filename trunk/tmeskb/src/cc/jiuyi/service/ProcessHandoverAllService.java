package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverAll;
/**
 * Service接口 - 总体工序交接
 */
public interface ProcessHandoverAllService extends BaseService<ProcessHandoverAll, String>{
		public void saveAllProcess(Admin admin);
		
		public Pager jqGrid(Pager pager,Admin admin);
		
		public List<ProcessHandoverAll> getListOfAllProcess(String productDate,String shift,String factoryId); 
}
