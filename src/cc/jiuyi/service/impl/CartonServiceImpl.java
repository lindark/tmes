package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import cc.jiuyi.sap.rfc.CartonRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

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
	private CartonRfc cartonRfc;

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
	public synchronized void updateState(List<Carton> list, String workingbillid) throws IOException, CustomerException {
		List<Carton> cartonList = new ArrayList<Carton>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.load(admin.getId());
		//线边仓
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();
		//工厂
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		String matnr = workingbill.getMatnr();//物料编号
		ThinkWayUtil util = new ThinkWayUtil();
		String budat = util.SystemDate();//过账日期
		
		//sap同步准备,有些数据是测试的，后期根据上面的变量做修改
		for (int i = 0; i < list.size(); i++) {
			Carton carton = list.get(i);
			carton.setCartonCode("50110123");
			//carton.setCartonCode(matnr);
			carton.setMove_type("101");
			carton.setLgort("2501");
			//carton.setLgort(warehouse);
			carton.setWerks("1000");
			//carton.setWerks(werks);
			carton.setBudat(budat);
			carton.setLifnr("10503");
			cartonList.add(carton);
		}
		cartonList = cartonRfc.CartonCrt(cartonList);
		for (int i = 0; i < cartonList.size(); i++) {
			Carton caron = cartonList.get(i);
			if("S".equals(caron.getE_type())){
				System.out.println("SAP同步成功");
				System.out.println(caron.getE_message());
			}else if("E".equals(caron.getE_type())){
				System.out.println("SAP同步失败");
				throw new CustomerException(caron.getE_message());
			}
		}
		Integer totalamount = workingbill.getCartonTotalAmount();
		for (int i = 0; i < cartonList.size(); i++) {
			Carton carton = cartonList.get(i);
			String ex_mblnr = carton.getEx_mblnr();
			carton = cartonDao.get(carton.getId());
			carton.setEx_mblnr(ex_mblnr);
			totalamount = carton.getCartonAmount() + totalamount;
			carton.setConfirmUser(admin);
			carton.setState("1");
			cartonDao.update(carton);
		}
		/*for (int i = 0; i < list.size(); i++) {
			Carton carton = list.get(i);
			totalamount = carton.getCartonAmount() + totalamount;
			carton.setConfirmUser(admin);
			carton.setState("1");
			cartonDao.update(carton);
		}*/
		workingbill.setCartonTotalAmount(totalamount);
		workingbillService.update(workingbill);
	}

	@Override
	public synchronized void updateState2(List<Carton> list,
			String workingbillid) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getCartonTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			Carton carton = list.get(i);
			if (carton.getState().equals("1")) {
				totalamount -= carton.getCartonAmount();
			}
			carton.setConfirmUser(admin);
			carton.setState("3");
			cartonDao.update(carton);
		}
		workingbill.setCartonTotalAmount(totalamount);
		workingbillService.update(workingbill);

	}

}
