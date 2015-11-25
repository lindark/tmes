package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EnteringwareHouseService;
import cc.jiuyi.service.WorkingBillService;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

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
	public Integer getSingleConversationRatio(String unitDescription,
			String convertUnit) {
		return enteringwareHouseDao.getSingleConversationRatio(unitDescription,
				convertUnit);
	}

	@Override
	public List<EnteringwareHouse> getByBill(String workingBillId) {
		return enteringwareHouseDao.getByBill(workingBillId);
	}

	@Override
	public void updateState(List<EnteringwareHouse> list, String statu,
			String workingbillid,Integer ratio) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getTotalSingleAmount();
		for (int i = 0; i < list.size(); i++) {
			EnteringwareHouse enteringwareHouse = list.get(i);
			if (statu.equals("1")) {
				totalamount = enteringwareHouse.getStorageAmount()*ratio + totalamount;
			}
			if (statu.equals("3") && enteringwareHouse.getState().equals("1")) {
				totalamount -= enteringwareHouse.getStorageAmount()*ratio;
			}
			enteringwareHouse.setConfirmUser(admin);
			enteringwareHouse.setState(statu);
			enteringwareHouseDao.update(enteringwareHouse);
		}
		workingbill.setTotalSingleAmount(totalamount);
		workingbillService.update(workingbill);
		
	}

}