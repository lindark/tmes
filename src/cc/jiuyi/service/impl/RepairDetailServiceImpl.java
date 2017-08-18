package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDetailDao;
import cc.jiuyi.entity.RepairDetail;
import cc.jiuyi.service.RepairDetailService;

/**
 * Service实现类 -
 * @author 
 *
 */

@Service
public class RepairDetailServiceImpl extends BaseServiceImpl<RepairDetail, String>implements RepairDetailService{

	@Resource
	private RepairDetailDao repairDetailDao;
	
	@Resource
	public void setBaseDao(RepairDetailDao repairDetailDao){
		super.setBaseDao(repairDetailDao);
	}
	
	@Override
	public void delete(String id) {
		RepairDetail repairDetail = repairDetailDao.load(id);
		this.delete(repairDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<RepairDetail> getPickList() {
		return repairDetailDao.getPickList();
	}

	@Override
	public List<RepairDetail> getPickList(String processids) {
		return repairDetailDao.getPickList(processids);
	}

	@Override
	public Pager getRepairDetailPager(Pager pager, HashMap<String, String> map) {
		return repairDetailDao.getRepairDetailPager(pager, map);
	}

	
	

}
