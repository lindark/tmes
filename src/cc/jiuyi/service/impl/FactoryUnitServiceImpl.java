package cc.jiuyi.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryUnitDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.service.FactoryUnitService;

/**
 * Service实现类 -单元管理
 * @author Reece
 *
 */

@Service
public class FactoryUnitServiceImpl extends BaseServiceImpl<FactoryUnit, String>implements FactoryUnitService{

	@Resource
	private FactoryUnitDao factoryUnitDao;
	
	@Resource
	public void setBaseDao(FactoryUnitDao factoryUnitDao){
		super.setBaseDao(factoryUnitDao);
	}
	
	@Override
	public void delete(String id) {
		FactoryUnit factoryUnit = factoryUnitDao.load(id);
		this.delete(factoryUnit);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<FactoryUnit> getFactoryUnitList() {		
		return factoryUnitDao.getFactoryUnitList();
	}

	@Override
	public Pager getFactoryUnitPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return factoryUnitDao.getFactoryUnitPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		factoryUnitDao.updateisdel(ids, oper);
		
	}

	@Override
	public boolean isExistByFactoryUnitCode(String factoryUnitCode) {
		// TODO Auto-generated method stub
		return factoryUnitDao.isExistByFactoryUnitCode(factoryUnitCode);
	}

	
}
