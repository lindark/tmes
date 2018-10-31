package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cc.jiuyi.action.admin.WorkbinAction;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.WorkbinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.Workbin;
import cc.jiuyi.entity.WorkbinSon;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.WorkbinRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.WorkbinService;
import cc.jiuyi.service.WorkbinsonService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 纸箱
 */
@Service
public class WorkbinServiceImpl extends BaseServiceImpl<Workbin, String> implements WorkbinService {

	private static Logger log = Logger.getLogger(WorkbinServiceImpl.class);  
	
	@Resource
	private WorkbinDao workbinDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	@Resource
	private WorkbinRfc workbinRfc;
	@Resource
	private DictService dictService;
	@Resource
	private BomService bomService;
	@Resource
	private WorkbinsonService csService;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private WorkbinsonService workbinSonService;

	@Resource
	public void setBaseDao(WorkbinDao workbinDao) {
		super.setBaseDao(workbinDao);
	}

	/**
	 * jqgrid查询
	 */
	public Pager getWorkbinPager(Pager pager,String loginid)
	{
		Admin admin=this.adminservice.get(loginid);
		String productDate=admin.getProductDate();//生产日期
		String teamshift=admin.getShift();//当前班次
		String unitId=admin.getTeam()==null? "":admin.getTeam().getFactoryUnit()==null ?"":admin.getTeam().getFactoryUnit().getId();
		return workbinDao.getWorkbinPager(pager,productDate,teamshift,unitId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		workbinDao.updateisdel(ids, oper);

	}

	/**
	 * 刷卡确认
	 * 考虑线程同步
	 */
	public synchronized String updateState(List<Workbin> list,String workingbillid, String cardnumber) throws IOException,CustomerException {
		/*
		List<Workbin> workbinList = new ArrayList<Workbin>();
		Admin admin = adminservice.getByCardnum(cardnumber);
		admin = adminservice.load(admin.getId());
		WorkingBill workingbill = workingbillService.get(workingbillid);
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();// 线边仓
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
		//String matnr = workingbill.getMatnr();// 物料编号
		ThinkWayUtil util = new ThinkWayUtil();
		String budat = util.SystemDate();// 过账日期

		
		String matnr ="";  //物料编码
		List<Bom> bomList = new ArrayList<Bom>();
		bomList = bomService.findBom(workingbill.getAufnr(), workingbill.getProductDate());
		for (int i = 0; i < bomList.size(); i++) {
			Bom bom = bomList.get(i);
			if(bom.getMaterialCode().startsWith("5")){
				matnr = bom.getMaterialCode();
			}	
		}
		
		if(matnr.equals("")){
			return "E";
		}
		
		// sap同步准备,有些数据是测试的，后期根据上面的变量做修改
		    for (int i = 0; i < list.size(); i++) {
			Workbin workbin = list.get(i);
			// workbin.setWorkbinCode("50110123");
			workbin.setWorkbinCode(matnr);
			workbin.setMove_type("101");
			// workbin.setLgort("2501");
			workbin.setLgort(warehouse);
			// workbin.setWerks("1000");
			workbin.setWerks(werks);
			workbin.setBudat(budat);
			workbin.setLifnr(ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1"));
			//System.out.println("Lifnr"+workbin.getLifnr());
			workbinList.add(workbin);
		}
		    
		//调用sap函数接口
		workbinList = workbinRfc.WorkbinCrt(workbinList);
		//根据返回集合，判断函数调用是否成功
		for (int i = 0; i < workbinList.size(); i++) {
			Workbin caron = workbinList.get(i);
			if ("S".equals(caron.getE_type())) {
				System.out.println("SAP同步成功");
				System.out.println(caron.getE_message());
			} else if ("E".equals(caron.getE_type())) {
				System.out.println("SAP同步失败");
				throw new CustomerException(caron.getE_message());
			}
		}
		//若成功，则更新数据库
		Integer totalamount = workingbill.getWorkbinTotalAmount();
		for (int i = 0; i < workbinList.size(); i++) {
			Workbin workbin = workbinList.get(i);
			String ex_mblnr = workbin.getEx_mblnr();
			workbin = workbinDao.get(workbin.getId());
			workbin.setEx_mblnr(ex_mblnr);
			totalamount = workbin.getWorkbinAmount() + totalamount;
			workbin.setConfirmUser(admin);
			workbin.setState("1");
			workbinDao.update(workbin);
		}
		/*
		 * for (int i = 0; i < list.size(); i++) { Workbin workbin = list.get(i);
		 * totalamount = workbin.getWorkbinAmount() + totalamount;
		 * workbin.setConfirmUser(admin); workbin.setState("1");
		 * workbinDao.update(workbin); }
		 *
		workingbill.setWorkbinTotalAmount(totalamount);
		workingbillService.update(workingbill);
		*/
		return null;
	}


	/**
	 * 刷卡撤销
	 */
	@Override
	public synchronized void updateState2(List<Workbin> list,
			String workingbillid, String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getCartonTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			Workbin workbin = list.get(i);
			if (workbin.getState().equals("1")) {
				totalamount -= workbin.getWorkbinAmount();
			}
			workbin.setConfirmUser(admin);
			workbin.setState("3");
			this.update(workbin);
		}
		workingbill.setCartonTotalAmount(totalamount);
		workingbillService.update(workingbill);
	}
	
	
	/**
	 * 刷卡撤销 by Reece
	 */
	@Override
	public synchronized void updateCancel(List<Workbin> list,String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
		for (int i = 0; i < list.size(); i++) {
			Workbin workbin = list.get(i);	
			workbin.setConfirmUser(admin);
			workbin.setState("3");
			this.update(workbin);
		}
	}
	
	

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--新增前
	 */
	public List<WorkbinSon> getBomByConditions()
	{
		List<WorkbinSon>cslist=new ArrayList<WorkbinSon>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		if(list_wb!=null)
		{
			for(int i=0;i<list_wb.size();i++)
			{
				WorkingBill wb=list_wb.get(i);
				Bom bom=this.bomService.getBomByConditions(wb.getAufnr(), wb.getProductDate(),"5",wb.getWorkingBillCode());//根据订单号,生产日期,以"5"开关的查询
				if(bom!=null)
				{
					WorkbinSon cs=new WorkbinSon();
//					cs.setMATNR(bom.getMaterialCode());//物料编码
//					cs.setMATNRDES(bom.getMaterialName());//物料描述
					cs.setProductcode(wb.getMatnr());//产品编号
					cs.setProductname(wb.getMaktx());//产品名称
					//cs.setWbcode(wb.getWorkingBillCode());//随工单编码
					cs.setWbid(wb.getId());//随工单ID
					cs.setXcstotal(wb.getCartonTotalAmount()+"");//累计数量
					cslist.add(cs);
				}
			}
		}
		return cslist;
	}
	
