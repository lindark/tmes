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
	 * 根据订单编号+版本获取工艺路线
	 * @param aufnr
	 * @param version
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteList(String aufnr,Integer version);
	
	/**
	 * 根据物料号+生产日期获取最大版本
	 * @param matnr
	 * @param productDate
	 * @return
	 */
	public Integer getMaxVersion(String matnr,String productDate);
	
	/**
	 * 根据生产订单获取最大版本号
	 * @param aufnr
	 * @return
	 */
	public Integer getMaxVersion(String aufnr);

	/**
	 * 生产订单号,版本号,编码查询一条工艺路线
	 * @param aufnr
	 * @param maxversion
	 * @param processCode
	 * @return
	 */
	public ProcessRoute getOneByConditions(String aufnr, Integer maxversion,
			String processCode);

}
