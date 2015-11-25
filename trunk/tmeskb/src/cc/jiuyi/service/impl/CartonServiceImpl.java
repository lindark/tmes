package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.WorkingBillService;

/**
 * Service实现类 纸箱
 */
@Service
@Transactional
public class CartonServiceImpl extends BaseServiceImpl<Carton, String>
		implements CartonService {

	@Resource
	private CartonDao cartonDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(CartonDao cartonDao) {
		super.setBaseDao(cartonDao);
	}

	@Override
	public Pager getCartonPager(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return cartonDao.getCartonPager(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		cartonDao.updateisdel(ids, oper);

	}
	@Override
	/**
	 * 考虑线程同步
	 */
	public synchronized void updateState(List<Carton> list, String statu,
			String workingbillid) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getCartonTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			Carton carton = list.get(i);
			if (statu.equals("1")) {
				totalamount = carton.getCartonAmount() + totalamount;
			}
			if (statu.equals("3") && carton.getState().equals("1")) {
				totalamount -= carton.getCartonAmount();
			}
			carton.setConfirmUser(admin);
			carton.setState(statu);
			cartonDao.update(carton);
		}
		workingbill.setCartonTotalAmount(totalamount);
		workingbillService.update(workingbill);
	}

}
