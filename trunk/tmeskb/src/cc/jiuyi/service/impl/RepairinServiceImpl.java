package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.RepairinPieceService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ArithUtil;

/**
 * Service实现类 返修收货
 */
@Service
public class RepairinServiceImpl extends BaseServiceImpl<Repairin, String>
		implements RepairinService {
	@Resource
	private RepairinDao repairinDao;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private AdminService adminservice;

	@Resource
	public void setBaseDao(RepairinDao repairinDao) {
		super.setBaseDao(repairinDao);
	}
	@Resource
	private MaterialService mService;//物料表
	@Resource
	private RepairinPieceService rpService;//组件表
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
	public synchronized void updateState(List<Repairin> list, String statu,
			String workingbillid, String cardnumber) {
		//Admin admin = adminservice.getByCardnum(cardnumber);
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
			//repairin.setConfirmUser(admin);
			//repairin.setState(statu);
			//repairinDao.update(repairin);
		}
		workingbill.setTotalRepairinAmount(totalamount);
		workingbillService.update(workingbill);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return repairinDao.historyjqGrid(pager, map);
	}

	/**
	 * 1.与SAP交互没有问题,更新本地数据库
	 * 2.确认
	 */
	public void updateMyData(Repairin repairin,String cardnumber,int my_id)
	{
		Repairin r=this.repairinDao.get(repairin.getId());//根据id查询
		Admin admin=this.adminservice.getByCardnum(cardnumber);
		r.setConfirmUser(admin);//确认人
		r.setState("1");//状态改为已确认
		r.setModifyDate(new Date());//更新日期
		if(my_id==1)
		{
			r.setE_TYPE(repairin.getE_TYPE());//返回类型：成功S/失败E
			r.setE_MESSAGE(repairin.getE_MESSAGE());//返回消息
			r.setEX_MBLNR(repairin.getEX_MBLNR());//返回物料凭证
		}
		this.repairinDao.update(r);
	}

	/**
	 * 获取物料表中包含list1中的数据
	 */
	public List<Bom> getIncludedByMaterial(List<Bom> list1)
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
					listbom.add(b);
				}
			}
		}
		return listbom;
	}

	/**
	 * 新增
	 */
	public void saveData(Repairin repairin, String cardnumber,List<RepairinPiece>list_rp)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		/**保存主表数据*/
		repairin.setCreateUser(admin);
		repairin.setCreateDate(new Date());//创建日期
		repairin.setModifyDate(new Date());//修改日期
		String rid=this.repairinDao.save(repairin);
		/**保存组件表数据*/
		Repairin r=this.repairinDao.get(rid);//根据id查询
		saveInfo(r,list_rp);
	}

	/**
	 * 修改
	 */
	public void updateData(Repairin repairin,List<RepairinPiece>list_rp,String cardnumber)
	{
		Admin admin = adminservice.getByCardnum(cardnumber);
		/**修改主表数据*/
		Repairin r = repairinDao.get(repairin.getId());
		List<RepairinPiece>list=new ArrayList<RepairinPiece>(r.getRpieceSet());
		BeanUtils.copyProperties(repairin, r, new String[] { "id" });
		r.setModifyDate(new Date());//修改日期
		r.setCreateUser(admin);
		this.repairinDao.update(r);
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
		saveInfo(r,list_rp);
	}
	
	/**
	 * 新增组件数据共用方法
	 */
	public void saveInfo(Repairin r,List<RepairinPiece>list_rp)
	{
		if(list_rp!=null)
		{
			for(int i=0;i<list_rp.size();i++)
			{
				RepairinPiece rp=list_rp.get(i);
				rp.setCreateDate(new Date());//创建日期
				rp.setModifyDate(new Date());//修改日期
				//组件总数量=返修数量/产品数量 *组件数量
				rp.setRpcount(""+ArithUtil.mul(ArithUtil.div(r.getReceiveAmount(), rp.getProductnum()), rp.getPiecenum()));//组件总数量
				rp.setRepairin(r);
				this.rpService.save(rp);
			}
		}
	}
}
