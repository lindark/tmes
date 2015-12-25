package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 工艺路线
 */

public interface ProcessRouteService extends BaseService<ProcessRoute, String> {

	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);

	/**
	 * 根据processroute 集合 merge
	 * 
	 * @param processRouteList
	 */
	public void mergeProcessroute(List<ProcessRoute> processRouteList,
			String productid);

	/**
	 * 获取最高版本号
	 * 
	 * @return
	 */
	public Integer getMaxVersion(String productid);
	
	/**
	 * 获取最高版本号-根据产品名称
	 * @return
	 */
	public Integer getMaxVersionBycode(String productcode);

	/**
	 * 根据产品编码获取最高版本号
	 * 
	 * @param productCode
	 * @return
	 */
	public Integer getMaxVersionByCode(String productCode);

	/**
	 * 根据产品编码获取最高版本的工艺路线
	 * 
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteByProductCode(String productCode);

	/**
	 * 根据产品编号，获取所有相关工序，包括历史版本
	 * 
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getAllProcessRouteByProductCode(String productCode);

	/**
	 * 根据版本号和产品编号获取相关工序
	 * 
	 * @param version
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteByVersionAndCode(Integer version,
			String productCode);
}