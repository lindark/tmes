package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.EquipmentDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Equipment;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.EquipmentService;

/**
 * Service实现类 - 设备
 */

@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment, String> implements EquipmentService {

	@Resource
	private EquipmentDao equipmentDao;
	
	@Resource
	public void setBaseDao(EquipmentDao equipmentDao) {
		super.setBaseDao(equipmentDao);
	}

	@Override
	public Pager getEquipmentPager(Pager pager, HashMap<String, String> map) {
	
		return equipmentDao.getEquipmentPager(pager, map);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		equipmentDao.updateisdel(ids, oper);
	}

	@Override
	public boolean isExistByEquipmentNo(String equipmentNo) {
		// TODO Auto-generated method stub
		return equipmentDao.isExistByEquipmentNo(equipmentNo);
	}
}
