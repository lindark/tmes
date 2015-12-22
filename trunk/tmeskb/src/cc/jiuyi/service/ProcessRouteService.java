package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 工艺路线
 */

public interface ProcessRouteService extends BaseService<ProcessRoute, String> {
	
	/**
	 * 根据processroute 集合 merge
	 * @param processRouteList
	 */
	public void mergeProcessroute(List<ProcessRoute> processRouteList,String productid);
}