	/**
	 * 新增保存
	 */
	public Map<String,Object> saveData(List<WorkbinSon> list_cs, String cardnumber,String loginid,String bktxt)
	{
		
		Map<String,Object> map = new HashMap<String,Object>();
//		System.out.println("------------------"+map.get("message")+"--------------------------");
//		if("S".equals(map.get("status"))){
			Admin admin = adminservice.getByCardnum(cardnumber);
			Admin login_admin=this.adminservice.get(loginid);
			
			Team team = login_admin.getTeam();
			if(team == null){
				map.put("status", "E");
				map.put("message", "当前人员未绑定班组");
				return map;
			}
			FactoryUnit factoryUnit = team.getFactoryUnit();
			if(factoryUnit == null){
				map.put("status", "E");
				map.put("message", "当前人员未绑定单元");
				return map;
			}
			String xbcCode = factoryUnit.getWarehouse(); 
			for(WorkbinSon wbs : list_cs) {
				if(!wbs.getJslgort().equals(xbcCode)) {
					map.put("status", "E");
					map.put("message", "存在交货单收货地址"+wbs.getJslgort()+"与当前线边仓地址"+xbcCode+"不一致！");
					return map;
				}
			}
			
			/**主表数据插入*/
			Workbin c=new Workbin();
			c.setCreateDate(new Date());//创建日期
			c.setModifyDate(new Date());//修改日期
			c.setCreateUser(admin);//创建人
			c.setProductDate(login_admin.getProductDate());//生产日期
			c.setTeamshift(login_admin.getShift());//班次
			c.setConfirmUser(admin);
//			c.setFactoryUnit(login_admin.getTeam().getFactoryUnit());
			c.setState("2");//状态-以保存
//			c.setBktxt(bktxt);
			c.setOderNumber(bktxt);
			String cid=this.save(c);//保存
			c.setId(cid);
			/**子表数据插入*/
			log.info("-------------料想保存数据-----"+list_cs.size()+"--------------------------");
			List<WorkbinSon> wblis = saveinfo(cid,list_cs);
//		}
			map.put("Status", "S");
			map.put("workbin", c);
			map.put("workbinson", wblis);
		return map;
		
	}
	
