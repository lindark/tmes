package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PositionManagementDao;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.PositionManagement;
import cc.jiuyi.service.PositionManagementService;


@Service
public class PositionManagementServiceImpl extends BaseServiceImpl<PositionManagement, String>implements PositionManagementService{
	@Resource
	private PositionManagementDao positionManagementDao;

	@Resource
	public void setBaseDao(PositionManagementDao positionManagementDao){
		super.setBaseDao(positionManagementDao);
	}
	
	@Override
	public Pager getPositionManagementPager(Pager pager,
			HashMap<String, String> map) {
		return positionManagementDao.getPositionManagementPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		positionManagementDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<PositionManagement> getPositionManagementList() {
		return positionManagementDao.getPositionManagementList();
	}

	@Override
	public List<String> getPositionList(PositionManagement positionManagement) {
		return positionManagementDao.getPositionList(positionManagement);
	}

	@Override
	public List<String> getPositionList1(PositionManagement positionManagement) {
		return positionManagementDao.getPositionList1(positionManagement);
	}
	
	
}
