package cc.jiuyi.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DailyWorkDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
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

	/**
	 * 刷卡确认
	 */
	@Override
	public synchronized void updateState(List<DailyWork> list,String state,
			String workingbillid,String cardnumber) throws IOException,
			CustomerException {
		List<DailyWork> dailyWorkList = new ArrayList<DailyWork>();
		Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill workingbill = workingbillService.get(workingbillid);
		String workingbillCode = workingbill.getWorkingBillCode();
		String code = workingbillCode
				.substring(0, workingbillCode.length() - 2);
		String wb = workingbillCode.substring(workingbillCode.length() - 2);
		Double totalamount = workingbill.getDailyWorkTotalAmount();
		//sap同步前的准备，包括传入文本、工序等
		for (int i = 0; i < list.size(); i++) {
			DailyWork dailyWork = list.get(i);
		//	dailyWork = dailyWorkDao.get(dailyWork.getId());
			dailyWork.setStep(dailyWork.getProcessCode());
			dailyWork.setWb(wb);
			dailyWork.setOrderid(code);
			dailyWorkList.add(dailyWork);
		}
		//调用sap函数接口
		dailyWorkList = dailyWorkRfc.BatchSetDailyWork(dailyWorkList);
		//遍历返回的集合，判断sap同步是否成功
		for (int i = 0; i < dailyWorkList.size(); i++) {
			DailyWork dailyWork = dailyWorkList.get(i);
			if ("S".equals(dailyWork.getE_type())) {
				System.out.println("SAP同步成功");
				System.out.println(dailyWork.getE_message());
			} else if ("E".equals(dailyWork.getE_type())) {
				System.out.println("SAP同步失败");
				throw new CustomerException(dailyWork.getE_message());
			}
			System.out.println(dailyWork.getCONF_NO());
		}
				
		HashMap<String,Object> maps = new HashMap<String,Object>();
		maps.put("id",workingbillid);
		maps.put("state", state);
		maps.put("cardno", cardnumber);

		updateWorkingInoutCalculate(dailyWorkList,maps);		
	}

	/**
	 * 刷卡撤销
	 */
	@Override
	public void updateState2(List<DailyWork> list,String state,String workingbillid,
			String cardnumber) throws IOException, CustomerException {
		Admin admin = adminservice.getByCardnum(cardnumber);
		List<DailyWork> dailyWorkList = new ArrayList<DailyWork>();
		// 未确认的如果调sap函数会报错，所以先处理未确认的
		for (int i = 0; i < list.size(); i++) {
			DailyWork dailyWork = new DailyWork();
			dailyWork = list.get(i);
			if ("2".equals(dailyWork.getState())) {
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
				dailyWork.setState("3");
//				dailyWork.setConfirmUser(admin);
				dailyWork.setRevokedUser(admin.getName());
				dailyWork.setRevokedTime(time);
				dailyWork.setRevokedUserCard(admin.getCardNumber());
				dailyWork.setRevokedUserId(admin.getId());
				this.update(dailyWork);
			} else {
				dailyWorkList.add(dailyWork);
			}
		}
		if (dailyWorkList.size() > 0) {
			dailyWorkList = dailyWorkRfc.BatchSetDailyWorkCancel(dailyWorkList);
			for (int i = 0; i < dailyWorkList.size(); i++) {
				DailyWork dailyWork = dailyWorkList.get(i);
				if ("S".equals(dailyWork.getE_type())) {
					System.out.println("SAP同步成功");
					System.out.println(dailyWork.getE_message());
				} else if ("E".equals(dailyWork.getE_type())) {
					System.out.println("SAP同步失败");
					throw new CustomerException(dailyWork.getE_message());
				}
			}
			
			
			HashMap<String,Object> maps = new HashMap<String,Object>();
			maps.put("id",workingbillid);
			maps.put("state", state);
			maps.put("cardno", cardnumber);
			
			updateWorkingInoutCalculate(list,maps);			
		}
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return dailyWorkDao.historyjqGrid(pager, map);
	}

	@Override
	public void updateWorkingInoutCalculate(List<DailyWork> paramaterList,HashMap<String,Object> maps) {
		String card=(String)maps.get("cardno");
		Admin admin = adminservice.getByCardnum(card);
		String workid=(String)maps.get("id");
		String state=(String)maps.get("state");
		
		WorkingBill workingbill = workingbillService.get(workid);
		BigDecimal totalamount = new BigDecimal(workingbill.getDailyWorkTotalAmount());
		if(state.equalsIgnoreCase("1")){//刷卡确定
			
		for (int i = 0; i < paramaterList.size(); i++) {
			DailyWork dailyWork = paramaterList.get(i);	
			String CONF_NO = dailyWork.getCONF_NO();// 确认号
			String CONF_CNT = dailyWork.getCONF_CNT();// 计数器
			dailyWork = dailyWorkDao.get(dailyWork.getId());			
			totalamount  =  new BigDecimal(dailyWork.getEnterAmount()).add(totalamount).setScale(2, RoundingMode.HALF_UP);
			if("1".equalsIgnoreCase(dailyWork.getMoudle())){
				workingbill.setChecknum1("1");
			}else if("2".equalsIgnoreCase(dailyWork.getMoudle())){
				workingbill.setChecknum2("2");
			}else if("3".equalsIgnoreCase(dailyWork.getMoudle())){
				workingbill.setChecknum3("3");
			}else if("4".equalsIgnoreCase(dailyWork.getMoudle())){
				workingbill.setChecknum4("4");
			}else{
				workingbill.setChecknum5("5");
			}
             
			//dailyWork.setEnterAmount(dailyWork.getEnterAmount());
			
			dailyWork.setCONF_NO(CONF_NO);
			dailyWork.setCONF_CNT(CONF_CNT);
			dailyWork.setConfirmUser(admin);
			dailyWork.setState("1");			
			this.update(dailyWork);
		}
		workingbill.setDailyWorkTotalAmount(totalamount.doubleValue());
		workingbillService.merge(workingbill);
		
		}else{//刷卡撤销
			for (int i = 0; i < paramaterList.size(); i++) {
				DailyWork dailyWork = paramaterList.get(i);
				String CONF_NO = dailyWork.getCONF_NO();// 确认号
				String CONF_CNT = dailyWork.getCONF_CNT();// 计数器
				dailyWork = dailyWorkDao.get(dailyWork.getId());
				totalamount = totalamount.subtract(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
				if("1".equalsIgnoreCase(dailyWork.getMoudle())){
					workingbill.setChecknum1("1");
				}else if("2".equalsIgnoreCase(dailyWork.getMoudle())){
					workingbill.setChecknum2("2");
				}else if("3".equalsIgnoreCase(dailyWork.getMoudle())){
					workingbill.setChecknum3("3");
				}else if("4".equalsIgnoreCase(dailyWork.getMoudle())){
					workingbill.setChecknum4("4");
				}else{
					workingbill.setChecknum5("5");
				}
				
			//	dailyWork.setEnterAmount(dailyWork.getEnterAmount());
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
				dailyWork.setCONF_NO(CONF_NO);
				dailyWork.setCONF_CNT(CONF_CNT);
//				dailyWork.setConfirmUser(admin);
				dailyWork.setRevokedUser(admin.getName());
				dailyWork.setRevokedTime(time);
				dailyWork.setRevokedUserCard(admin.getCardNumber());
				dailyWork.setRevokedUserId(admin.getId());
				dailyWork.setState("3");				
				this.update(dailyWork);
			}
			workingbill.setDailyWorkTotalAmount(totalamount.doubleValue());
			workingbillService.merge(workingbill);
		}
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return dailyWorkDao.historyExcelExport(map);
	}

}