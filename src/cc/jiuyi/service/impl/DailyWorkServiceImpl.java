package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.DailyWorkRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;

/**
 * Service实现类 - 报工
 */

@Service
public class DailyWorkServiceImpl extends BaseServiceImpl<DailyWork, String>
		implements DailyWorkService {
	@Resource
	private DailyWorkDao dailyWorkDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	@Resource
	private ProductsService productsService;
	@Resource
	private DailyWorkRfc dailyWorkRfc;

	@Resource
	public void setBaseDao(DailyWorkDao dailyWork) {
		super.setBaseDao(dailyWork);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return dailyWorkDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public synchronized void updateState(List<DailyWork> list,
			String workingbillid) throws IOException, CustomerException {
		List<DailyWork> dailyWorkList = new ArrayList<DailyWork>();
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		String workingbillCode = workingbill.getWorkingBillCode();
		String code = workingbillCode
				.substring(0, workingbillCode.length() - 2);
		Double totalamount = workingbill.getDailyWorkTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			DailyWork dailyWork = list.get(i);
			dailyWork = dailyWorkDao.get(dailyWork.getId());
			dailyWork.setStep(dailyWork.getProcess().getProcessCode());
			dailyWork.setOrderid("100117061");
			//dailyWork.setOrderid(code);
			//dailyWorkRfc.SetDailyWork("100117061", "0010", dailyWork.getEnterAmount().toString());
			dailyWorkList.add(dailyWork);
		}
		dailyWorkList = dailyWorkRfc.BatchSetDailyWork(dailyWorkList);
		for (int i = 0; i < dailyWorkList.size(); i++) {
			DailyWork dailyWork = dailyWorkList.get(i);
			if("S".equals(dailyWork.getE_type())){
				System.out.println("SAP同步成功");
				System.out.println(dailyWork.getE_message());
			}else if("E".equals(dailyWork.getE_type())){
				System.out.println("SAP同步失败");
				throw new CustomerException(dailyWork.getE_message());
			}
		}
		for (int i = 0; i < list.size(); i++) {
			DailyWork d = list.get(i);
			totalamount = d.getEnterAmount() + totalamount;
			d.setConfirmUser(admin);
			d.setState("1");
			dailyWorkDao.update(d);
		}
		workingbill.setDailyWorkTotalAmount(totalamount);
		workingbillService.update(workingbill);
	}

	@Override
	public void updateState2(List<DailyWork> list, String workingbillid) {
		Admin admin = adminservice.getLoginAdmin();
		WorkingBill workingbill = workingbillService.get(workingbillid);
		String workingbillCode = workingbill.getWorkingBillCode();
		String code = workingbillCode
				.substring(0, workingbillCode.length() - 2);
		String matnr = workingbill.getMatnr();// 当前随工单的产品编码
		Products products = productsService.getProducts(matnr);
		Set<Process> processes = products.getProcess();// 当前产品的工序
		Iterator<Process> it = processes.iterator();// 工序迭代器
		Double totalamount = workingbill.getDailyWorkTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			DailyWork dailyWork = list.get(i);
			if (dailyWork.getState().equals("1")) {
				totalamount -= dailyWork.getEnterAmount();
			}
			dailyWork.setConfirmUser(admin);
			dailyWork.setState("3");
			dailyWorkDao.update(dailyWork);
		}
		workingbill.setDailyWorkTotalAmount(totalamount);
		workingbillService.update(workingbill);

	}

}