	/**
	 * 保存子表数据共用方法
	 */
	public List<WorkbinSon> saveinfo(String cid,List<WorkbinSon> list_cs)
	{
		List<WorkbinSon> wblist = new ArrayList<WorkbinSon>();
		Workbin c=this.get(cid);
		if(list_cs!=null)
		{
			for(int i=0;i<list_cs.size();i++)
			{
				WorkbinSon cs=list_cs.get(i);
				cs.setWorkbin(c);		
				cs.setState("2");
				String id = this.csService.save(cs);
				cs.setId(id);
				wblist.add(cs);
			}
		}
		return wblist;
	}
	
	/**
	 * 获取bom中随工单对应的以5开头的各个物料--编辑
	 */
	public List<WorkbinSon> getBomByConditions_edit(String id)
	{
		List<WorkbinSon>cslist=new ArrayList<WorkbinSon>();
		/**外表的数据*/
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		List<WorkbinSon>xlist=new ArrayList<WorkbinSon>();
		for(int i=0;i<list_wb.size();i++)
		{
			WorkingBill wb=list_wb.get(i);
			Bom bom=this.bomService.getBomByConditions(wb.getAufnr(), wb.getProductDate(),"5",wb.getWorkingBillCode());//根据订单号,生产日期,以"5"开关的查询
			if(bom!=null)
			{
				WorkbinSon cs=new WorkbinSon();
//				cs.setMATNR(bom.getMaterialCode());//物料编码
//				cs.setMATNRDES(bom.getMaterialName());//物料描述
				cs.setProductcode(wb.getMatnr());//产品编号
				cs.setProductname(wb.getMaktx());//产品名称
				//cs.setWbcode(wb.getWorkingBillCode());//随工单编码
				cs.setWbid(wb.getId());//随工单ID
				//cs.setXcstotal(wb.getWorkbinTotalAmount()+"");//累计数量
				xlist.add(cs);
			}
		}
		List<WorkbinSon> xcslist = new ArrayList<WorkbinSon>();
		for(WorkbinSon cs : xlist){
			boolean flag = true;
			for(WorkbinSon cs1 : xcslist){
//				if(cs.getMATNR().equals(cs1.getMATNR()) && cs.getProductcode().equals(cs1.getProductcode())){
//					flag = false;
//					break;
//				}
			}
			if(flag){
				xcslist.add(cs);
			}
		}
		xlist = xcslist;
		/**外表数据和本表的数据融合*/
		Workbin c=this.get(id);
		List<WorkbinSon>xlist2=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());
		if(xlist2.size()>0&&xlist.size()>0)
		{
			for(int i=0;i<xlist.size();i++)
			{
				WorkbinSon cs=xlist.get(i);
				for(int j=0;j<xlist2.size();j++)
				{
					WorkbinSon cs2=xlist2.get(j);
//					if(cs2.getMATNR().equals(cs.getMATNR()) && cs2.getProductcode().equals(cs.getProductcode()))
//					{
//						cs.setCscount(cs2.getCscount());
//					}
				}
				cslist.add(cs);
			}
		}
		if(cslist.size()>0)
		{
			return cslist;
		}
		return xlist;
	}

	/**
	 * 修改
	 */
	public void updateData(List<WorkbinSon> list_cs, String id,String bktxt)
	{
		Workbin c=this.get(id);
//		c.setBktxt(bktxt);// 修改单据编号
		this.update(c);
		List<WorkbinSon>list=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());//获取对应子表数据
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				WorkbinSon cs=list.get(i);
				this.csService.delete(cs.getId());//删除
			}
			
		}
		/*if (list_cs != null) {
			for (int i = 0; i < list_cs.size(); i++) {
				WorkbinSon cs = list.get(i);
			}
			c.setModifyDate(new Date());// 修改日期
			c.setBktxt(bktxt);// 修改单据编号
			this.update(c);
		}*/

		/** 子表数据插入 */
		saveinfo(id,list_cs);
		
	}

	/**
	 * 与SAP交互
	 */
	public String updateToSAP(String[] ids,String cardnumber,String loginid)throws IOException,CustomerException
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		Admin a=this.adminservice.get(loginid);
		String warehouse = a.getTeam().getFactoryUnit().getWarehouse();// 线边仓(库存地点)
		String lifnr= ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1");//供应商
		String werks = a.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂
		for (int i = 0; i < ids.length; i++)
		{
			Workbin c = this.get(ids[i]);
			List<WorkbinSon>list=new ArrayList<WorkbinSon>();
			List<WorkbinSon>listcs=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());
			for(int j=0;j<listcs.size();j++)
			{
				WorkbinSon cs=listcs.get(j);
				WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				cs.setLIFNR(lifnr);//供应商
//				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
//				cs.setLGORT(warehouse);//库存地点
				cs.setMOVE_TYPE("101");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
//				Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getCartonTotalAmount());
//				wb.setCartonTotalAmount(d.intValue());
				this.workingbillService.update(wb);
			}
