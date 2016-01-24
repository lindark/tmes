package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.dao.UnitdistributeProductDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.UnitdistributeProductService;

/**
 * Service实现类 -单元分配产品
 *
 */

@Service
public class UnitdistributeProductServiceImpl extends BaseServiceImpl<UnitdistributeProduct, String>implements UnitdistributeProductService{

	@Resource
	private UnitdistributeProductDao unitdistributeProductDao;
	
	@Resource
	public void setBaseDao(UnitdistributeProductDao unitdistributeProductDao){
		super.setBaseDao(unitdistributeProductDao);
	}

	@Override
	public Pager getUnitProductPager(Pager pager, HashMap<String, String> map) {
		return unitdistributeProductDao.getUnitProductPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		unitdistributeProductDao.updateisdel(ids, oper);
		
	}

}
