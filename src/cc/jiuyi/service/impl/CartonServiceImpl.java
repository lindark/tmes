package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.CartonRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.CartonsonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 纸箱
 */
@Repository
public class CartonServiceImpl extends BaseServiceImpl<Carton, String> implements CartonService {

	@Resource
	private CartonDao cartonDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	@Resource
	private CartonRfc cartonRfc;
	@Resource
	private DictService dictService;
	@Resource
	private BomService bomService;
	@Resource
	private CartonsonService csService;

	@Resource
	public void setBaseDao(CartonDao cartonDao) {
		super.setBaseDao(cartonDao);
	}

	/**
	 * jqgrid查询
	 */
	public Pager getCartonPager(Pager pager)
	{
		return cartonDao.getCartonPager(pager);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		cartonDao.updateisdel(ids, oper);

	}

	/**
	 * 刷卡确认
	 * 考虑线程同步
	 */
	public synchronized String updateState(List<Carton> list,String workingbillid, String cardnumber) throws IOException,CustomerException {
		/*
		List<Carton> cartonList = new ArrayList<Carton>();
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
			Carton carton = list.get(i);
			// carton.setCartonCode("50110123");
			carton.setCartonCode(matnr);
			carton.setMove_type("101");
			// carton.setLgort("2501");
			carton.setLgort(warehouse);
			// carton.setWerks("1000");
			carton.setWerks(werks);
			carton.setBudat(budat);
			carton.setLifnr(ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1"));
			//System.out.println("Lifnr"+carton.getLifnr());
			cartonList.add(carton);
		}
		    
		//调用sap函数接口
		cartonList = cartonRfc.CartonCrt(cartonList);
		//根据返回集合，判断函数调用是否成功
		for (int i = 0; i < cartonList.size(); i++) {
			Carton caron = cartonList.get(i);
			if ("S".equals(caron.getE_type())) {
				System.out.println("SAP同步成功");
				System.out.println(caron.getE_message());
			} else if ("E".equals(caron.getE_type())) {
				System.out.println("SAP同步失败");
				throw new CustomerException(caron.getE_message());
			}
		}
		//若成功，则更新数据库
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
		/*
		 * for (int i = 0; i < list.size(); i++) { Carton carton = list.get(i);
		 * totalamount = carton.getCartonAmount() + totalamount;
		 * carton.setConfirmUser(admin); carton.setState("1");
		 * cartonDao.update(carton); }
		 *
		workingbill.setCartonTotalAmount(totalamount);
		workingbillService.update(workingbill);
		*/
		return null;
	}


