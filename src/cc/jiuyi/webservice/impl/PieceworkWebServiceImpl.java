package cc.jiuyi.webservice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * @param aftWorkOddAmount  下班零头     - oddHandOver : actualBomMount
	 * @param unRepairAmount 表面异常维修数   - WorkingBill :   totalRepairAmount
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
	public List<Map<String,Object>> getPieceworkListOne(String factory,String workShop,String factoryUnit,String productDate,String shift){
		factory = factory==null?"":factory;
		workShop = workShop==null?"":workShop;
		factoryUnit = factoryUnit==null?"":factoryUnit;
		productDate =productDate==null?"":productDate;
		shift = shift==null?"":shift;
		 List<Map<String,Object>> PieceworkLists = new ArrayList<Map<String,Object>>();
		List<WorkingBill> WorkingBillList = workingBillService.findListWorkingBill(productDate, shift);
		if(WorkingBillList!=null){
			for(WorkingBill wb : WorkingBillList){
				if(factory.equals(wb.getWerks()) && workShop.equals(wb.getTeam().getFactoryUnit().getWorkShop())){
					if(!"".equals(factoryUnit) && factoryUnit.equals(workShop.equals(wb.getTeam().getFactoryUnit()))){
						Map<String,Object> PieceworkMap = new HashMap<String,Object>();
						PieceworkMap.put("factory", factory);//工厂
						PieceworkMap.put("workShop",workShop);//车间
						PieceworkMap.put("factoryUnit",factoryUnit);//单元
						PieceworkMap.put("productDate",productDate);//生产日期
						PieceworkMap.put("shift",shift);//班次
						PieceworkMap.put("wokingBillCode",wb.getWorkingBillCode());//随工单号
						PieceworkMap.put("materialCode",wb.getMatnr());//物料编码
						PieceworkMap.put("materialDesp",wb.getMaktx());//物料描述
						Orders orders = ordersService.get("aufnr", wb.getWorkingBillCode());
						PieceworkMap.put("mujuntext",orders==null?"":orders.getMujuntext());//模具组号
						
						List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", wb.getWorkingBillCode());
						if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
							PieceworkMap.put("befWorkOddAmount",oddHandOverListBefore.get(0).getActualBomMount().toString());//上班零头
						}else{
							PieceworkMap.put("befWorkOddAmount","0");//上班零头
						}
						PieceworkMap.put("StorageAmount",wb.getTotalSingleAmount().toString());//入库数 
						List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", wb.getWorkingBillCode());
						if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
							PieceworkMap.put("aftWorkOddAmount",oddHandOverListAfter.get(0).getActualBomMount().toString());//下班零头
						}else{
							PieceworkMap.put("aftWorkOddAmount","0");//下班零头
						}
						PieceworkMap.put("unRepairAmount",wb.getTotalRepairAmount().toString());//表面异常维修数
						Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
						if(dailyWorkSet!=null && dailyWorkSet.size()>0){
							BigDecimal totalAmount = new BigDecimal(0);
							for(DailyWork dailyWork : dailyWorkSet){
								totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
							}
							PieceworkMap.put("qualifiedAmount",totalAmount.toString());//报工数
						}else{
							PieceworkMap.put("qualifiedAmount","0");//报工数
						}
						PieceworkMap.put("qualifiedRatio","");//一次合格率  待确定
						PieceworkLists.add(PieceworkMap);
					}else{
						Map<String,Object> PieceworkMap = new HashMap<String,Object>();
						PieceworkMap.put("factory", factory);//工厂
						PieceworkMap.put("workShop",workShop);//车间
						PieceworkMap.put("factoryUnit",factoryUnit);//单元
						PieceworkMap.put("productDate",productDate);//生产日期
						PieceworkMap.put("shift",shift);//班次
						PieceworkMap.put("wokingBillCode",wb.getWorkingBillCode());//随工单号
						PieceworkMap.put("materialCode",wb.getMatnr());//物料编码
						PieceworkMap.put("materialDesp",wb.getMaktx());//物料描述
						Orders orders = ordersService.get("aufnr", wb.getWorkingBillCode());
						PieceworkMap.put("mujuntext",orders==null?"":orders.getMujuntext());//模具组号
						
						List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", wb.getWorkingBillCode());
						if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
							PieceworkMap.put("befWorkOddAmount",oddHandOverListBefore.get(0).getActualBomMount().toString());//上班零头
						}else{
							PieceworkMap.put("befWorkOddAmount","0");//上班零头
						}
						PieceworkMap.put("StorageAmount",wb.getTotalSingleAmount().toString());//入库数 
						List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", wb.getWorkingBillCode());
						if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
							PieceworkMap.put("aftWorkOddAmount",oddHandOverListAfter.get(0).getActualBomMount().toString());//下班零头
						}else{
							PieceworkMap.put("aftWorkOddAmount","0");//下班零头
						}
						PieceworkMap.put("unRepairAmount",wb.getTotalRepairAmount().toString());//表面异常维修数
						Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
						if(dailyWorkSet!=null && dailyWorkSet.size()>0){
							BigDecimal totalAmount = new BigDecimal(0);
							for(DailyWork dailyWork : dailyWorkSet){
								totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
							}
							PieceworkMap.put("qualifiedAmount",totalAmount.toString());//报工数
						}else{
							PieceworkMap.put("qualifiedAmount","0");//报工数
						}
						PieceworkMap.put("qualifiedRatio","");//一次合格率  待确定
						PieceworkLists.add(PieceworkMap);
					}
				}
			}
		}
		
		return PieceworkLists;
	}
	/**
	 * 计件功能 第二个功能
	 * @param factory 工厂
	 * @param workShop 车间
	 * @param factoryUnit 单元
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @param classSys 班制
	 * @param team 班组
	 * @param tempId 工号
	 * @param name 姓名
	 * @param cardId 卡号
	 * @param post 岗位  
	 * @param station   工位   
	 * @param mujuntext 模具组号
	 * @param workingRange  工作范围   = 物料编码+物料描述
	 * @param phone 手机号  
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
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
