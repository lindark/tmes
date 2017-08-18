package cc.jiuyi.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.SarkDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Sark;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.SarkRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.SarkService;
import cc.jiuyi.service.SarksonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 纸箱
 */
@Service
public class SarkServiceImpl extends BaseServiceImpl<Sark, String> implements SarkService {

	@Resource
	private SarkDao sarkDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	@Resource
	private SarkRfc sarkRfc;
	@Resource
	private DictService dictService;
	@Resource
	private BomService bomService;
	@Resource
	private SarksonService csService;
	@Resource
	private FactoryUnitService factoryUnitService;

	@Resource
	public void setBaseDao(SarkDao sarkDao) {
		super.setBaseDao(sarkDao);
	}

	/**
	 * jqgrid查询
	 */
	public Pager getSarkPager(Pager pager,String loginid)
	{
		Admin admin=this.adminservice.get(loginid);
		String productDate=admin.getProductDate();//生产日期
		String teamshift=admin.getShift();//当前班次
		String unitId=admin.getTeam()==null? "":admin.getTeam().getFactoryUnit()==null ?"":admin.getTeam().getFactoryUnit().getId();
		return sarkDao.getSarkPager(pager,productDate,teamshift,unitId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		sarkDao.updateisdel(ids, oper);

	}

	/**
	 * 刷卡确认
	 * 考虑线程同步
	 */
	public synchronized String updateState(List<Sark> list,String workingbillid, String cardnumber) throws IOException,CustomerException {
		/*
		List<Sark> sarkList = new ArrayList<Sark>();
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
			Sark sark = list.get(i);
			// sark.setSarkCode("50110123");
			sark.setSarkCode(matnr);
			sark.setMove_type("101");
			// sark.setLgort("2501");
			sark.setLgort(warehouse);
			// sark.setWerks("1000");
			sark.setWerks(werks);
			sark.setBudat(budat);
			sark.setLifnr(ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1"));
			//System.out.println("Lifnr"+sark.getLifnr());
			sarkList.add(sark);
		}
		    
		//调用sap函数接口
		sarkList = sarkRfc.SarkCrt(sarkList);
		//根据返回集合，判断函数调用是否成功
		for (int i = 0; i < sarkList.size(); i++) {
			Sark caron = sarkList.get(i);
			if ("S".equals(caron.getE_type())) {
				System.out.println("SAP同步成功");
				System.out.println(caron.getE_message());
			} else if ("E".equals(caron.getE_type())) {
				System.out.println("SAP同步失败");
				throw new CustomerException(caron.getE_message());
			}
		}
		//若成功，则更新数据库
		Integer totalamount = workingbill.getSarkTotalAmount();
		for (int i = 0; i < sarkList.size(); i++) {
			Sark sark = sarkList.get(i);
			String ex_mblnr = sark.getEx_mblnr();
			sark = sarkDao.get(sark.getId());
			sark.setEx_mblnr(ex_mblnr);
			totalamount = sark.getSarkAmount() + totalamount;
			sark.setConfirmUser(admin);
			sark.setState("1");
			sarkDao.update(sark);
		}
		/*
		 * for (int i = 0; i < list.size(); i++) { Sark sark = list.get(i);
		 * totalamount = sark.getSarkAmount() + totalamount;
		 * sark.setConfirmUser(admin); sark.setState("1");
		 * sarkDao.update(sark); }
		 *
		workingbill.setSarkTotalAmount(totalamount);
		workingbillService.update(workingbill);
		*/
		return null;
	}


	/**
	 * 刷卡撤销
	 */
	@Override
	public synchronized void updateState2(List<Sark> list,
			String workingbillid, String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getSarkTotalAmount();
		for (int i = 0; i < list.size(); i++) {
			Sark sark = list.get(i);
			if (sark.getState().equals("1")) {
				totalamount -= sark.getSarkAmount();
			}
			sark.setConfirmUser(admin);
			sark.setState("3");
			this.update(sark);
		}
		workingbill.setSarkTotalAmount(totalamount);
		workingbillService.update(workingbill);
	}
	
	
	/**
	 * 刷卡撤销 by Reece
	 */
	@Override
	public synchronized void updateCancel(List<Sark> list,String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
		for (int i = 0; i < list.size(); i++) {
			Sark sark = list.get(i);	
			sark.setConfirmUser(admin);
			sark.setState("3");
			this.update(sark);
		}
	}
	
	

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--新增前
	 */
	public List<SarkSon> getBomByConditions()
	{
		List<SarkSon>cslist=new ArrayList<SarkSon>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		if(list_wb!=null)
		{
			for(int i=0;i<list_wb.size();i++)
			{
				WorkingBill wb=list_wb.get(i);
				List<Bom> bomlist=this.bomService.getBomByConditions2(wb.getAufnr(), wb.getProductDate(),"506",wb.getWorkingBillCode());//根据订单号,生产日期,以"506"开关的查询
				if(bomlist.size() > 0) {
					for(Bom bom : bomlist) {
						if(bom!=null)
						{
							SarkSon cs=new SarkSon();
							cs.setMATNR(bom.getMaterialCode());//物料编码
							cs.setMATNRDES(bom.getMaterialName());//物料描述
							cs.setProductcode(wb.getMatnr());//产品编号
							cs.setProductname(wb.getMaktx());//产品名称
							//cs.setWbcode(wb.getWorkingBillCode());//随工单编码
							cs.setWbid(wb.getId());//随工单ID
							cs.setXcstotal(wb.getSarkTotalAmount()+"");//累计数量
							cslist.add(cs);
						}
					}
				}
			}
		}
		return cslist;
	}
	
	/**
	 * 新增保存
	 */
	public void saveData(List<SarkSon> list_cs, String cardnumber,String loginid,String bktxt)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		Admin login_admin=this.adminservice.get(loginid);
		/**主表数据插入*/
		Sark c=new Sark();
		c.setCreateDate(new Date());//创建日期
		c.setModifyDate(new Date());//修改日期
		c.setCreateUser(admin);//创建人
		c.setProductDate(login_admin.getProductDate());//生产日期
		c.setTeamshift(login_admin.getShift());//班次
		c.setFactoryUnit(login_admin.getTeam().getFactoryUnit());
		c.setState("2");//状态-未确认
		c.setBktxt(bktxt);
		String cid=this.save(c);//保存
		/**子表数据插入*/
		saveinfo(cid,list_cs);
	}
	
