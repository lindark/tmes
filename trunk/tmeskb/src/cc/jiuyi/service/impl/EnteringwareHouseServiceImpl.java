package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.EnteringwareHouseService;
import cc.jiuyi.service.WorkingBillService;

//import org.springmodules.cache.annotations.CacheFlush;

/**
 * Service实现类 - 入库
 */

@Service
public class EnteringwareHouseServiceImpl extends
		BaseServiceImpl<EnteringwareHouse, String> implements
		EnteringwareHouseService {
	@Resource
	private EnteringwareHouseDao enteringwareHouseDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(EnteringwareHouseDao enteringwareHouse) {
		super.setBaseDao(enteringwareHouse);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map, String workingbillId) {
		return enteringwareHouseDao
				.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public List<EnteringwareHouse> getByBill(String workingBillId) {
		return enteringwareHouseDao.getByBill(workingBillId);
	}

	@Override
	public synchronized void updateState(List<EnteringwareHouse> list,
			String state, String workingbillid, Integer ratio, String cardnumber) {
		HashMap<String,Object> maps = new HashMap<String,Object>();
		maps.put("id",workingbillid);
		maps.put("state", state);
		maps.put("cardno", cardnumber);
		maps.put("ratio", ratio.toString());
		
		updateWorkingInoutCalculate(list,maps);

	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return enteringwareHouseDao.historyjqGrid(pager, map);
	}

	@Override
	public void updateWorkingInoutCalculate(
		List<EnteringwareHouse> list,HashMap<String,Object> maps) {
		String card=(String)maps.get("cardno");
		Admin admin = adminservice.getByCardnum(card);
		String workid=(String)maps.get("id");
		String state=(String)maps.get("state");
		String ratio=(String)maps.get("ratio");
		Integer ratio1=Integer.parseInt(ratio);
		WorkingBill workingbill = workingbillService.get(workid);
		Integer totalamount = workingbill.getTotalSingleAmount();

		if(state.equalsIgnoreCase("1")){//刷卡确定
		for (int i = 0; i < list.size(); i++) {
			EnteringwareHouse enteringwareHouse = list.get(i);	
			totalamount = enteringwareHouse.getStorageAmount()+totalamount;
												
			enteringwareHouse.setConfirmUser(admin);
			enteringwareHouse.setState(state);
			this.update(enteringwareHouse);
		}
		
		}else{//刷卡撤销
			for (int i = 0; i < list.size(); i++) {
				EnteringwareHouse enteringwareHouse = list.get(i);								
				totalamount -= enteringwareHouse.getStorageAmount();				
				enteringwareHouse.setConfirmUser(admin);
				enteringwareHouse.setState(state);
				this.update(enteringwareHouse);
			}
		}
		
		workingbill.setTotalSingleAmount(totalamount);
		workingbillService.update(workingbill);
	}

}