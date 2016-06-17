package cc.jiuyi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.EnteringwareHouseDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.EnteringwareHouseService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

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
			String state, WorkingBill workingbill,String cardnumber) {
		HashMap<String,Object> maps = new HashMap<String,Object>();
		maps.put("workingbill",workingbill);
		maps.put("state", state);
		maps.put("cardno", cardnumber);
		
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
		WorkingBill workingbill=(WorkingBill)maps.get("workingbill");
		String state=(String)maps.get("state");
		Double totalamount = workingbill.getTotalSingleAmount();

		if(state.equalsIgnoreCase("1")){//刷卡确定
		for (int i = 0; i < list.size(); i++) {
			EnteringwareHouse enteringwareHouse = list.get(i);	
			String exmblnr=enteringwareHouse.getEx_mblnr();

			enteringwareHouse=	enteringwareHouseDao.get(enteringwareHouse.getId());
			totalamount = enteringwareHouse.getStorageAmount()+totalamount;
			enteringwareHouse.setEx_mblnr(exmblnr);

			//enteringwareHouse.setStorageAmount(enteringwareHouse.getStorageAmount()/ratio1);
			enteringwareHouse.setConfirmUser(admin);
			enteringwareHouse.setState(state);
			this.update(enteringwareHouse);
		}
		
		}else{//刷卡撤销
			for (int i = 0; i < list.size(); i++) {
				EnteringwareHouse enteringwareHouse = list.get(i);	
				String exmblnr=enteringwareHouse.getEx_mblnr();
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
				enteringwareHouse=	enteringwareHouseDao.get(enteringwareHouse.getId());
				totalamount -= enteringwareHouse.getStorageAmount();					
				enteringwareHouse.setEx_mblnr(enteringwareHouse.getEx_mblnr()+"/"+exmblnr);
//				enteringwareHouse.setConfirmUser(admin);
				enteringwareHouse.setState(state);
				enteringwareHouse.setRevokedUser(admin.getName());
				enteringwareHouse.setRevokedTime(time);
				enteringwareHouse.setRevokedUserCard(admin.getCardNumber());
				enteringwareHouse.setRevokedUserId(admin.getId());
				//enteringwareHouse.setStorageAmount(enteringwareHouse.getStorageAmount()/ratio1);
				this.update(enteringwareHouse);
			}
		}
		
		workingbill.setTotalSingleAmount(totalamount);
		workingbillService.update(workingbill);

	}
	
	/**
	 * 获取批次号
	 * @return
	 */
	public String getCharg(WorkingBill workingbill){
		/**  modify weitao
		 * 处理 编号问题
		 */
		Calendar a = ThinkWayUtil.getCalendar(workingbill.getProductDate());
		Integer year = a.get(Calendar.YEAR);
		Integer mounth = a.get(Calendar.MONTH)+1;//获取月份
		Integer day = a.get(Calendar.DATE);//获取日期
		String yearls = StringUtils.substring(year.toString(), year.toString().length()-2);
		String mounthls=""+mounth;
		String dayls=""+day;
		if(Integer.parseInt(yearls) < 10)
			yearls="0"+year;
		if(mounth <10)
			mounthls = "0"+mounth;
		if(day<10)
			dayls="0"+day;
		String charg =yearls+mounthls+dayls;//流水号前8位
		
		return charg;
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return enteringwareHouseDao.historyExcelExport(map);
	}

}