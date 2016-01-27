package cc.jiuyi.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutService;

/**
 * Service实现类 -领/退料
 * @author Reece
 *
 */

@Service
public class PickDetailServiceImpl extends BaseServiceImpl<PickDetail, String>implements PickDetailService{

	@Resource
	private PickDetailDao pickDetailDao;
	@Resource
	private WorkingInoutService workingInoutService;
	@Resource
	private BomService bomService;

	@Resource
	public void setBaseDao(PickDetailDao pickDetailDao){
		super.setBaseDao(pickDetailDao);
	}
	@Resource
	private AdminService adminService;
	@Resource
	private PickService pickService;
	@Resource
	private WorkingBillService workingBillService;
//	@Resource
//	private PickDetailService pickDetailService;
//	@Resource
//	private PickRfcImpl pickRfcImpl;
	
	
	private String pickRfc;
	private List<PickDetail> pkList;
	
	
	@Override
	public void delete(String id) {
		PickDetail pickDetail = pickDetailDao.load(id);
		this.delete(pickDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<PickDetail> getPickDetailList() {		
		return pickDetailDao.getPickDetailList();
	}

	@Override
	public Pager getPickDetailPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return pickDetailDao.getPickDetailPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		pickDetailDao.updateisdel(ids, oper);
		
	}

	/**刷卡提交保存主从表**/
	public String saveSubmit(List<PickDetail> pickDetailList,Pick pick) {
		String pk = pickService.save(pick);
		pick.setId(pk);
		for(PickDetail pickDetail: pickDetailList){
			pickDetail.setPick(pick);//建关系
			pickDetailDao.save(pickDetail);
		}
		return pk;
	}
	
	/**刷卡确认保存主从表**/
	public String saveApproval(List<PickDetail> pickDetailList, Pick pick) {
		String pk = pickService.save(pick);
		pick.setId(pk);
		for(PickDetail pickDetail: pickDetailList){
			pickDetail.setPick(pick);//建关系
			pickDetailDao.save(pickDetail);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		this.updateWorkingInoutCalculate(pickDetailList,map);//向投入产出表更新数据
		return pk;
	}

	@Override
	public List<PickDetail> getPickDetail(String id) {
		// TODO Auto-generated method stub
		return pickDetailDao.getPickDetail(id);
	}

	@Override
	public void updateAll(Pick pick, List<PickDetail> pickDetail,String cardNumber,String info) {
		Admin admin = this.adminService.getByCardnum(cardNumber);
		Pick pickNew = this.pickService.get(pick.getId());//获取主表对象
		pickNew.setModifyDate(new Date());
		pickNew.setModifyUser(admin);
		this.pickService.update(pickNew);
		

		for (int i = 0; i < pickDetail.size(); i++) {
			PickDetail pickDetailOld = pickDetail.get(i);
			if (pickDetailOld.getId() != null && !"".equals(pickDetailOld.getId())) {
				PickDetail pickDetailNow = this.pickDetailDao.get(pickDetailOld.getId());
				if (pickDetailOld.getPickAmount() != null && !"".equals(pickDetailOld.getPickAmount())) {
					pickDetailNow.setPickAmount(pickDetailOld.getPickAmount());
					pickDetailNow.setPickType(info);
					pickDetailNow.setCqmultiple(pickDetailOld.getCqmultiple());
					pickDetailNow.setCqPickAmount(pickDetailOld.getCqPickAmount());
					pickDetailNow.setCqhStockAmount(pickDetailOld.getCqhStockAmount());
					pickDetailNow.setModifyDate(new Date());
					this.update(pickDetailNow);
				} else {
					this.delete(pickDetailNow.getId());
				}
			} else {
				if (pickDetailOld.getPickAmount() != null && !"".equals(pickDetailOld.getPickAmount())) {
					pickDetailOld.setPickType(info);
					pickDetailOld.setPick(pickNew);
					this.save(pickDetailOld);
				}				
			}
		}
    }

	@Override
	//刷卡确认向投入产出表更新数据
	public void updateWorkingInoutCalculate(List<PickDetail> paramaterList,
			HashMap<String, Object> maps) {
		for (int i = 0; i < paramaterList.size(); i++) {
			PickDetail pickDetail = paramaterList.get(i);
			
			WorkingBill workingBill = pickDetail.getPick().getWorkingbill();//随工单对象
			String workingBillId = pickDetail.getPick().getWorkingbill().getId();// 随工单号			
			String materialCode = pickDetail.getMaterialCode();// 物料号
						
			List<Bom> bomList = bomService.findBom(workingBill.getAufnr(), workingBill.getProductDate(),materialCode, workingBill.getWorkingBillCode());
			Double d = 0.0;
			Double p = 0.0;
			for (int j = 0; j < bomList.size(); j++) {
				Bom bom = bomList.get(j);
				d += bom.getMaterialAmount();// Bom数量
				p = bom.getProductAmount();// 产品数量
			}
			Double unitChange = d/p ;//(兑换比例)一个产品需要几个子件
		
			Double workingBillPlanCount = workingBill.getPlanCount().doubleValue();// 找到随工单订单数量
			Double planCount = workingBillPlanCount * unitChange;//计算出随工单产品对应的子件数量
			Double pickAmount = Double.parseDouble(pickDetail.getPickAmount());// 领料数量
			Double multiple = this.Calculate(planCount, pickAmount);//计算倍数
			Double recipientsAmount = pickAmount;//领用数
			
			/**根据随工单号查询投入产出表中是否已经有数据**/			
			boolean flag = workingInoutService.isExist(workingBillId, materialCode);
			/**如果不存在就保存一条进去**/
			if(!flag){
				WorkingInout workingInout = new WorkingInout();				
				/**如果退料的情况**/
				if(pickDetail.getPickType().equals("262")){
					//workingInout.setMultiple(0-multiple);//投入产出减
					workingInout.setRecipientsAmount(0-recipientsAmount);//领用数减
				}
				/**如果是领料的情况**/
				else{				
					//workingInout.setMultiple(multiple);//投入产出加
					workingInout.setRecipientsAmount(recipientsAmount);//添加领用数
				}
				workingInout.setMaterialCode(materialCode);//保存物料号
				workingInout.setWorkingbill(workingBillService.get(workingBillId));//保存随工单
				workingInoutService.save(workingInout);//投入产出表保存
							
			}
			else{
				/**如果不存在就更新一条进去**/
				WorkingInout workingInout = workingInoutService.findWorkingInout(workingBillId, materialCode);
				/**如果退料的情况**/
				if (pickDetail.getPickType().equals("262")) {
					//workingInout.setMultiple(workingInout.getMultiple()- multiple);//投入产出减
					workingInout.setRecipientsAmount(workingInout.getRecipientsAmount()-recipientsAmount);//领用数减少
				}
				/**如果是领料的情况**/
				else{					
					//workingInout.setMultiple(workingInout.getMultiple() + multiple); //投入产出加
					workingInout.setRecipientsAmount(workingInout.getRecipientsAmount()+recipientsAmount);//领用数增加
				}
				workingInoutService.update(workingInout);
			}
		}
	}

	@Override
	//刷卡撤销向投入产出表更新数据
	public void updateWorkingInoutCalculateBack(List<PickDetail> paramaterList) {
		for (int i = 0; i < paramaterList.size(); i++) {
			PickDetail pickDetail = paramaterList.get(i);
			
			WorkingBill workingBill = pickDetail.getPick().getWorkingbill();//随工单对象
			String workingBillId = pickDetail.getPick().getWorkingbill().getId();// 随工单号			
			String materialCode = pickDetail.getMaterialCode();// 物料号
						
			List<Bom> bomList = bomService.findBom(workingBill.getAufnr(), workingBill.getProductDate(),materialCode, workingBill.getWorkingBillCode());
			Double d = 0.0;
			Double p = 0.0;
			for (int j = 0; j < bomList.size(); j++) {
				Bom bom = bomList.get(j);
				d += bom.getMaterialAmount();// Bom数量
				p = bom.getProductAmount();// 产品数量
			}
			Double unitChange = d/p ;//(兑换比例)一个产品需要几个子件
		
			Double workingBillPlanCount = workingBill.getPlanCount().doubleValue();// 找到随工单订单数量
			Double planCount = workingBillPlanCount * unitChange;//计算出随工单产品对应的子件数量
			Double pickAmount = Double.parseDouble(pickDetail.getPickAmount());// 领料数量
			Double multiple = this.Calculate(planCount, pickAmount);//计算倍数
			Double recipientsAmount = pickAmount;//领用数
			
		   WorkingInout workingInout = workingInoutService.findWorkingInout(workingBillId, materialCode);
		   /**如果退料的情况**/
		   if (pickDetail.getPickType().equals("262")) {
				//workingInout.setMultiple(workingInout.getMultiple() + multiple);//投入加
				workingInout.setRecipientsAmount(workingInout.getRecipientsAmount()+recipientsAmount);//领用数增加
			}
		   /**如果是领料的情况**/
		   else{					
				//workingInout.setMultiple(workingInout.getMultiple() - multiple); //投入减
				workingInout.setRecipientsAmount(workingInout.getRecipientsAmount()-recipientsAmount);//领用数减少
			}
				workingInoutService.update(workingInout);
		}
	}

	@Override
	public Double Calculate(Double planCount, Double pickAmount) {
		BigDecimal str = new BigDecimal(planCount / pickAmount);
		Double multiple = str.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();// 倍数=订单数量/领料数量 保留小数点后三位
		return multiple;
	}

	
}