	/**
	 * 保存子表数据共用方法
	 */
	public void saveinfo(String cid,List<SarkSon> list_cs)
	{
		Sark c=this.get(cid);
		if(list_cs!=null)
		{
			for(int i=0;i<list_cs.size();i++)
			{
				SarkSon cs=list_cs.get(i);
				if(cs.getCscount()!=null&&!"".equals(cs.getCscount()))
				{
					cs.setSark(c);				
					this.csService.save(cs);
				}
			}
		}
	}
	
	/**
	 * 获取bom中随工单对应的以6开头的各个物料--编辑
	 */
	public List<SarkSon> getBomByConditions_edit(String id)
	{
		List<SarkSon>cslist=new ArrayList<SarkSon>();
		/**外表的数据*/
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		List<SarkSon>xlist=new ArrayList<SarkSon>();
		for(int i=0;i<list_wb.size();i++)
		{
			WorkingBill wb=list_wb.get(i);
			List<Bom> bomlist=this.bomService.getBomByConditions2(wb.getAufnr(), wb.getProductDate(),"506",wb.getWorkingBillCode());//根据订单号,生产日期,以"6"开关的查询
			if(bomlist.size() > 0) {
				for(Bom bom : bomlist) {
					if(bom!=null)
					{
						SarkSon cs=new SarkSon();
						cs.setMATNR(bom.getMaterialCode());//物料编码
						cs.setMATNRDES(bom.getMaterialName());//物料描述
						cs.setProductcode(wb.getMatnr());//产品编号
						cs.setProductname(wb.getMaktx());//产品名称
						//cs.setWbcode(wb.getWorkingBillCode());//随工单编码
						cs.setWbid(wb.getId());//随工单ID
						//cs.setXcstotal(wb.getSarkTotalAmount()+"");//累计数量
						xlist.add(cs);
					}
				}
			}
		}
		List<SarkSon> xcslist = new ArrayList<SarkSon>();
		for(SarkSon cs : xlist){
			boolean flag = true;
			for(SarkSon cs1 : xcslist){
				if(cs.getMATNR().equals(cs1.getMATNR()) && cs.getProductcode().equals(cs1.getProductcode())){
					flag = false;
					break;
				}
			}
			if(flag){
				xcslist.add(cs);
			}
		}
		xlist = xcslist;
		/**外表数据和本表的数据融合*/
		Sark c=this.get(id);
		List<SarkSon>xlist2=new ArrayList<SarkSon>(c.getSarksonSet());
		if(xlist2.size()>0&&xlist.size()>0)
		{
			for(int i=0;i<xlist.size();i++)
			{
				SarkSon cs=xlist.get(i);
				for(int j=0;j<xlist2.size();j++)
				{
					SarkSon cs2=xlist2.get(j);
					if(cs2.getMATNR().equals(cs.getMATNR()) && cs2.getProductcode().equals(cs.getProductcode()))
					{
						cs.setCscount(cs2.getCscount());
					}
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
	public void updateData(List<SarkSon> list_cs, String id,String bktxt)
	{
		Sark c=this.get(id);
		c.setBktxt(bktxt);// 修改单据编号
		this.update(c);
		List<SarkSon>list=new ArrayList<SarkSon>(c.getSarksonSet());//获取对应子表数据
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				SarkSon cs=list.get(i);
				this.csService.delete(cs.getId());//删除
			}
			
		}
		/*if (list_cs != null) {
			for (int i = 0; i < list_cs.size(); i++) {
				SarkSon cs = list.get(i);
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
			Sark c = this.get(ids[i]);
			List<SarkSon>list=new ArrayList<SarkSon>();
			List<SarkSon>listcs=new ArrayList<SarkSon>(c.getSarksonSet());
			for(int j=0;j<listcs.size();j++)
			{
				SarkSon cs=listcs.get(j);
				WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				cs.setLIFNR(lifnr);//供应商
				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
				cs.setLGORT(warehouse);//库存地点
				cs.setMOVE_TYPE("101");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
				Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getSarkTotalAmount());
				wb.setSarkTotalAmount(d.intValue());
				this.workingbillService.update(wb);
			}
			if(list.size()>0)
			{
				Sark c_return1=sarkRfc.SarkCrt("X",list);
				if("E".equals(c_return1.getE_TYPE()))
				{
					return c_return1.getE_MESSAGE();
				}
				Sark c_return=sarkRfc.SarkCrt("",list);
				if("E".equals(c_return.getE_TYPE()))
				{
					return c_return.getE_MESSAGE();
				}
				c.setE_TYPE(c_return.getE_TYPE());//返回类型
				c.setEX_MBLNR(c_return.getEX_MBLNR());//凭证号
				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
				c.setModifyDate(new Date());
				c.setConfirmUser(admin);//确认人
				c.setState("1");
				this.update(c);
			}
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
			Sark c = this.get(ids[i]);
			List<SarkSon>list=new ArrayList<SarkSon>();
			List<SarkSon>listcs=new ArrayList<SarkSon>(c.getSarksonSet());
			for(int j=0;j<listcs.size();j++)
			{
				SarkSon cs=listcs.get(j);
				//WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				WorkingBill wb = workingbillService.get(cs.getWbid());
				FactoryUnit fun = factoryUnitService.get("workCenter",wb.getWorkcenter());
				cs.setLIFNR(lifnr);//供应商
				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
				cs.setLGORT(fun.getWarehouse());//库存地点
				cs.setMOVE_TYPE("101");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
				//Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getSarkTotalAmount());
				//wb.setSarkTotalAmount(d.intValue());
				//this.workingbillService.update(wb);
			}
			if(list.size()>0)
			{
				Object[] c_returnobj = sarkRfc.SarkCrtNew("X",list);
				Sark c_return1=(Sark) c_returnobj[0];
				if("E".equals(c_return1.getE_TYPE()))
				{
					return c_return1.getE_MESSAGE();
				}
				Object[] c_returnobj1=sarkRfc.SarkCrtNew("",list);
				Sark c_return=(Sark) c_returnobj1[0];
				if("E".equals(c_return.getE_TYPE()))
				{
					return c_return.getE_MESSAGE();
				}
				c.setE_TYPE(c_return.getE_TYPE());//返回类型
				c.setEX_MBLNR(c_return.getEX_MBLNR());//凭证号
				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
				c.setModifyDate(new Date());
				c.setConfirmUser(admin);//确认人
				c.setState("1");
				this.update(c);
				List<SarkSon> cslist=  (List<SarkSon>) c_returnobj1[1];
				for(SarkSon cs : cslist){
					this.csService.update(cs);
				}
			}
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
			Sark c = this.get(ids[i]);
			List<SarkSon>list=new ArrayList<SarkSon>();
			List<SarkSon>listcs=new ArrayList<SarkSon>(c.getSarksonSet());
			for(int j=0;j<listcs.size();j++)
			{
				SarkSon cs=listcs.get(j);
				WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				FactoryUnit fun = factoryUnitService.get("workCenter",wb.getWorkcenter());
				cs.setLIFNR(lifnr);//供应商
				cs.setWERKS(werks);//工厂
				cs.setBUDAT(c.getProductDate());//过账日期
				cs.setLGORT(fun.getWarehouse());//库存地点
				cs.setMOVE_TYPE("102");//移动类型
				list.add(cs);
				this.csService.update(cs);
				//修改随工单中对应数量
				Double d=ArithUtil.add(Integer.parseInt(cs.getCscount()), wb.getSarkTotalAmount());
				wb.setSarkTotalAmount(d.intValue());
				this.workingbillService.update(wb);
			}
			if(list.size()>0)
			{
				Sark c_return1=sarkRfc.SarkCrt("X",list);
				if("E".equals(c_return1.getE_TYPE()))
				{
					return c_return1.getE_MESSAGE();
				}
				Sark c_return=sarkRfc.SarkCrt("",list);
				if("E".equals(c_return.getE_TYPE()))
				{
					return c_return.getE_MESSAGE();
				}
				c.setE_TYPE(c_return.getE_TYPE());//返回类型
				String str = c.getEX_MBLNR();
				c.setEX_MBLNR(str==null?"":str+"/"+c_return.getEX_MBLNR());//凭证号
				c.setE_MESSAGE(c_return.getE_MESSAGE());//返回消息
				c.setModifyDate(new Date());
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
//				c.setConfirmUser(admin);//确认人
				c.setRevokedUser(admin.getName());
				c.setRevokedTime(time);
				c.setRevokedUserCard(admin.getCardNumber());
				c.setRevokedUserId(admin.getId());
				c.setState("3");
				this.update(c);
			}
			if(listcs.size()==0&&list.size()==0){
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
				c.setRevokedUser(admin.getName());
				c.setRevokedTime(time);
				c.setRevokedUserCard(admin.getCardNumber());
				c.setRevokedUserId(admin.getId());
				c.setState("3");
				this.update(c);
			}
		}
		return "S";
	}
	/**
	 * 查看
	 */
	public List<SarkSon> getToShow(String id)
	{
		List<SarkSon> list_cs=new ArrayList<SarkSon>();
		//根据id查询主表
		Sark c=this.get(id);
		List<SarkSon> list=new ArrayList<SarkSon>(c.getSarksonSet());
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				SarkSon cs=list.get(i);
				//WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询随工单
				//cs.setXcstotal(wb.getSarkTotalAmount()+"");
				list_cs.add(cs);
			}
		}
		return list_cs;
	}


	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return sarkDao.historyjqGrid(pager, map);
	}

	@Override
	public Pager findSarkByPager(Pager pager, HashMap<String, String> mapcheck) {
		// TODO Auto-generated method stub
		return sarkDao.findSarkByPager(pager, mapcheck);
	}





}
