package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.RepairPieceService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Service实现类 返修
 */
@Service
@Transactional
public class RepairServiceImpl extends BaseServiceImpl<Repair, String>
		implements RepairService {
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
	private ProductsService productsService;
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService mService;//物料表
	@Resource
	private RepairPieceService rpService;//组件表

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
			if (statu.equals("1")) {
				totalamount = repair.getRepairAmount() + totalamount;
			}
			if (statu.equals("3") && repair.getState().equals("1")) {
				totalamount -= repair.getRepairAmount();
			}
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
	public void updateMyData(Repair repair,String cardnumber,int my_id)
	{
		Repair r=this.repairDao.get(repair.getId());//根据id查询
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		r.setConfirmUser(admin);//确认人
		r.setState("1");//状态改为已确认
		r.setModifyDate(new Date());//更新日期
		if(my_id==1)
		{
			r.setE_TYPE(repair.getE_TYPE());//返回类型：成功S/失败E
			r.setE_MESSAGE(repair.getE_MESSAGE());//返回消息
			r.setEX_MBLNR(repair.getEX_MBLNR());//返回物料凭证
		}
		this.repairDao.update(r);
	}

	/**
	 * 获取物料表中包含list1中的数据
	 */
	public List<Bom> getIncludedByMaterial(List<Bom> list1)
	{

		Admin admin = adminservice.getByCardnum(cardnumber);
		//Date productDate = ThinkWayUtil.formatStringDate(wb.getProductDate());//生产日期
		String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
		List<Bom> list_bom=bomService.findBom(aufnr, wb.getProductDate());
		String workingBillCode=wb.getWorkingBillCode();
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
					listbom.add(b);
				}
			}
		}
		return listbom;
	}

	/**
	 * 新增
	 */
	public void saveData(Repair repair, String cardnumber,List<RepairPiece>list_rp)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		/**保存主表数据*/
		repair.setCreateUser(admin);
		repair.setCreateDate(new Date());//创建日期
		repair.setModifyDate(new Date());//修改日期
		String rid=this.repairDao.save(repair);
		/**保存组件表数据*/
		Repair r=this.repairDao.get(rid);//根据id查询
		saveInfo(r,list_rp);
	}

	/**
	 * 修改
	 */
	public void updateData(Repair repair,List<RepairPiece>list_rp)
	{
		/**修改主表数据*/
		Repair r = repairDao.get(repair.getId());
		List<RepairPiece>list=new ArrayList<RepairPiece>(r.getRpieceSet());
		BeanUtils.copyProperties(repair, r, new String[] { "id" });
		r.setModifyDate(new Date());//修改日期
		this.repairDao.update(r);
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
		saveInfo(r,list_rp);
	}
	
	/**
	 * 新增组件数据共用方法
	 */
	public void saveInfo(Repair r,List<RepairPiece>list_rp)
	{
		if(list_rp!=null)
		{
			for(int i=0;i<list_rp.size();i++)
			{
				RepairPiece rp=list_rp.get(i);
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				//组件总数量=返修数量/产品数量 *组件数量
				rp.setRpcount(""+ArithUtil.mul(ArithUtil.div(r.getRepairAmount(), rp.getProductnum()), rp.getPiecenum()));//组件总数量
				rp.setRepair(r);
				this.rpService.save(rp);
			}
		}
	}
}