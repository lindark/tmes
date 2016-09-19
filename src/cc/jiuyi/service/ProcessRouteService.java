package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProcessRoute;

/**
 * Service接口 - 工艺路线
 */

public interface ProcessRouteService extends BaseService<ProcessRoute, String> {

	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);
	
	/**
	 * 根据订单编号+版本获取工艺路线
	 * @param aufnr
	 * @param version
	 * @return
	 */
	public List<ProcessRoute> getProcessRouteList(String aufnr, Integer version);
	
	/**
	 * 根据生产订单ID+生产日期获取最大版本
	 * @param orderid
	 * @param productDate
	 * @return
	 */
	public Integer getMaxVersion(String orderid,String productDate);
	
	public List<ProcessRoute> findProcessRoute(String aufnr,String productDate);
	
	public List<ProcessRoute> findProcessRoute(String aufnr);
	
	/**
	 * 根据生产订单获取最大版本号
	 * @param aufnr
	 * @return
	 */
	public Integer getMaxVersion(String aufnr);

	/**
	 * 生产订单号,日期,编码查询一条工艺路线
	 * @param aufnr
	 * @param productDate
	 * @param processCode
	 * @return
	 */
	public ProcessRoute getOneByConditions(String aufnr, String productDate,
			String processCode);
	
	/*
	 * 获取工序
	 * */
     public String getProcess(List<String> processRouteIdList,String steus);    
     
     
    /*通过工序编号获取工序名
     * 
     * */ 
     public String getProcessName(String processCode);
     
	
     public List<Object[]> getMaxVersion1(String workcenter);
     public List<Object[]> getMaxVersion(List<String> orderidList);
     public ProcessRoute getProcessRoute(Integer version,String orderid);
}