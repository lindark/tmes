package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FaultReasonDao;
import cc.jiuyi.dao.UnitdistributeModelDao;
import cc.jiuyi.dao.UnitdistributeProductDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;

/**
 * Service实现类 -单元分配模具
 *
 */

@Service
public class UnitdistributeModelServiceImpl extends BaseServiceImpl<UnitdistributeModel, String>implements UnitdistributeModelService{

	@Resource
	private UnitdistributeModelDao unitdistributeModelDao;
	
	@Resource
	public void setBaseDao(UnitdistributeModelDao unitdistributeModelDao){
		super.setBaseDao(unitdistributeModelDao);
	}

	@Override
	public Pager getUnitModelPager(Pager pager, HashMap<String, String> map) {
		return unitdistributeModelDao.getUnitModelPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		unitdistributeModelDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<UnitdistributeModel> getModelList(String unitCode) {
		// TODO Auto-generated method stub
		return unitdistributeModelDao.getModelList(unitCode);
	}

}
