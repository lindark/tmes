package cc.jiuyi.webservice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.webservice.PieceworkWebService;

@Component
@WebService(serviceName="PieceworkWebService",endpointInterface="cc.jiuyi.webservice.PieceworkWebService")
public class PieceworkWebServiceImpl implements PieceworkWebService {
	 

	@Resource
	private WorkingBillService  workingBillService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private BomService bomService;
	@Resource
	private OddHandOverService oddHandOverService;
	
	
	public Dict getDict() {
		Dict d = new Dict();
		d.setCreateDate(new Date());
		d.setDictdesp("ceshi");
		System.out.println(">>>>>Service:"+d);
		
		return d;
	}
	/**
	 * 计件功能 第一个功能
	 * @param factory 工厂
	 * @param workShop 车间
	 * @param factoryUnit 单元
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @param wokingBillCode 随工单号
	 * @param materialCode 物料编码
	 * @param materialDesp 物料描述
	 * @param mujuntext 模具组号
	 * @param befWorkOddAmount上班零头    - oddHandOver : actualBomMount
	 * @param StorageAmount   入库数   - WorkingBill :   totalSingleAmount
	 * @param aftWorkOddAmount  上班零头     - oddHandOver : actualBomMount
	 * @param unRepairAmount 表面异常维修数   - WorkingBill :   totalRepairAmount
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
	public List<List<String>> getPieceworkListOne(String factory,String workShop,String factoryUnit,String productDate,String shift){
		factory = factory==null?"":factory;
		workShop = workShop==null?"":workShop;
		factoryUnit = factoryUnit==null?"":factoryUnit;
		productDate =productDate==null?"":productDate;
		shift = shift==null?"":shift;
		List<List<String>> PieceworkLists = new ArrayList<List<String>>();
		List<WorkingBill> WorkingBillList = workingBillService.findListWorkingBill(productDate, shift);
		if(WorkingBillList!=null){
			for(WorkingBill wb : WorkingBillList){
				if(factory.equals(wb.getWerks()) && workShop.equals(wb.getTeam().getFactoryUnit().getWorkShop())){
					if(!"".equals(factoryUnit) && factoryUnit.equals(workShop.equals(wb.getTeam().getFactoryUnit()))){
						List<String> PieceworkList = new ArrayList<String>();
						PieceworkList.add(factory);//工厂
						PieceworkList.add(workShop);//车间
						PieceworkList.add(factoryUnit);//单元
						PieceworkList.add(productDate);//生产日期
						PieceworkList.add(shift);//班次
						PieceworkList.add(wb.getWorkingBillCode());//随工单号
						PieceworkList.add(wb.getMatnr());//物料编码
						PieceworkList.add(wb.getMaktx());//物料描述
						Orders orders = ordersService.get("aufnr", wb.getWorkingBillCode());
						PieceworkList.add(orders==null?"":orders.getMujuntext());//模具组号
						
						List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", wb.getWorkingBillCode());
						if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
							PieceworkList.add(oddHandOverListBefore.get(0).getActualBomMount().toString());//上班零头
						}else{
							PieceworkList.add("0");//上班零头
						}
						PieceworkList.add(wb.getTotalSingleAmount().toString());//入库数 
						List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", wb.getWorkingBillCode());
						if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
							PieceworkList.add(oddHandOverListAfter.get(0).getActualBomMount().toString());//下班零头
						}else{
							PieceworkList.add("0");//下班零头
						}
						PieceworkList.add(wb.getTotalRepairAmount().toString());//表面异常维修数
						Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
						if(dailyWorkSet!=null && dailyWorkSet.size()>0){
							BigDecimal totalAmount = new BigDecimal(0);
							for(DailyWork dailyWork : dailyWorkSet){
								totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
							}
							PieceworkList.add(totalAmount.toString());//报工数
						}else{
							PieceworkList.add("0");//报工数
						}
						PieceworkList.add("");//一次合格率  待确定
						PieceworkLists.add(PieceworkList);
					}else{
						List<String> PieceworkList = new ArrayList<String>();
						PieceworkList.add(factory);
						PieceworkList.add(workShop);
						PieceworkList.add(factoryUnit);
						PieceworkList.add(productDate);
						PieceworkList.add(shift);
						PieceworkList.add(wb.getWorkingBillCode());//随工单号
						PieceworkList.add(wb.getMatnr());//物料编码
						PieceworkList.add(wb.getMaktx());//物料描述
						Orders orders = ordersService.get("aufnr", wb.getWorkingBillCode());
						PieceworkList.add(orders==null?"":orders.getMujuntext());//模具组号
						
						List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", wb.getWorkingBillCode());
						if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
							PieceworkList.add(oddHandOverListBefore.get(0).getActualBomMount().toString());//上班零头
						}else{
							PieceworkList.add("0");//上班零头
						}
						PieceworkList.add(wb.getTotalSingleAmount().toString());//入库数 
						List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", wb.getWorkingBillCode());
						if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
							PieceworkList.add(oddHandOverListAfter.get(0).getActualBomMount().toString());//下班零头
						}else{
							PieceworkList.add("0");//下班零头
						}
						PieceworkList.add(wb.getTotalRepairAmount().toString());//表面异常维修数
						Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
						if(dailyWorkSet!=null && dailyWorkSet.size()>0){
							BigDecimal totalAmount = new BigDecimal(0);
							for(DailyWork dailyWork : dailyWorkSet){
								totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
							}
							PieceworkList.add(totalAmount.toString());//报工数
						}else{
							PieceworkList.add("0");//报工数
						}
						PieceworkList.add("");//一次合格率  待确定
						PieceworkLists.add(PieceworkList);
					}
				}
			}
		}
		
		return PieceworkLists;
	}
	
	public List<List<String>> getPieceworkListTwo(String factory,String workShop,String factoryUnit,String productDate,String shift){
		factory = factory==null?"":factory;
		workShop = workShop==null?"":workShop;
		factoryUnit = factoryUnit==null?"":factoryUnit;
		productDate =productDate==null?"":productDate;
		shift = shift==null?"":shift;
		List<List<String>> PieceworkLists = new ArrayList<List<String>>();
		
		return PieceworkLists;
	}
}