//			if(list.size()>0)
//			{
//				Workbin c_return1=workbinRfc.WorkbinCrt("X",list);
//				if("E".equals(c_return1.getE_TYPE()))
//				{
//					return c_return1.getE_MESSAGE();
//				}
//				Workbin c_return=workbinRfc.WorkbinCrt("",list);
//				if("E".equals(c_return.getE_TYPE()))
//				{
//					return c_return.getE_MESSAGE();
//				}
//				c.setE_TYPE(c_return.getE_TYPE());//返回类型
//				c.setEX_MBLNR(c_return.getEX_MBLNR());//凭证号
//				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
//				c.setModifyDate(new Date());
//				c.setConfirmUser(admin);//确认人
//				c.setState("1");
//				this.update(c);
//			}
		}
		return "S";
	}

	@Override
	public String updateToSAPNew(String[] ids, String cardnumber, String loginid)
			throws IOException, CustomerException {
		Admin admin = adminservice.getByCardnum(cardnumber);
		Admin a=this.adminservice.get(loginid);
		//String warehouse = a.getTeam().getFactoryUnit().getWarehouse();// 线边仓(库存地点)
		String lifnr= ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1");//供应商
		String werks = a.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂
		for (int i = 0; i < ids.length; i++)
		{
			Workbin c = this.get(ids[i]);
			List<WorkbinSon>list=new ArrayList<WorkbinSon>();
			List<WorkbinSon>listcs=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());
			for(int j=0;j<listcs.size();j++)
			{
				WorkbinSon cs=listcs.get(j);
				//WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				WorkingBill wb = workingbillService.get(cs.getWbid());
				FactoryUnit fun = factoryUnitService.get("workCenter",wb.getWorkcenter());
				cs.setLIFNR(lifnr);//供应商
//				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
//				cs.setLGORT(fun.getWarehouse());//库存地点
				cs.setMOVE_TYPE("101");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
				//Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getWorkbinTotalAmount());
				//wb.setWorkbinTotalAmount(d.intValue());
				//this.workingbillService.update(wb);
			}
//			if(list.size()>0)
//			{
//				Object[] c_returnobj = workbinRfc.WorkbinCrtNew("X",list);
//				Workbin c_return1=(Workbin) c_returnobj[0];
//				if("E".equals(c_return1.getE_TYPE()))
//				{
//					return c_return1.getE_MESSAGE();
//				}
//				Object[] c_returnobj1=workbinRfc.WorkbinCrtNew("",list);
//				Workbin c_return=(Workbin) c_returnobj1[0];
//				if("E".equals(c_return.getE_TYPE()))
//				{
//					return c_return.getE_MESSAGE();
//				}
//				c.setE_TYPE(c_return.getE_TYPE());//返回类型
//				c.setEX_MBLNR(c_return.getEX_MBLNR());//凭证号
//				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
//				c.setModifyDate(new Date());
//				c.setConfirmUser(admin);//确认人
//				c.setState("1");
//				this.update(c);
//				List<WorkbinSon> cslist=  (List<WorkbinSon>) c_returnobj1[1];
//				for(WorkbinSon cs : cslist){
//					this.csService.update(cs);
//				}
//			}
			if(listcs.size()==0&&list.size()==0){
				c.setConfirmUser(admin);//确认人
				c.setState("1");
				this.update(c);
			}
		}
		return "S";
	}
	@Override
	public String updateToSAPReturn(String[] ids, String cardnumber,
			String loginid) throws IOException, CustomerException {
		Admin admin = adminservice.getByCardnum(cardnumber);
		Admin a=this.adminservice.get(loginid);
		//String warehouse = a.getTeam().getFactoryUnit().getWarehouse();// 线边仓(库存地点)
		String lifnr= ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1");//供应商
		String werks = a.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂
		for (int i = 0; i < ids.length; i++)
		{
			Workbin c = this.get(ids[i]);
			List<WorkbinSon>list=new ArrayList<WorkbinSon>();
			List<WorkbinSon>listcs=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());
			for(int j=0;j<listcs.size();j++)
			{
				WorkbinSon cs=listcs.get(j);
				WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				FactoryUnit fun = factoryUnitService.get("workCenter",wb.getWorkcenter());
				cs.setLIFNR(lifnr);//供应商
//				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
//				cs.setLGORT(fun.getWarehouse());//库存地点
				cs.setMOVE_TYPE("102");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
//				Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getCartonTotalAmount());
//				wb.setCartonTotalAmount(d.intValue());
				this.workingbillService.update(wb);
			}
//			if(list.size()>0)
//			{
//				Workbin c_return1=workbinRfc.WorkbinCrt("X",list);
//				if("E".equals(c_return1.getE_TYPE()))
//				{
//					return c_return1.getE_MESSAGE();
//				}
//				Workbin c_return=workbinRfc.WorkbinCrt("",list);
//				if("E".equals(c_return.getE_TYPE()))
//				{
//					return c_return.getE_MESSAGE();
//				}
//				c.setE_TYPE(c_return.getE_TYPE());//返回类型
//				String str = c.getEX_MBLNR();
//				c.setEX_MBLNR(str==null?"":str+"/"+c_return.getEX_MBLNR());//凭证号
//				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
//				c.setModifyDate(new Date());
//				Date date = new Date(); 
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//				String time = dateFormat.format(date); 
////				c.setConfirmUser(admin);//确认人
//				c.setRevokedUser(admin.getName());
//				c.setRevokedTime(time);
//				c.setRevokedUserCard(admin.getCardNumber());
//				c.setRevokedUserId(admin.getId());
//				c.setState("3");
//				this.update(c);
//			}
//			if(listcs.size()==0&&list.size()==0){
//				Date date = new Date(); 
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//				String time = dateFormat.format(date); 
//				c.setRevokedUser(admin.getName());
//				c.setRevokedTime(time);
//				c.setRevokedUserCard(admin.getCardNumber());
//				c.setRevokedUserId(admin.getId());
//				c.setState("3");
//				this.update(c);
//			}
		}
		return "S";
	}
	/**
	 * 查看
	 */
	public List<WorkbinSon> getToShow(String id)
	{
		List<WorkbinSon> list_cs=new ArrayList<WorkbinSon>();
		//根据id查询主表
		Workbin c=this.get(id);
		List<WorkbinSon> list=new ArrayList<WorkbinSon>(c.getWorkbinsonSet());
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				WorkbinSon cs=list.get(i);
				//WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询随工单
				//cs.setXcstotal(wb.getWorkbinTotalAmount()+"");
				list_cs.add(cs);
			}
		}
		return list_cs;
	}


	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return workbinDao.historyjqGrid(pager, map);
	}

	@Override
	public Pager findWorkbinByPager(Pager pager, HashMap<String, String> mapcheck) {
		// TODO Auto-generated method stub
		return workbinDao.findWorkbinByPager(pager, mapcheck);
	}

	@Override
	public void updateWorkbinAndSon(Workbin workbin, List<WorkbinSon> wbslist) {
		workbin.setState("1");
		update(workbin);
		for(WorkbinSon wbs : wbslist) {
			wbs.setState("1");
			workbinSonService.update(wbs);
		}
		
	}



}
