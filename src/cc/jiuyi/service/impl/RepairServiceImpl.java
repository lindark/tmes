package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.service.RepairService;

/**
 * Service实现类 返修
 */
@Service
@Transactional
public class RepairServiceImpl extends BaseServiceImpl<Repair, String> implements RepairService{
	@Resource
	private RepairDao repairDao;
	
	@Resource
	public void setBaseDao(RepairDao repairDao){
		super.setBaseDao(repairDao);
	}
	public Pager getRepairPager(Pager pager,HashMap<String,String> map){
		return repairDao.getRepairPager(pager,map);
	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		repairDao.updateisdel(ids, oper);
	}
}
