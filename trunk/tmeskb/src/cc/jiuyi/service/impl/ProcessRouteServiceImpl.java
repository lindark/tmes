package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProcessRouteService;

/**
 * Service实现类 - 文章
 */

@Service
public class ProcessRouteServiceImpl extends BaseServiceImpl<ProcessRoute, String> implements ProcessRouteService {

	@Resource
	private ProcessRouteDao processroutedao;
	@Resource
	private OrdersService ordersservice;

	@Resource
	public void setBaseDao(ProcessRouteDao processroutedao) {
		super.setBaseDao(processroutedao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return processroutedao.findPagerByjqGrid(pager, map);
	}
	
	public List<ProcessRoute> getProcessRouteList(String aufnr, Integer version) {
		return processroutedao.getProcessRouteList(aufnr, version);
	}
	
	public Integer getMaxVersion(String orderid,String productDate){
		return processroutedao.getMaxVersion(orderid, productDate);
	}
	
	public List<ProcessRoute> findProcessRoute(String aufnr,String productDate) {
		Orders orders = ordersservice.get("aufnr",aufnr);
		Integer maxversion = processroutedao.getMaxVersion(orders.getId(), productDate);
		if(maxversion!=null)
		{
			return processroutedao.getProcessRouteList(aufnr, maxversion);
		}
		return null;
	}
	
	public List<ProcessRoute> findProcessRoute(String aufnr) {
		Orders orders = ordersservice.get("aufnr",aufnr);
		Integer maxversion = processroutedao.getMaxVersion(orders.getId());
		if(maxversion!=null)
		{
			return processroutedao.getProcessRouteList(aufnr, maxversion);
		}
		return null;
	}

	public Integer getMaxVersion(String aufnr) {
		return processroutedao.getMaxVersion(aufnr);
	}

	/**
	 * 生产订单号,日期,编码查询一条工艺路线
	 */
	public ProcessRoute getOneByConditions(String aufnr, String productDate,
			String processCode)
	{
		Orders orders = ordersservice.get("aufnr",aufnr);
		Integer maxversion = processroutedao.getMaxVersion(orders.getId(), productDate);
		if(maxversion!=null)
		{
			return processroutedao.getOneByConditions(aufnr, maxversion,processCode);
		}
		return null;
	}

	@Override
	public String getProcess(List<String> processRouteIdList,String steus) {
		return processroutedao.getProcess(processRouteIdList,steus);
	}

	public List<Object[]> getMaxVersion1(String workcenter){
		return processroutedao.getMaxVersion1(workcenter);
	}
	
	
	public List<Object[]> getMaxVersion(List<String> orderidList){
		return processroutedao.getMaxVersion(orderidList);
	}
	
	public ProcessRoute getProcessRoute(Integer version,String orderid){
		return processroutedao.getProcessRoute(version, orderid);
	}
	 public String getProcessName(String processCode){
    	 return processroutedao.getProcessName(processCode);
     }
}