package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PositionManagementDao;
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
	
	
}
