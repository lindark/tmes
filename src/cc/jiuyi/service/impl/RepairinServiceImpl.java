package cc.jiuyi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.sap.rfc.RepairInRfc;
import cc.jiuyi.sap.rfc.RepairRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.RepairinPieceService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutCalculateBase;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ArithUtil;

/**
 * Service实现类 返修收货
 */
@Service
public class RepairinServiceImpl extends BaseServiceImpl<Repairin, String>
		implements RepairinService,WorkingInoutCalculateBase<RepairinPiece> {
	public static Logger log = Logger.getLogger(RepairinService.class);
	
	@Resource
	private RepairinDao repairinDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;
	@Resource
	private RepairInRfc repairinRfc;
	
	@Resource
	public void setBaseDao(RepairinDao repairinDao) {
		super.setBaseDao(repairinDao);
	}
	@Resource
	private MaterialService mService;//物料表
	@Resource
	private RepairinPieceService rpService;//组件表
	@Resource
	private WorkingInoutService wiService;
	/**
	 * =========================================================
	 */
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairinDao.findPagerByjqGrid(pager, map, workingbillId);
	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		repairinDao.updateisdel(ids, oper);
	}

	@Override
	/**
	 * 考虑线程同步
	 */
	public synchronized String updateState(List<Repairin> list, String statu,
			String workingbillid, String cardnumber) {
		Admin admin = adminservice.getByCardnum(cardnumber);
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String time = dateFormat.format(date); 
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getTotalRepairinAmount();
		for (int i = 0; i < list.size(); i++) {
			Repairin repairin = list.get(i);
			if (statu.equals("1")) {
				totalamount = repairin.getReceiveAmount() + totalamount;
			}
			if (statu.equals("3") && repairin.getState().equals("1")) {
				totalamount -= repairin.getReceiveAmount();
			}
			if(repairin.getEX_MBLNR()!=null && repairin.getEX_MBLNR().contains("/")!=true){
				repairin = revokedSAP(repairin,admin,workingbillid,cardnumber);
				if(repairin.getE_TYPE().equals("E")){
					return repairin.getE_MESSAGE();
				}
			}
			repairin.setRevokedTime(time);
			repairin.setRevokedUser(admin.getName());
			repairin.setRevokedUserCard(cardnumber);
			repairin.setRevokedUserId(admin.getId());
			repairin.setState(statu);
			repairinDao.update(repairin);
		}
		workingbill.setTotalRepairinAmount(totalamount);
		workingbillService.update(workingbill);
		return "您的操作已成功!";
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return repairinDao.historyjqGrid(pager, map);
	}

	/**
	 * 1.与SAP交互没有问题,更新本地数据库
	 * 2.确认
	 */
	public void updateMyData(Repairin repairin,String cardnumber,int my_id,String wbid)
	{
		Repairin r=this.get(repairin.getId());//根据id查询
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		r.setConfirmUser(admin);//确认人
		r.setState("1");//状态改为已确认
		r.setModifyDate(new Date());//更新日期
		if(my_id==1)
		{
			r.setE_TYPE(repairin.getE_TYPE());//返回类型：成功S/失败E
			r.setE_MESSAGE(repairin.getE_MESSAGE());//返回消息
			r.setEX_MBLNR(repairin.getEX_MBLNR());//返回物料凭证
			WorkingBill wb = workingbillService.get(wbid);
			//Double d1=ArithUtil.add(wb.getRepairamount(),r.getReceiveAmount());//返修,返修收货共用数量
			Double d2=ArithUtil.add(wb.getTotalRepairinAmount(),r.getReceiveAmount());//投入产出
			//wb.setRepairamount(d1);//返修,返修收货共用数量
			wb.setTotalRepairinAmount(d2.intValue());//投入产出
			this.workingbillService.merge(wb);
			/*List<RepairinPiece>list_rp=new ArrayList<RepairinPiece>(r.getRpieceSet());
			HashMap<String,Object>map=new HashMap<String,Object>();
			map.put("wbid", wbid);//随工单ID
			updateWorkingInoutCalculate(list_rp,map);*/
		}
		this.merge(r);
	}

	/**
	 * 获取物料表中包含list1中的数据
	 */
	public List<Bom> getIncludedByMaterial(List<Bom> list1,int plancount,FactoryUnit factoryUnit)
	{
		List<Bom>listbom=new ArrayList<Bom>();
		if(list1.size()>0)
		{
			//物料表是否存在
			for(int i=0;i<list1.size();i++)
			{
				Bom b=list1.get(i);
				//根据物料id查询是否存在
				if(this.mService.getByCode(b.getMaterialCode(),factoryUnit))
				{
					b.setXplancount(plancount);//随工单计划数量
					listbom.add(b);
				}
			}
		}
		return listbom;
	}

	/**
	 * 新增
	 */
	public void saveData(Repairin repairin, String cardnumber,List<RepairinPiece>list_rp,List<Bom>list_bom,Admin admin1)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill wb = workingbillService.get(repairin.getWorkingbill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		String item_text=workingBillCode.substring(workingBillCode.length()-2);
		String productdate=wb.getProductDate();
		productdate=productdate.replace("-","").substring(2);
		String charg=productdate+item_text;
		/**保存主表数据*/
		repairin.setCreateUser(admin);
		repairin.setCreateDate(new Date());//创建日期
		repairin.setModifyDate(new Date());//修改日期
		repairin.setZTEXT(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
		repairin.setBUDAT(wb.getProductDate());//过账日期
		if(admin1 !=null){
			repairin.setProductDate(admin1.getProductDate());//生产日期
			repairin.setShift(admin1.getShift());//班次
			if(admin1.getTeam() !=null){
				repairin.setFactoryUnitCode(admin1.getTeam().getFactoryUnit().getFactoryUnitCode());
				repairin.setFactoryUnitName(admin1.getTeam().getFactoryUnit().getFactoryUnitName());
			}
		}
		if(wb !=null){
			repairin.setWorkingbillCode(wb.getWorkingBillCode());//随工单编号
			repairin.setMatnr(wb.getMatnr());//产品编号
		}
		String rid=this.save(repairin);
		/**保存组件表数据*/
		Repairin r=this.get(rid);//根据id查询
		//2.新增
		if("ZJ".equals(repairin.getRepairintype()))
		{
			//组件--选择的组件
			saveInfo(r,list_rp,item_text,charg);
		}
		else if("CP".equals(repairin.getRepairintype()))
		{
			//成品--所有组件
			double d=1d;
			if(wb.getPlanCount()!=null&&wb.getPlanCount()!=0)
			{
				d=wb.getPlanCount();
			}
			saveInfo2(r,list_bom,item_text,d,charg);
		}
	}

	/**
	 * 修改
	 */
	public void updateData(Repairin repairin,List<RepairinPiece>list_rp,String cardnumber,List<Bom>list_bom,Admin admin1)
	{
		//Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill wb = workingbillService.get(repairin.getWorkingbill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		String item_text=workingBillCode.substring(workingBillCode.length()-2);
		String productdate=wb.getProductDate();
		productdate=productdate.replace("-","").substring(2);
		String charg=productdate+item_text;
		/**修改主表数据*/
		Repairin r = this.get(repairin.getId());
		List<RepairinPiece>list=new ArrayList<RepairinPiece>(r.getRpieceSet());
		r.setDepartName(repairin.getDepartName());
		r.setCostcenter(repairin.getCostcenter());
		r.setReceiveAmount(repairin.getReceiveAmount());//返修收货数量
		r.setModifyDate(new Date());//修改日期
		//r.setCreateUser(admin);
		if(admin1 !=null){
			repairin.setProductDate(admin1.getProductDate());//生产日期
			repairin.setShift(admin1.getShift());//班次
			if(admin1.getTeam() !=null){
				repairin.setFactoryUnitCode(admin1.getTeam().getFactoryUnit().getFactoryUnitCode());
				repairin.setFactoryUnitName(admin1.getTeam().getFactoryUnit().getFactoryUnitName());
			}
		}
		if(wb !=null){
			repairin.setWorkingbillCode(wb.getWorkingBillCode());//随工单编号
			repairin.setMatnr(wb.getMatnr());//产品编号
		}
		this.update(r);
		/**修改组件表数据*/
		//1.删除原数据
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				RepairinPiece rp=list.get(i);
				this.rpService.delete(rp.getId());
			}
		}
		//2.新增
		if("ZJ".equals(repairin.getRepairintype()))
		{
			//组件--选择的组件
			saveInfo(r,list_rp,item_text,charg);
		}
		else if("CP".equals(repairin.getRepairintype()))
		{
			//成品--所有组件
			double d=1d;
			if(wb.getPlanCount()!=null&&wb.getPlanCount()!=0)
			{
				d=wb.getPlanCount();
			}
			saveInfo2(r,list_bom,item_text,d,charg);
		}
	}
	
	/**
	 * 新增组件数据共用方法
	 */
	public void saveInfo(Repairin r,List<RepairinPiece>list_rp,String item_text,String charg)
	{
		if(list_rp!=null)
		{
			for(int i=0;i<list_rp.size();i++)
			{
				RepairinPiece rp=list_rp.get(i);
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				rp.setITEM_TEXT(item_text);
				rp.setCHARG(charg);//批次
				//组件总数量=返修数量/产品数量 *组件数量
				if(rp.getProductnum()==null||rp.getProductnum()==0)
				{
					rp.setProductnum(Double.parseDouble("1"));
					rp.setRpcount(ArithUtil.mul(r.getReceiveAmount(),rp.getPiecenum()));//乘法
				}
				else
				{
					double d1=ArithUtil.div(rp.getPiecenum(), rp.getProductnum());//除法
					double d2=ArithUtil.mul(d1, r.getReceiveAmount());//乘法
					double d3=ArithUtil.round(d2, 3);//四舍五入,保留3位
					rp.setRpcount(d3);//组件总数量
				}
				rp.setRepairin(r);
				this.rpService.save(rp);
			}
		}
	}
	
	/**
	 * 新增组件数据共用方法2
	 */
	public void saveInfo2(Repairin r,List<Bom>list_bom,String item_text,double plancount,String charg)
	{
		if(list_bom!=null)
		{
			for(int i=0;i<list_bom.size();i++)
			{
				RepairinPiece rp=new RepairinPiece();
				Bom b=list_bom.get(i);
				rp.setITEM_TEXT(item_text);
				rp.setCHARG(charg);//批次
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				rp.setRpcode(b.getMaterialCode());//物料编码
				rp.setRpname(b.getMaterialName());//组件名称
				rp.setProductnum(plancount);//产品数量
				rp.setPiecenum(b.getMaterialAmount());//组件数量
				//组件总数量=返修数量/产品数量 *组件数量
				if(rp.getProductnum()==null||rp.getProductnum()==0)
				{
					rp.setProductnum(Double.parseDouble("1"));
					rp.setRpcount(ArithUtil.mul(r.getReceiveAmount(), rp.getPiecenum()));
				}
				else
				{
					Double d1=ArithUtil.div(rp.getPiecenum(), rp.getProductnum());//除法
					Double d2=ArithUtil.mul(d1, r.getReceiveAmount());//乘法
					Double d3=ArithUtil.round(d2, 3);//四舍五入,保留x位
					rp.setRpcount(d3);//组件总数量
				}
				rp.setRepairin(r);
				this.rpService.save(rp);
			}
		}
	}
	@Override
	public void updateWorkingInoutCalculate(List<RepairinPiece> list_rp,
			HashMap<String, Object> map)
	{
		for(int i=0;i<list_rp.size();i++)
		{
			RepairinPiece rp=list_rp.get(i);
			String wbid=map.get("wbid").toString();//随工单ID
			String code=rp.getRpcode();//物料编码
			String des=rp.getRpname();//物料描述
			Double count=rp.getRpcount();//数量
			WorkingInout wi = this.wiService.findWorkingInout(wbid, code);
			if(wi!=null)
			{
				Double wicount=wi.getRepairinAmount();//数量
				if(wicount==null)
				{
					wicount=0d;
				}
				wi.setRepairAmount(ArithUtil.add(wicount, count));
				wi.setModifyDate(new Date());
				this.wiService.update(wi);
			}
			else
			{
				/**新增*/
				WorkingBill wb=this.workingbillService.get(wbid);//根据ID查询一条
				WorkingInout w=new WorkingInout();
				w.setWorkingbill(wb);//随工单
				w.setMaterialCode(code);//物料号
				w.setMaterialName(des);//物料描述
				w.setRepairinAmount((count));//数量
				w.setCreateDate(new Date());//初始化创建日期
				w.setModifyDate(new Date());//初始化修改日期
				this.wiService.save(w);
			}
		}
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return repairinDao.historyExcelExport(map);
	}
	
	public Repairin revokedSAP(Repairin repairin,Admin admin,String workingbillid,String cardnumber){
		try{
			String oldMblur = repairin.getEX_MBLNR()+"/";
			if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null){
				repairin.setLGORT(admin.getTeam().getFactoryUnit().getWarehouse());//库存地点
			}else{
				repairin.setE_TYPE("E");
				repairin.setE_MESSAGE("无法找到刷卡人的班次或单元信息");
				return repairin;
			}
			if(admin.getTeam().getFactoryUnit().getWorkShop()!=null&&admin.getTeam().getFactoryUnit().getWorkShop().getFactory()!=null){
				repairin.setWERKS(admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
			}else{
				repairin.setE_TYPE("E");
				repairin.setE_MESSAGE("无法找到刷卡人的车间或工厂信息");
				return repairin;
			}
			List<RepairinPiece> listrp = new ArrayList<RepairinPiece>(repairin.getRpieceSet());// 取出对应的组件
			String oldMblnr = repairin.getEX_MBLNR();
			if (listrp.size() > 0){
				/**有组件数据,进行SAP交互*/
				// 调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
				Repairin r_sapreturn1 = repairinRfc.revokedRepairCrt("X",repairin, listrp);
				if(r_sapreturn1.getEX_MBLNR()!=null && !"".equals(r_sapreturn1.getEX_MBLNR())){
					log.info("---X----ex_mblnr---"+r_sapreturn1.getEX_MBLNR());
				}
				/** 出现问题 */
				if ("E".equalsIgnoreCase(r_sapreturn1.getE_TYPE()))
				{
					return r_sapreturn1;
				}else{
					Repairin r_sapreturn = repairinRfc.revokedRepairCrt("",repairin, listrp);
					r_sapreturn.setEX_MBLNR(oldMblnr+"/"+r_sapreturn.getEX_MBLNR());
					if ("E".equalsIgnoreCase(r_sapreturn.getE_TYPE())){
						return r_sapreturn;
					}
					log.info("-------mblnr---"+r_sapreturn.getEX_MBLNR());
					if ("E".equalsIgnoreCase(r_sapreturn.getE_TYPE())){
						return r_sapreturn;
					}
					if(r_sapreturn.getEX_MBLNR()==null || oldMblur.equals(r_sapreturn.getEX_MBLNR())){
						r_sapreturn.setE_MESSAGE("未返回凭证");
					}
					/** 与SAP交互没有问题,更新本地数据库 */
					this.updateMyData(r_sapreturn, cardnumber,1,workingbillid);
				}
			}else{
				/**没有组件数据,只把状态改为确认*/
				this.updateMyData(repairin, cardnumber,2,"");
			}
		return repairin;
		}catch(Exception e){
			return repairin;
		}
	}
}
