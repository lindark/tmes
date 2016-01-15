package cc.jiuyi.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDao;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.CustomerException;

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

	/**刷卡确认保存主从表**/
	public String save(List<PickDetail> pickDetailList,Pick pick) {
		String pk = pickService.save(pick);
		pick.setId(pk);
		for(PickDetail pickDetail: pickDetailList){
			pickDetail.setPick(pick);//建关系
			pickDetailDao.save(pickDetail);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		this.updateWorkingInoutCalculate(pickDetailList,map);
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
					pickDetailNow.setPickType(pickDetailOld.getPickType());
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
	public void updateWorkingInoutCalculate(List<PickDetail> paramaterList,
			HashMap<String, Object> maps) {
		for (int i = 0; i < paramaterList.size(); i++) {
			PickDetail pickDetail = paramaterList.get(i);
			
			Double planCount = pickDetail.getPick().getWorkingbill().getPlanCount().doubleValue();//找到随工单订单数量
			Double pickAmount = Double.parseDouble(pickDetail.getPickAmount());//领料数量
	        BigDecimal str = new BigDecimal(planCount / pickAmount);
	        Double multiple = str.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();//倍数=订单数量/领料数量 保留小数点后三位          
		    
	        String workingBillId = pickDetail.getPick().getWorkingbill().getId();//随工单号
			String materialCode = pickDetail.getMaterialCode();//物料号
			
			/**根据随工单号查询投入产出表中是否已经有数据**/			
			boolean flag = workingInoutService.isExist(workingBillId, materialCode);
			/**如果不存在就保存一条进去**/
			if(!flag){
				WorkingInout workingInout = new WorkingInout();				
				/**如果退料的情况**/
				if(pickDetail.getPickType().equals("262")){
					workingInout.setMultiple(0-multiple);//投入产出减
				}				
				workingInout.setMultiple(multiple);//投入产出加
				workingInoutService.save(workingInout);//投入产出表保存
							
				WorkingBill workingBill = workingBillService.get(workingBillId);
				workingBill.setWorkingInout(workingInout);
				workingBillService.update(workingBill);//随工单保存
			}
			else{
				/**如果不存在就更新一条进去**/
				WorkingInout workingInout = workingInoutService.findWorkingInout(workingBillId, materialCode);
				/**如果退料的情况**/
				if (pickDetail.getPickType().equals("262")) {
					workingInout.setMultiple(workingInout.getMultiple()- multiple);//投入产出减
				}
				workingInout.setMultiple(workingInout.getMultiple() + multiple); //投入产出加
				workingInoutService.update(workingInout);
			}
		}
	}

	
}
