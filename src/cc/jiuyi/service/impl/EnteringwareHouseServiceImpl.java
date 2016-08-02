package cc.jiuyi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
	public static Logger log = Logger.getLogger(EnteringwareHouseServiceImpl.class);
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

	/*@Override
	public synchronized void updateState(List<EnteringwareHouse> list,
			String state, WorkingBill workingbill,String cardnumber) {
		HashMap<String,Object> maps = new HashMap<String,Object>();
		maps.put("workingbill",workingbill);
		maps.put("state", state);
		maps.put("cardno", cardnumber);
		
		updateWorkingInoutCalculate(list,maps);

	}
*/
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return enteringwareHouseDao.historyjqGrid(pager, map);
	}

	/*@Override
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

	}*/
	
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

	@Override
	public void updateApproval(String cardnumber,String loginid, String id, String confirmed,
			String undo, WorkingBill workingbill) throws Exception {
		try {
			Admin admin1 = adminservice.getByCardnum(cardnumber);
			//Admin admin=adminservice.get(loginid);		
			//String warehouse = admin.getTeam().getFactoryUnit().getWarehouse();// 线边仓
			//String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
			ThinkWayUtil util = new ThinkWayUtil();
			String budat = util.SystemDate();// 过账日期
			String[] ids = id.split(",");
			List<EnteringwareHouse> list = get(ids);
			for (int i = 0; i < list.size(); i++) {
				EnteringwareHouse enteringwareHouse = list.get(i);
				if (confirmed.equals(enteringwareHouse.getState())) {
					throw new Exception("1");
				}
				if (undo.equals(enteringwareHouse.getState())) {
					throw new Exception("2");
				}
			}	
			String charg = getCharg(workingbill);
			Double totalamount = workingbill.getTotalSingleAmount();
			for(EnteringwareHouse e:list){
				e.setBudat(budat);
				//e.setWerks(werks);
				//e.setLgort(warehouse);
				e.setMoveType("101");
				e.setBatch(charg);
				e.setState(confirmed);
				e.setConfirmUser(admin1);
				update(e);
				//enteringwareHouse = enteringwareHouseService.get(e.getId());
				totalamount = e.getStorageAmount()+totalamount;
				//e.setStorageAmount(e.getStorageAmount());
				//enterList.add(e);
			}
			workingbill.setTotalSingleAmount(totalamount);
			workingbillService.update(workingbill);
		} catch (Exception e) {
			log.info(e);
			if("1".equals(e.getMessage())){
				throw new Exception("已确认的无须再确认!");
			}else if("2".equals(e.getMessage())){
				throw new Exception("已撤消的无须再确认!");
			}else{
				throw new Exception("数据更新失败，请重试");
			}
			
		}
		

		
	}

	@Override
	public void updateUndo(String cardnumber, String loginid, String id,
			String unconfirm, String confirmed, String undo,
			WorkingBill workingbill) throws Exception {
		try {
			Admin admin1 = adminservice.getByCardnum(cardnumber);
			//Admin admin=adminservice.get(loginid);
			//String warehouse = admin.getTeam().getFactoryUnit().getWarehouse();// 线边仓
			//String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
			ThinkWayUtil util = new ThinkWayUtil();
			String budat = util.SystemDate();// 过账日期
			String[] ids = id.split(",");
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			String time = dateFormat.format(date); 
			Double totalamount = workingbill.getTotalSingleAmount();
			String charg = getCharg(workingbill);
			List<EnteringwareHouse> list = get(ids);
			for (int i = 0; i < list.size(); i++) {
				EnteringwareHouse enteringwareHouse = get(ids[i]);
				if (undo.equals(enteringwareHouse.getState())) {
					throw new Exception("1");
				}
			}
			for(EnteringwareHouse e:list){
				if(unconfirm.equals(e.getState())){//未确认直接撤销
					e.setState(undo);
					e.setRevokedTime(time);
					e.setRevokedUser(admin1.getName());
					e.setRevokedUserCard(cardnumber);
					e.setRevokedUserId(admin1.getId());
					update(e);
				}else if(confirmed.equals(e.getState())){//已确认直接撤销
					e.setState(undo);
					e.setBudat(budat);
					//e.setWerks(werks);
					//e.setLgort(warehouse);
					e.setMoveType("102");
					e.setBatch(charg);
					e.setRevokedTime(time);
					e.setRevokedUser(admin1.getName());
					e.setRevokedUserCard(cardnumber);
					e.setRevokedUserId(admin1.getId());
					update(e);
					totalamount -= e.getStorageAmount();	
					
				}
			}
			workingbill.setTotalSingleAmount(totalamount);
			workingbillService.update(workingbill);
			
		} catch (Exception e) {
			log.info(e);
			if("1".equals(e.getMessage())){
				throw new Exception("已撤销的无法再撤销！");
			}else{
				throw new Exception("数据更新失败，请重试");
			}
			
		}
	}

}