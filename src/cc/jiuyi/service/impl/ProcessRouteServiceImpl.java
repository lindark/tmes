package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.Bom;
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
	
	public Integer getMaxVersion(String matnr,Date productDate){
		return processroutedao.getMaxVersion(matnr, productDate);
	}
	
	public List<ProcessRoute> findProcessRoute(String aufnr,Date productDate) {
		Orders orders = ordersservice.get("aufnr",aufnr);
		Integer maxversion = processroutedao.getMaxVersion(orders.getMatnr(), productDate);
		return processroutedao.getProcessRouteList(aufnr, maxversion);
	}

	public Integer getMaxVersion(String aufnr) {
		return processroutedao.getMaxVersion(aufnr);
	}

}