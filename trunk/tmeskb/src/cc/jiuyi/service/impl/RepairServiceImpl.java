package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.RepairPieceService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutCalculateBase;
import cc.jiuyi.util.ArithUtil;

/**
 * Service实现类 返修
 */
@Service
public class RepairServiceImpl extends BaseServiceImpl<Repair, String>
		implements RepairService,WorkingInoutCalculateBase<RepairPiece> {
	@Resource
	private RepairDao repairDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(RepairDao repairDao) {
		super.setBaseDao(repairDao);
	}
	@Resource
	private MaterialService mService;//物料表
	@Resource
	private RepairPieceService rpService;//组件表
	//@Resource
	//private WorkingInoutService wiService;

	/**===========================================================*/
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return repairDao.findPagerByjqGrid(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		repairDao.updateisdel(ids, oper);
	}

	@Override
	/**
	 * 考虑线程同步
	 */
	public synchronized void updateState(List<Repair> list, String statu,
			String workingbillid,String cardnumber) {
		//Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill workingbill = workingbillService.get(workingbillid);
		Integer totalamount = workingbill.getTotalRepairAmount();
		for (int i = 0; i < list.size(); i++) {
			Repair repair = list.get(i);
			totalamount = repair.getRepairAmount() + totalamount;
			/*if (statu.equals("3") && repair.getState().equals("1")) {
				totalamount -= repair.getRepairAmount();
			}*/
			//repair.setConfirmUser(admin);
			//repairDao.update(repair);
		}
		workingbill.setTotalRepairAmount(totalamount);
		workingbillService.update(workingbill);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return repairDao.historyjqGrid(pager, map);
	}

	/**
	 * 与SAP交互没有问题,更新本地数据库
	 */
	public void updateMyData(Repair r_return,String cardnumber,int my_id,String wbid)
	{
		Repair r=this.get(r_return.getId());//根据id查询
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		r.setConfirmUser(admin);//确认人
		r.setState("1");//状态改为已确认
		r.setModifyDate(new Date());//更新日期
		if(my_id==1)
		{
			r.setE_TYPE(r_return.getE_TYPE());//返回类型：成功S/失败E
			r.setE_MESSAGE(r_return.getE_MESSAGE());//返回消息
			r.setEX_MBLNR(r_return.getEX_MBLNR());//返回物料凭证
			WorkingBill wb = workingbillService.get(wbid);
			//Double d1=ArithUtil.sub(wb.getRepairamount(),r.getRepairAmount());//返修,返修收货共用数量
			Double d2=ArithUtil.add(wb.getTotalRepairAmount(),r.getRepairAmount());//投入产出数量
			//wb.setRepairamount(d1);//返修,返修收货共用数量
			wb.setTotalRepairAmount(d2.intValue());//投入产出数量
			this.workingbillService.merge(wb);
			/*List<RepairPiece>list_rp=new ArrayList<RepairPiece>(r.getRpieceSet());
			HashMap<String,Object>map=new HashMap<String,Object>();
			map.put("wbid", wbid);//随工单ID
			updateWorkingInoutCalculate(list_rp,map);*/
		}
		this.merge(r);
	}

	/**
	 * 获取物料表中包含list1中的数据
	 */
	public List<Bom> getIncludedByMaterial(List<Bom> list1,int plancount)
	{
		List<Bom>listbom=new ArrayList<Bom>();
		if(list1.size()>0)
		{
			//物料表是否存在
			for(int i=0;i<list1.size();i++)
			{
				Bom b=list1.get(i);
				//根据物料id查询是否存在
				if(this.mService.getByCode(b.getMaterialCode()))
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
	public void saveData(Repair repair, String cardnumber,List<RepairPiece>list_rp,List<Bom>list_bom)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill wb = workingbillService.get(repair.getWorkingbill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		/**保存主表数据*/
		repair.setCreateUser(admin);
		repair.setCreateDate(new Date());//创建日期
		repair.setModifyDate(new Date());//修改日期
		repair.setZTEXT(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
		repair.setBUDAT(wb.getProductDate());//过账日期
		String rid=this.save(repair);
		/**保存组件表数据*/
		Repair r=this.get(rid);//根据id查询
		if("ZJ".equals(repair.getRepairtype()))
		{
			//组件--选择的组件
			saveInfo(r,list_rp,workingBillCode);
		}
		else if("CP".equals(repair.getRepairtype()))
		{
			//成品--所有组件
			double d=1d;
			if(wb.getPlanCount()!=null&&wb.getPlanCount()!=0)
			{
				d=wb.getPlanCount();
			}
			saveInfo2(r,list_bom,workingBillCode,d);
		}
	}

	/**
	 * 修改
	 */
	public void updateData(Repair repair,List<RepairPiece>list_rp,String cardnumber,List<Bom>list_bom)
	{
		//Admin admin = adminservice.getByCardnum(cardnumber);
		WorkingBill wb = workingbillService.get(repair.getWorkingbill().getId());//当前随工单
		String workingBillCode = wb.getWorkingBillCode();
		/**修改主表数据*/
		Repair r = this.get(repair.getId());
		List<RepairPiece>list=new ArrayList<RepairPiece>(r.getRpieceSet());
		r.setRepairAmount(repair.getRepairAmount());//返修数量
		r.setRepairPart(repair.getRepairPart());//返修部位
		r.setDuty(repair.getDuty());//责任人/批次
		r.setProcessCode(repair.getProcessCode());//责任工序
		r.setMould(repair.getMould());//模具
		r.setModifyDate(new Date());//修改日期
		//r.setCreateUser(admin);
		this.update(r);
		/**修改组件表数据*/
		//1.删除原数据
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				RepairPiece rp=list.get(i);
				this.rpService.delete(rp.getId());
			}
		}
		//2.新增
		if("ZJ".equals(repair.getRepairtype()))
		{
			//组件--选择的组件
			saveInfo(r,list_rp,workingBillCode);
		}
		else if("CP".equals(repair.getRepairtype()))
		{
			//成品--所有组件
			double d=1d;
			if(wb.getPlanCount()!=null&&wb.getPlanCount()!=0)
			{
				d=wb.getPlanCount();
			}
			saveInfo2(r,list_bom,workingBillCode,d);
		}
	}
	
	/**
	 * 新增组件数据共用方法
	 */
	public void saveInfo(Repair r,List<RepairPiece>list_rp,String workingBillCode)
	{
		if(list_rp!=null)
		{
			for(int i=0;i<list_rp.size();i++)
			{
				RepairPiece rp=list_rp.get(i);
				rp.setITEM_TEXT(workingBillCode.substring(workingBillCode.length()-2));
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				//组件总数量=返修数量/产品数量 *组件数量
				if(rp.getProductnum()==null||rp.getProductnum()==0)
				{
					rp.setProductnum(Double.parseDouble("1"));//产品数量
					rp.setRpcount(""+ArithUtil.mul(r.getRepairAmount(), rp.getPiecenum()));
				}
				else
				{
					double d1=ArithUtil.div(rp.getPiecenum(), rp.getProductnum());//除法
					double d2=ArithUtil.mul(d1, r.getRepairAmount());//乘法
					double d3=ArithUtil.round(d2, 3);//四舍五入,保留3位
					rp.setRpcount(""+d3);//组件总数量
				}
				rp.setRepair(r);
				this.rpService.save(rp);
			}
		}
	}
	
	/**
	 * 新增组件数据共用方法2
	 */
	public void saveInfo2(Repair r,List<Bom>list_bom,String workingBillCode,Double plancount)
	{
		if(list_bom!=null)
		{
			for(int i=0;i<list_bom.size();i++)
			{
				RepairPiece rp=new RepairPiece();
				Bom b=list_bom.get(i);
				rp.setITEM_TEXT(workingBillCode.substring(workingBillCode.length()-2));
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				rp.setRpcode(b.getMaterialCode());//物料编码
				rp.setRpname(b.getMaterialName());//组件名称
				rp.setProductnum(plancount);//产品数量
				rp.setPiecenum(b.getMaterialAmount());//组件数量
				//组件总数量=返修数量/产品数量 *组件数量
				if(rp.getProductnum()==null||rp.getProductnum()==0)
				{
					rp.setProductnum(Double.parseDouble("1"));//产品数量
					rp.setRpcount(""+ArithUtil.mul(r.getRepairAmount(), rp.getPiecenum()));
				}
				else
				{
					Double d1=ArithUtil.div(rp.getPiecenum(), rp.getProductnum());//除法
					Double d2=ArithUtil.mul(d1, r.getRepairAmount());//乘法
					Double d3=ArithUtil.round(d2, 3);//四舍五入,保留3位
					rp.setRpcount(""+d3);//组件总数量
				}
				rp.setRepair(r);
				this.rpService.save(rp);
			}
		}
	}

	/**
	 * 投入产出
	 */
	public void updateWorkingInoutCalculate(List<RepairPiece> list_rp,HashMap<String, Object> map)
	{
		/*for(int i=0;i<list_rp.size();i++)
		{
			RepairPiece rp=list_rp.get(i);
			String wbid=map.get("wbid").toString();//随工单ID
			String code=rp.getRpcode();//物料编码
			Double count=Double.parseDouble(rp.getRpcount());//数量
			WorkingInout wi = this.wiService.findWorkingInout(wbid, code);
			if(wi!=null)
			{
				Double wicount=wi.getRepairAmount();//数量
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
				*//**新增*//*
				WorkingBill wb=this.workingbillService.get(wbid);//根据ID查询一条
				WorkingInout w=new WorkingInout();
				w.setWorkingbill(wb);//随工单
				w.setMaterialCode(code);//物料号
				w.setRepairAmount((count));//数量
				w.setCreateDate(new Date());//初始化创建日期
				w.setModifyDate(new Date());//初始化修改日期
				this.wiService.save(w);
			}
		}*/
	}
}