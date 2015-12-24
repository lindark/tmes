package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProcessRoute;


/**
 * Dao接口 - 工艺路线
 */
public interface ProcessRouteDao extends BaseDao<ProcessRoute, String> {

	public Pager findPagerByjqGrid(Pager pager,HashMap<String,String>map);
	
	/**
	 * 获取最高版本号
	 * @return
	 */
	public Integer getMaxVersion(String productid);
	
	/**
	 * 根据产品编码获取最高版本号
	 * @param productCode
	 * @return
	 */
	public Integer getMaxVersionByCode(String productCode);
	
	/**
	 * 根据产品编码获取最高版本的工艺路线
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteByProductCode(String productCode);
	
	/**
	 * 根据产品编号，获取所有相关工序，包括历史版本
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getAllProcessRouteByProductCode(String productCode);
	
	/**
	 * 根据版本号和产品编号获取相关工序
	 * @param version
	 * @param productCode
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteByVersionAndCode(Integer version,String productCode);
}