	/**
	 * 刷卡撤销
	 */
	@Override
	public synchronized void updateState2(List<Carton> list,
			String workingbillid, String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
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

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return cartonDao.historyjqGrid(pager, map);
	}

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--新增前
	 */
	public List<CartonSon> getBomByConditions()
	{
		List<CartonSon>cslist=new ArrayList<CartonSon>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		if(list_wb!=null)
		{
			for(int i=0;i<list_wb.size();i++)
			{
				WorkingBill wb=list_wb.get(i);
				Bom bom=this.bomService.getBomByConditions(wb.getAufnr(), wb.getProductDate(),"5");//根据订单号,生产日期,以"5"开关的查询
				if(bom!=null)
				{
					CartonSon cs=new CartonSon();
					cs.setMATNR(bom.getMaterialCode());//物料编码
					cs.setMATNRDES(bom.getMaterialName());//物料描述
					cs.setProductcode(wb.getMatnr());//产品编号
					cs.setProductname(wb.getMaktx());//产品名称
					cs.setWbcode(wb.getWorkingBillCode());//随工单编码
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
	public void saveData(List<CartonSon> list_cs, String cardnumber)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		/**主表数据插入*/
		Carton c=new Carton();
		c.setCreateDate(new Date());//创建日期
		c.setModifyDate(new Date());//修改日期
		c.setCreateUser(admin);//创建人
		c.setState("2");//状态-未确认
		String cid=this.cartonDao.save(c);//保存
		/**子表数据插入*/
		saveinfo(cid,list_cs);
	}
	
	/**
	 * 保存子表数据共用方法
	 */
	public void saveinfo(String cid,List<CartonSon> list_cs)
	{
		if(list_cs!=null)
		{
			for(int i=0;i<list_cs.size();i++)
			{
				CartonSon cs=list_cs.get(i);
				if(cs.getCscount()!=null&&!"".equals(cs.getCscount()))
				{
					this.csService.save(cs);
				}
			}
		}
	}
	
	/**
	 * 获取bom中随工单对应的以5开头的各个物料--编辑
	 */
	public List<CartonSon> getBomByConditions_edit(String id)
	{
		List<CartonSon>cslist=new ArrayList<CartonSon>();
		/**外表的数据*/
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		List<WorkingBill>list_wb=workingbillService.getListWorkingBillByDate(admin);
		List<CartonSon>xlist=new ArrayList<CartonSon>();
		for(int i=0;i<list_wb.size();i++)
		{
			WorkingBill wb=list_wb.get(i);
			Bom bom=this.bomService.getBomByConditions(wb.getAufnr(), wb.getProductDate(),"5");//根据订单号,生产日期,以"5"开关的查询
			if(bom!=null)
			{
				CartonSon cs=new CartonSon();
				cs.setMATNR(bom.getMaterialCode());//产品编号(物料编码)
				cs.setMATNRDES(bom.getMaterialName());//产品名称(物料描述)
				cs.setXcstotal(wb.getCartonTotalAmount()+"");//累计数量
				cs.setWbid(wb.getId());//随工单ID
				xlist.add(cs);
			}
		}
		/**外表数据和本表的数据融合*/
		Carton c=this.cartonDao.get(id);
		List<CartonSon>xlist2=new ArrayList<CartonSon>(c.getCartonsonSet());
		if(xlist2.size()>0&&xlist.size()>0)
		{
			for(int i=0;i<xlist.size();i++)
			{
				CartonSon cs=xlist.get(i);
				for(int j=0;j<xlist2.size();j++)
				{
					CartonSon cs2=xlist2.get(j);
					if(cs2.getWbid().equals(cs.getWbid()))
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
	public void updateData(List<CartonSon> list_cs, String id)
	{
		Carton c=this.cartonDao.get(id);
		List<CartonSon>list=new ArrayList<CartonSon>(c.getCartonsonSet());//获取对应子表数据
		c.setModifyDate(new Date());//修改日期
		this.cartonDao.update(c);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				CartonSon cs=list.get(i);
				this.csService.delete(cs.getId());//删除
			}
			
		}
		/**子表数据插入*/
		saveinfo(id,list_cs);
	}

	/**
	 * 与SAP交互
	 */
	public String updateToSAP(String[] ids,String cardnumber)throws IOException,CustomerException
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();// 线边仓(库存地点)
		String lifnr= ThinkWayUtil.getDictValueByDictKey(dictService,"lifnr", "1");//供应商
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂
		for (int i = 0; i < ids.length; i++)
		{
			Carton c = this.cartonDao.get(ids[i]);
			List<CartonSon>list=new ArrayList<CartonSon>();
			List<CartonSon>listcs=new ArrayList<CartonSon>(c.getCartonsonSet());
			for(int j=0;j<listcs.size();j++)
			{
				CartonSon cs=listcs.get(j);
				WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
				cs.setLIFNR(lifnr);//供应商
				cs.setWERKS(werks);//工厂
				cs.setBUDAT(wb.getProductDate());//过账日期
				cs.setLGORT(warehouse);//库存地点
				cs.setMOVE_TYPE("101");//移动类型
				list.add(cs);
				this.csService.update(cs);
			}
			if(list.size()>0)
			{
				for(int j=0;j<list.size();j++)
				{
					CartonSon cs=list.get(j);
					cartonRfc.CartonCrt(cs);
					if("E".equals(cs.getE_TYPE()))
					{
						return cs.getE_MESSAGE();
					}
					WorkingBill wb=this.workingbillService.get(cs.getWbid());//根据id查询一条
					wb.setCartonTotalAmount(Integer.parseInt(ArithUtil.add(Double.parseDouble(cs.getCscount()), wb.getCartonTotalAmount())+""));
					this.workingbillService.update(wb);
				}
			}
			c.setState("1");
			this.cartonDao.update(c);
		}
		return "S";
	}
}
