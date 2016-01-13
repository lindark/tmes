package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.BomDao;
import cc.jiuyi.dao.OrdersDao;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.OrdersService;

/**
 * Service实现类 - Bom
 */

@Service
public class BomServiceImpl extends BaseServiceImpl<Bom, String> implements BomService {

	@Resource
	private BomDao bomDao;
	@Resource
	private OrdersService orderservice;

	@Resource
	public void setBaseDao(BomDao bomDao) {
		super.setBaseDao(bomDao);
	}


	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return bomDao.findPagerByjqGrid(pager, map);
	}


	@Override
	public List<Bom> findBom(String aufnr,String productDate) {
		Orders orders = orderservice.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxversion(orders.getId(),productDate);
		return bomDao.getBomList(aufnr, maxversion);
	}
	
	public List<Bom> findBom(String aufnr,String productDate,String materialCode){
		Orders orders = orderservice.get("aufnr",aufnr);//获取生产订单号
		Integer maxversion = bomDao.getMaxversion(orders.getId(),productDate);
		return bomDao.getBomList(aufnr, maxversion,materialCode);
		
	}


	@Override
	public Integer getMaxVersion(String aufnr) {
		return bomDao.getMaxVersion(aufnr);
	}


	@Override
	public List<Bom> getBomList(String aufnr, Integer maxversion) {
		return bomDao.getBomList(aufnr, maxversion);
	}
	
	/**
	 * jqGrid:(根据:子件编码/名称,随工单)查询
	 */
	public Pager getPieceByCondition(Pager pager,HashMap<String, String> map,WorkingBill wb)
	{
		String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
		String productDate = wb.getProductDate();
		Orders orders = orderservice.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxVersion(orders.getMatnr(), productDate);
		if(maxversion!=null)
		{
			return this.bomDao.getPieceByCondition(pager,map, aufnr, maxversion);
		}
		return null;
	}


	@Override
	public Integer getMaxversion(String orderId, String productDate) {
		return bomDao.getMaxversion(orderId, productDate);
	}
	
}