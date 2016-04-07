package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UnitdistributeModelDao;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.service.UnitdistributeModelService;

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
		return unitdistributeModelDao.getModelList(unitCode);
	}

	/**
	 * 查询所有工作范围
	 */
	public List<UnitdistributeModel> getAllList()
	{
		return this.unitdistributeModelDao.getAllList();
	}

	@Override
	public Pager getUBMList(Pager pager, HashMap<String, String> map) {
		return unitdistributeModelDao.getUBMList(pager,map);
	}

	/**
	 * 根据单元id和模具组号查询
	 */
	public UnitdistributeModel getByConditions(String fuid, String station)
	{
		return this.unitdistributeModelDao.getByConditions(fuid,station);
	}

}
