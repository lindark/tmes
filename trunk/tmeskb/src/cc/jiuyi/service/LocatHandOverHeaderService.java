package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.LocatHandOverHeader;

/**
 * Service接口 线边仓交接-主表
 */
public interface LocatHandOverHeaderService extends BaseService<LocatHandOverHeader, String> {
	public Pager jqGrid(Pager pager,Admin admin);
	void saveLocatHandOver(Admin admin, List<LocatHandOver> locatHandOverList,Admin admin1);

	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
	

	void updateLocatHandOver(Admin admin,String[] ids);

}
