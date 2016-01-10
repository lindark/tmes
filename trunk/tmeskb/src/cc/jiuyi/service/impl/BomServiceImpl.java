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
import cc.jiuyi.entity.Orders;
import cc.jiuyi.service.BomService;

/**
 * Service实现类 - Bom
 */

@Service
public class BomServiceImpl extends BaseServiceImpl<Bom, String> implements BomService {

	@Resource
	private BomDao bomDao;
	@Resource
	private OrdersDao orderdao;

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
		Orders orders = orderdao.get("aufnr",aufnr);
		Integer maxversion = bomDao.getMaxVersion(orders.getMatnr(), productDate);
		return bomDao.getBomList(aufnr, maxversion);
	}


	@Override
	public Integer getMaxVersion(String aufnr) {
		return bomDao.getMaxVersion(aufnr);
	}


	@Override
	public List<Bom> getBomList(String aufnr, Integer maxversion) {
		return bomDao.getBomList(aufnr, maxversion);
	}
}