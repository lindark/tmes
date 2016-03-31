package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.EquipmentDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Equipment;
import cc.jiuyi.entity.Equipments;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.EquipmentService;

/**
 * Service实现类 - 设备
 */

@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipments, String> implements EquipmentService {

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
		equipmentDao.updateisdel(ids, oper);
	}

	@Override
	public boolean isExistByEquipmentNo(String equipmentNo) {
		return equipmentDao.isExistByEquipmentNo(equipmentNo);
	}
	
	//同步管理
	public void saveorupdate(List<Equipments> equipmentList){
		for(int i=0;i<equipmentList.size();i++){
			Equipments equipment = equipmentList.get(i);
			Equipments equipment01 = equipmentDao.isExist1("equipmentNo", equipment.getEquipmentNo());
			if(equipment01 == null){//没找到
				equipmentDao.save(equipment);
			}else{//找到
				equipment.setId(equipment01.getId());
				equipmentDao.merge(equipment);
			}
		}
	}
}
