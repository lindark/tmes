package cc.jiuyi.webservice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;
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
	@Resource
	private KaoqinService kaoqinService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private FactoryUnitService factoryUnitService; 
	@Resource
	private UnitdistributeModelService unitdistributeModelService; 
	
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
	 * @param mouldNumber 模具组号
	 * @param befWorkOddAmount上班零头    - oddHandOver : actualBomMount
	 * @param storageAmount   入库数   - WorkingBill :   totalSingleAmount
	 * @param aftWorkOddAmount  下班零头     - oddHandOver : actualBomMount
	 * @param unRepairAmount 表面异常维修数   - WorkingBill :   totalRepairAmount   
	 * @param unBefWorkOddAmount 上班异常零头
	 * @param unAftWorkOddAmount 下班异常零头
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
	public String getPieceworkListOne(String xmlString){
		/*xmlString="<?xml version='1.0' encoding='UTF-8'?><ROOT><title name='计件系统第一个功能'>"
				+ "<factory>1000</factory>"
				+ "<workShop>201</workShop>"
				+ "<factoryUnit></factoryUnit>"
				+ "<productDate>2016-04-20</productDate>"
				+ "<shift></shift></title></ROOT>";  */
        Document doc = null;
        String factory="";
        String workShop="";
        String factoryUnit="";
        String productDate="";
        String shift="";
        try {
            doc = DocumentHelper.parseText(xmlString); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iter = rootElt.elementIterator("title"); // 获取根节点下的子节点title
            // 遍历title节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                factory = recordEle.elementTextTrim("factory"); // 拿到title节点下的子节点factory值
                workShop = recordEle.elementTextTrim("workShop"); // 拿到title节点下的子节点workShop值
                factoryUnit = recordEle.elementTextTrim("factoryUnit"); // 拿到title节点下的子节点factoryUnit值
                productDate = recordEle.elementTextTrim("productDate"); // 拿到title节点下的子节点productDate值
                shift = recordEle.elementTextTrim("shift"); // 拿到title节点下的子节点shift值
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*		
		factory = factory==null?"":factory;
		workShop = workShop==null?"":workShop;
		factoryUnit = factoryUnit==null?"":factoryUnit;
		productDate =productDate==null?"":productDate;
		shift = shift==null?"":shift;*/
		 List<Map<String,Object>> PieceworkLists = new ArrayList<Map<String,Object>>();
		 List<WorkingBill> WorkingBillList = workingBillService.findListWorkingBill(productDate, shift);
		
		if(WorkingBillList!=null){
			for(WorkingBill wb : WorkingBillList){
				 FactoryUnit fun = factoryUnitService.get("workCenter", wb.getWorkcenter());
				if(fun!=null){
						if(factory.equals(wb.getWerks()) && workShop.equals(fun.getWorkShop().getWorkShopCode())){
							Set<DailyWork> dwSet = wb.getDailyWork();
							if(dwSet!=null && dwSet.size()>0){
								for(DailyWork dw : dwSet){
									/*dw.setXmoudle(ThinkWayUtil.getDictValueByDictKey(dictService,
											"moudleType", dw.getMoudle()));*/
									if(!"".equals(factoryUnit) && factoryUnit.equals(fun.getFactoryUnitCode())){
										Map<String,Object> PieceworkMap = new HashMap<String,Object>();
										PieceworkMap.put("factory", judgeNull(factory));//工厂
										PieceworkMap.put("workShop",judgeNull(workShop));//车间
										PieceworkMap.put("factoryUnit",judgeNull(fun.getFactoryUnitCode()));//单元
										PieceworkMap.put("productDate",judgeNull(productDate));//生产日期
										PieceworkMap.put("shift",judgeNull(wb.getWorkingBillCode().substring(wb.getWorkingBillCode().length()-2)));//班次
										PieceworkMap.put("wokingBillCode",judgeNull(wb.getWorkingBillCode()));//随工单号
										PieceworkMap.put("materialCode",judgeNull(wb.getMatnr()));//物料编码
										PieceworkMap.put("materialDesp",judgeNull(wb.getMaktx()));//物料描述
										//Orders orders = ordersService.get("aufnr", judgeNull(wb.getWorkingBillCode()));
										//PieceworkMap.put("mujuntext",orders==null?"":judgeNull(orders.getMujuntext()));//模具组号
										PieceworkMap.put("mouldNumber",dw==null?"":judgeNull(dw.getMoudle()));//模具组号
										//PieceworkMap.put("mouldNumber",judgeNull(wb.getMoudle()));//模具组号
										List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", judgeNull(wb.getWorkingBillCode()));
										if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
											PieceworkMap.put("befWorkOddAmount",judgeNull(oddHandOverListBefore.get(0).getActualBomMount()));//上班零头
											PieceworkMap.put("unBefWorkOddAmount",judgeNull(oddHandOverListBefore.get(0).getUnBomMount()));//上班异常零头数
										}else{
											PieceworkMap.put("befWorkOddAmount","0");//上班零头
											PieceworkMap.put("unBefWorkOddAmount","0");//上班异常零头数
										}
										PieceworkMap.put("storageAmount",judgeNull(wb.getTotalSingleAmount()));//入库数 
										List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", judgeNull(wb.getWorkingBillCode()));
										if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
											PieceworkMap.put("aftWorkOddAmount",judgeNull(oddHandOverListAfter.get(0).getActualBomMount()));//下班零头
											PieceworkMap.put("unAftWorkOddAmount",judgeNull(oddHandOverListAfter.get(0).getUnBomMount()));//下班异常零头数
										}else{
											PieceworkMap.put("aftWorkOddAmount","0");//下班零头
											PieceworkMap.put("unAftWorkOddAmount","0");//下班异常零头数
										}
										PieceworkMap.put("unRepairAmount",judgeNull(wb.getTotalRepairAmount()));//表面异常维修数
										/*Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
										if(dailyWorkSet!=null && dailyWorkSet.size()>0){
											BigDecimal totalAmount = new BigDecimal(0);
											for(DailyWork dailyWork : dailyWorkSet){
												totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
											}
											PieceworkMap.put("qualifiedAmount",judgeNull(totalAmount));//报工数
										}else{
											PieceworkMap.put("qualifiedAmount","0");//报工数
										}*/
										PieceworkMap.put("qualifiedAmount",dw==null?"":dw.getEnterAmount()==null?"0":judgeNull(dw.getEnterAmount()));//报工数
										Set<Sample> sampleSet = wb.getSample();
										if(sampleSet!=null && sampleSet.size()>0){
											BigDecimal costQulified = new BigDecimal(0);
											BigDecimal costSampleNum = new BigDecimal(0);
											for(Sample sp :sampleSet){
												costQulified = costQulified.add(new BigDecimal("".equals(judgeNull(sp.getQulified()))?"0":sp.getQulified()));
												costSampleNum = costSampleNum.add(new BigDecimal("".equals(judgeNull(sp.getSampleNum()))?"0":sp.getSampleNum()));
											}
											if(costSampleNum.compareTo(new BigDecimal(0))==0){
												PieceworkMap.put("qualifiedRatio","");
											}else{
												
												String costQulifiedRate = costQulified.divide(costSampleNum,2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).toString()+"%";
												PieceworkMap.put("qualifiedRatio",costQulifiedRate);
											}
										}else{
											PieceworkMap.put("qualifiedRatio","");//一次合格率  (抽检合格率 平均值)
										}
										PieceworkLists.add(PieceworkMap);
									}else{
										Map<String,Object> PieceworkMap = new HashMap<String,Object>();
										PieceworkMap.put("factory", judgeNull(factory));//工厂
										PieceworkMap.put("workShop",judgeNull(workShop));//车间
										PieceworkMap.put("factoryUnit",judgeNull(fun.getFactoryUnitCode()));//单元
										PieceworkMap.put("productDate",judgeNull(productDate));//生产日期
										PieceworkMap.put("shift",judgeNull(wb.getWorkingBillCode().substring(wb.getWorkingBillCode().length()-2)));//班次
										PieceworkMap.put("wokingBillCode",judgeNull(wb.getWorkingBillCode()));//随工单号
										PieceworkMap.put("materialCode",judgeNull(wb.getMatnr()));//物料编码
										PieceworkMap.put("materialDesp",judgeNull(wb.getMaktx()));//物料描述
										//Orders orders = ordersService.get("aufnr", judgeNull(wb.getAufnr()));
										//PieceworkMap.put("mouldNumber",orders==null?"":judgeNull(orders.getMujuntext()));//模具组号
										PieceworkMap.put("mouldNumber",dw==null?"":judgeNull(dw.getMoudle()));//模具组号
										//PieceworkMap.put("mouldNumber",judgeNull(wb.getMoudle()));//模具组号
										List<OddHandOver> oddHandOverListBefore = oddHandOverService.getList("afterWorkingCode", judgeNull(wb.getWorkingBillCode()));
										if(oddHandOverListBefore!=null && oddHandOverListBefore.size()>0){
											PieceworkMap.put("befWorkOddAmount",judgeNull(oddHandOverListBefore.get(0).getActualBomMount()));//上班零头
											PieceworkMap.put("unBefWorkOddAmount",judgeNull(oddHandOverListBefore.get(0).getUnBomMount()));//上班异常零头数
										}else{
											PieceworkMap.put("befWorkOddAmount","0");//上班零头
											PieceworkMap.put("unBefWorkOddAmount","0");//上班异常零头数
										}
										PieceworkMap.put("storageAmount",judgeNull(wb.getTotalSingleAmount()));//入库数 
										List<OddHandOver> oddHandOverListAfter = oddHandOverService.getList("beforeWokingCode", judgeNull(wb.getWorkingBillCode()));
										if(oddHandOverListAfter!=null && oddHandOverListAfter.size()>0){
											PieceworkMap.put("aftWorkOddAmount",judgeNull(oddHandOverListAfter.get(0).getActualBomMount()));//下班零头
											PieceworkMap.put("unAftWorkOddAmount",judgeNull(oddHandOverListAfter.get(0).getUnBomMount()));//下班异常零头数
										}else{
											PieceworkMap.put("aftWorkOddAmount","0");//下班零头
											PieceworkMap.put("unAftWorkOddAmount","0");//下班异常零头数
										}
										PieceworkMap.put("unRepairAmount",judgeNull(wb.getTotalRepairAmount().toString()));//表面异常维修数
										/*Set<DailyWork> dailyWorkSet =  wb.getDailyWork();
										if(dailyWorkSet!=null && dailyWorkSet.size()>0){
											BigDecimal totalAmount = new BigDecimal(0);
											for(DailyWork dailyWork : dailyWorkSet){
												totalAmount = totalAmount.add(new BigDecimal(dailyWork.getEnterAmount())).setScale(2, RoundingMode.HALF_UP);
											}
											PieceworkMap.put("qualifiedAmount",judgeNull(totalAmount));//报工数
										}else{
											PieceworkMap.put("qualifiedAmount","0");//报工数
										}*/
										PieceworkMap.put("qualifiedAmount",dw==null?"":dw.getEnterAmount()==null?"0":judgeNull(dw.getEnterAmount()));//报工数
										
										Set<Sample> sampleSet = wb.getSample();
										if(sampleSet!=null && sampleSet.size()>0){
											BigDecimal costQulified = new BigDecimal(0);
											BigDecimal costSampleNum = new BigDecimal(0);
											for(Sample sp :sampleSet){
												costQulified = costQulified.add(new BigDecimal("".equals(judgeNull(sp.getQulified()))?"0":sp.getQulified()));
												costSampleNum = costSampleNum.add(new BigDecimal("".equals(judgeNull(sp.getSampleNum()))?"0":sp.getSampleNum()));
											}
											if(costSampleNum.compareTo(new BigDecimal(0))==0){
												PieceworkMap.put("qualifiedRatio","");
											}else{
												String costQulifiedRate =costQulified.divide(costSampleNum,2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).toString()+"%";
												PieceworkMap.put("qualifiedRatio",costQulifiedRate);
											}
										}else{
											PieceworkMap.put("qualifiedRatio","");//一次合格率  (抽检合格率 平均值)
										}
										PieceworkLists.add(PieceworkMap);
									}
								}
							}
						}
				}
			}
		}
		 //1. 构造空的Document
	    doc = DocumentHelper.createDocument();
	    //2. 构造根元素
	    Element rootElmt = doc.addElement("ROOT");
	    Element titleElmt  = rootElmt.addElement("title"); 
	    //title元素增加属性
	    titleElmt.addAttribute("name", "计件系统第一个功能");
	    //3. 递归构造子元素
	    
	    if(PieceworkLists.size()>0){
	    	 for(Map<String,Object> map : PieceworkLists){
	    		 Element pieceworklistElmt  = titleElmt.addElement("pieceworklist"); 
	   	      //pieceworklist元素设置数据
	   	      Element factoryElmt = pieceworklistElmt.addElement("factory");
	   	      factoryElmt.setText(judgeNull(map.get("factory")));
	   	      Element workShopElmt = pieceworklistElmt.addElement("workShop");
	   	      workShopElmt.setText(judgeNull(map.get("workShop")));
	   	      Element factoryUnitElmt = pieceworklistElmt.addElement("factoryUnit");
	   	      factoryUnitElmt.setText(judgeNull(map.get("factoryUnit")));
	   	      Element productDateElmt = pieceworklistElmt.addElement("productDate");
	   	      productDateElmt.setText(judgeNull(map.get("productDate")));
	   	      Element shiftElmt = pieceworklistElmt.addElement("shift");
	   	      shiftElmt.setText(judgeNull(map.get("shift")));
	   	      Element wokingBillCodeElmt = pieceworklistElmt.addElement("wokingBillCode");
	   	      wokingBillCodeElmt.setText(judgeNull(map.get("wokingBillCode")));
	   	      Element materialCodeElmt = pieceworklistElmt.addElement("materialCode");
	   	      materialCodeElmt.setText(judgeNull(map.get("materialCode")));
	   	      Element materialDespElmt = pieceworklistElmt.addElement("materialDesp");
	   	      materialDespElmt.setText(judgeNull(map.get("materialDesp")));
	   	      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
	   	      mouldNumberElmt.setText(judgeNull(map.get("mouldNumber")));
	   	      Element befWorkOddAmountElmt = pieceworklistElmt.addElement("befWorkOddAmount");
	   	      befWorkOddAmountElmt.setText(judgeNull(map.get("befWorkOddAmount")));
	   	      Element storageAmountElmt = pieceworklistElmt.addElement("storageAmount");
	   	      storageAmountElmt.setText(judgeNull(map.get("storageAmount")));
	   	      Element aftWorkOddAmountElmt = pieceworklistElmt.addElement("aftWorkOddAmount");
	   	      aftWorkOddAmountElmt.setText(judgeNull(map.get("aftWorkOddAmount")));
	   	      Element unRepairAmountElmt = pieceworklistElmt.addElement("unRepairAmount");
	   	      unRepairAmountElmt.setText(judgeNull(map.get("unRepairAmount")));
	   	      Element unBefWorkOddAmountElmt = pieceworklistElmt.addElement("unBefWorkOddAmount");
	   	      unBefWorkOddAmountElmt.setText(judgeNull(map.get("unBefWorkOddAmount")));
	   	      Element unAftWorkOddAmountElmt = pieceworklistElmt.addElement("unAftWorkOddAmount");
	   	      unAftWorkOddAmountElmt.setText(judgeNull(map.get("unAftWorkOddAmount")));
	   	      Element qualifiedAmountElmt = pieceworklistElmt.addElement("qualifiedAmount");
	   	      qualifiedAmountElmt.setText(judgeNull(map.get("qualifiedAmount")));
	   	      Element qualifiedRatioElmt = pieceworklistElmt.addElement("qualifiedRatio");
	   	      qualifiedRatioElmt.setText(judgeNull(map.get("qualifiedRatio")));
	   	    }
	    }else{
	    	Element pieceworklistElmt  = titleElmt.addElement("pieceworklist");
	   	      //pieceworklist元素设置数据
	   	      Element factoryElmt = pieceworklistElmt.addElement("factory");
	   	      factoryElmt.setText("");
	   	      Element workShopElmt = pieceworklistElmt.addElement("workShop");
	   	      workShopElmt.setText("");
	   	      Element factoryUnitElmt = pieceworklistElmt.addElement("factoryUnit");
	   	      factoryUnitElmt.setText("");
	   	      Element productDateElmt = pieceworklistElmt.addElement("productDate");
	   	      productDateElmt.setText("");
	   	      Element shiftElmt = pieceworklistElmt.addElement("shift");
	   	      shiftElmt.setText("");
	   	      Element wokingBillCodeElmt = pieceworklistElmt.addElement("wokingBillCode");
	   	      wokingBillCodeElmt.setText("");
	   	      Element materialCodeElmt = pieceworklistElmt.addElement("materialCode");
	   	      materialCodeElmt.setText("");
	   	      Element materialDespElmt = pieceworklistElmt.addElement("materialDesp");
	   	      materialDespElmt.setText("");
	   	      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
	   	      mouldNumberElmt.setText("");
	   	      Element befWorkOddAmountElmt = pieceworklistElmt.addElement("befWorkOddAmount");
	   	      befWorkOddAmountElmt.setText("");
	   	      Element storageAmountElmt = pieceworklistElmt.addElement("storageAmount");
	   	      storageAmountElmt.setText("");
	   	      Element aftWorkOddAmountElmt = pieceworklistElmt.addElement("aftWorkOddAmount");
	   	      aftWorkOddAmountElmt.setText("");
	   	      Element unRepairAmountElmt = pieceworklistElmt.addElement("unRepairAmount");
	   	      unRepairAmountElmt.setText("");
	   	      Element unBefWorkOddAmountElmt = pieceworklistElmt.addElement("unBefWorkOddAmount");
	   	      unBefWorkOddAmountElmt.setText("");
	   	      Element unAftWorkOddAmountElmt = pieceworklistElmt.addElement("unAftWorkOddAmount");
	   	      unAftWorkOddAmountElmt.setText("");
	   	      Element qualifiedAmountElmt = pieceworklistElmt.addElement("qualifiedAmount");
	   	      qualifiedAmountElmt.setText("");
	   	      Element qualifiedRatioElmt = pieceworklistElmt.addElement("qualifiedRatio");
	   	      qualifiedRatioElmt.setText("");
	    }
	   
		return doc.asXML();
	}
	/**
	 * 计件功能 第二个功能
	 * @param factory 工厂
	 * @param workShop 车间
	 * @param factoryUnit 单元
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @param classSys 班制
	 * @param basic基本
	 * @param team 班组
	 * @param workNumber 工号
	 * @param name 姓名
	 * @param cardNumber 卡号
	 * @param post 岗位  
	 * @param station   工位   
	 * @param mouldNumber 模具组号
	 * @param workingRange  工作范围   = 物料编码+物料描述
	 * @param phone 手机号  
	 * @param jiaban 加班 
	 * @param daiban 代班
	 * @return shijia 事假
	 * @return bingjia 病假
	 * @return nianxiujia 年休假
	 * @return hunjia 婚假
	 * @return sangjia 丧假
	 * @return chidao 迟到
	 * @return zaotui 早退
	 * @return kuanggong 旷工
	 * @return workState 上班状态
	 * @return workHour 上班时间
	 */
	public String getPieceworkListTwo(String xmlString){
		/*xmlString="<?xml version='1.0' encoding='UTF-8'?><ROOT><title name='计件系统第二个功能'>"
				+ "<factoryUnit></factoryUnit>"
				+ "<productDate>2016-05-04</productDate>"
				+ "<shift></shift></title></ROOT>";*/
        String productDate="";
        String shift="";
        String factoryUnit="";
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlString); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iter = rootElt.elementIterator("title"); // 获取根节点下的子节点title
            // 遍历title节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                productDate = recordEle.elementTextTrim("productDate"); // 拿到title节点下的子节点productDate值
                shift = recordEle.elementTextTrim("shift"); // 拿到title节点下的子节点shift值
                factoryUnit = recordEle.elementTextTrim("factoryUnit"); // 拿到title节点下的子节点shift值
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		List<Map<String,Object>> PieceworkLists = new ArrayList<Map<String,Object>>();
		if(!"".equals(factoryUnit)){
			List<Kaoqin> KaoqinList = kaoqinService.getKaoqinList(productDate, shift,factoryUnit);
			String mouldNumber = "";
			if(KaoqinList!=null && KaoqinList.size()>0){
				for(Kaoqin kq : KaoqinList){
					Map<String,Object> PieceworkMap = new HashMap<String,Object>();
					PieceworkMap.put("factoryUnit", kq.getFactoryUnitCode());//单元
					PieceworkMap.put("productDate", productDate);//生产日期
					PieceworkMap.put("shift", judgeNull(kq.getClasstime()));//班次
					String[] mud = judgeNull(kq.getModleNum()).split(",");
					String mudle = "";
					if(mud.length>0){
						for(int i=0; i<mud.length;i++){
							if(i==0){
								UnitdistributeModel unitdistributeModel = unitdistributeModelService.get(mud[i]);
								if(unitdistributeModel!=null){
									mudle = unitdistributeModel.getStation();
								}
							}else{
								UnitdistributeModel unitdistributeModel = unitdistributeModelService.get(mud[i]);
								if(unitdistributeModel!=null){
									if(!"".equals(mudle)){
										mudle = mudle+","+unitdistributeModel.getStation();
									}else{
										mudle = unitdistributeModel.getStation();
									}
								}
							}
						}
					}
					PieceworkMap.put("mouldNumber", mudle);// 模具组号
					PieceworkMap.put("workingRange", judgeNull(kq.getWorkName()));// 工作范围
					PieceworkMap.put("station", judgeNull(kq.getStationName()));// 工位
					Team team  = kq.getTeam();
					if(team!=null){
						PieceworkMap.put("classSys", judgeNull(team.getClassSys()));// 班制
						PieceworkMap.put("basic", judgeNull(team.getBasic()));// 基本
						PieceworkMap.put("team", judgeNull(team.getTeamCode()));// 班组
					}else{
						PieceworkMap.put("classSys", "");// 班制
						PieceworkMap.put("basic", "");// 基本
						PieceworkMap.put("team", "");// 班组
					}
					Admin admin = adminService.get(judgeNull(kq.getEmpid()));
					if(admin!=null){
						PieceworkMap.put("workNumber",  judgeNull(admin.getWorkNumber()));// 工号
						PieceworkMap.put("name",  judgeNull(admin.getName()));// 姓名
						PieceworkMap.put("cardNumber",  judgeNull(admin.getCardNumber()));// 卡号
						Post post = admin.getPost();
						if(post!=null){
							PieceworkMap.put("post", judgeNull(post.getPostName()));// 岗位
						}else{
							PieceworkMap.put("post", "");// 岗位
						}
						
						PieceworkMap.put("phone", judgeNull(admin.getPhoneNo()));// 手机号码
					}else{
						PieceworkMap.put("workNumber", "");// 工号
						PieceworkMap.put("name", "");// 姓名
						PieceworkMap.put("cardNumber", "");// 卡号
						PieceworkMap.put("post", "");// 岗位
						PieceworkMap.put("phone", "");// 手机号码
					}
					String workState = "";
					List<Dict> dictlist =  dictService.getList("dictname", "adminworkstate");
					if(dictlist!=null && dictlist.size()>0){
						for(Dict d : dictlist){
							if(d.getDictkey().equals(judgeNull(kq.getWorkState()))){
								workState = d.getDictvalue();
								PieceworkMap.put("workState", workState);
								break;
							}
						}
					}else{
						PieceworkMap.put("workState", workState);
					}
					PieceworkMap.put("workHour", judgeNull(kq.getTardyHours()));
					PieceworkLists.add(PieceworkMap);
				}
			}
		}else{
			List<Kaoqin> KaoqinList = kaoqinService.getKaoqinList(productDate, shift);
			String mouldNumber = "";
			if(KaoqinList!=null && KaoqinList.size()>0){
				for(Kaoqin kq : KaoqinList){
					Map<String,Object> PieceworkMap = new HashMap<String,Object>();
					PieceworkMap.put("factoryUnit", kq.getFactoryUnitCode());//单元
					PieceworkMap.put("productDate", productDate);//生产日期
					PieceworkMap.put("shift", judgeNull(kq.getClasstime()));//班次
					//PieceworkMap.put("mouldNumber", judgeNull(kq.getModleNum()));// 模具组号
					String[] mud = judgeNull(kq.getModleNum()).split(",");
					String mudle = "";
					if(mud.length>0){
						for(int i=0; i<mud.length;i++){
							if(i==0){
								UnitdistributeModel unitdistributeModel = unitdistributeModelService.get(mud[i]);
								if(unitdistributeModel!=null){
									mudle = unitdistributeModel.getStation();
								}
							}else{
								UnitdistributeModel unitdistributeModel = unitdistributeModelService.get(mud[i]);
								if(unitdistributeModel!=null){
									if(!"".equals(mudle)){
										mudle = mudle+","+unitdistributeModel.getStation();
									}else{
										mudle = unitdistributeModel.getStation();
									}
								}
							}
						}
					}
					PieceworkMap.put("mouldNumber", mudle);// 模具组号
					
					
					PieceworkMap.put("workingRange", judgeNull(kq.getWorkName()));// 工作范围
					PieceworkMap.put("station", judgeNull(kq.getStationName()));// 工位
					Team team  = kq.getTeam();
					if(team!=null){
						PieceworkMap.put("classSys", judgeNull(team.getClassSys()));// 班制
						PieceworkMap.put("basic", judgeNull(team.getBasic()));// 基本
						PieceworkMap.put("team", judgeNull(team.getTeamCode()));// 班组
						
						
						/*FactoryUnit fn = team.getFactoryUnit();
						
						if(fn!=null){
							Set<UnitdistributeModel> unitdistributemodelSet = fn.getUnitdistributemodelSet();
							if(unitdistributemodelSet!=null && unitdistributemodelSet.size()>0){
								int i=0;
								for(UnitdistributeModel um : unitdistributemodelSet){
									if("N".equals(um.getIsDel())){
										if(i==0){
											if(um.getStation()!=null && !"".equals(um.getStation())){
												mouldNumber = um.getStation();
												i++;
											}
										}else{
											if(um.getStation()!=null && !"".equals(um.getStation())){
												mouldNumber = mouldNumber+";"+um.getStation();
												i++;
											}else{
												if("".equals(mouldNumber)){
													mouldNumber = um.getStation();
													i++;
												}else{
													mouldNumber = mouldNumber+";"+um.getStation();
													i++;
												}
											}
										}
									}
								}
								PieceworkMap.put("mouldNumber", judgeNull(mouldNumber));// 模具组号
							}else{
								PieceworkMap.put("mouldNumber", judgeNull(mouldNumber));// 模具组号
							}
						}else{
							PieceworkMap.put("mouldNumber", judgeNull(mouldNumber));// 模具组号
						}*/
					}else{
						PieceworkMap.put("classSys", "");// 班制
						PieceworkMap.put("basic", "");// 基本
						PieceworkMap.put("team", "");// 班组
						//PieceworkMap.put("mouldNumber", judgeNull(mouldNumber));// 模具组号
					}
					Admin admin = adminService.get(judgeNull(kq.getEmpid()));
					if(admin!=null){
						PieceworkMap.put("workNumber",  judgeNull(admin.getWorkNumber()));// 工号
						PieceworkMap.put("name",  judgeNull(admin.getName()));// 姓名
						PieceworkMap.put("cardNumber",  judgeNull(admin.getCardNumber()));// 卡号
						Post post = admin.getPost();
						if(post!=null){
							PieceworkMap.put("post", judgeNull(post.getPostName()));// 岗位
							/*String station ="";
							Set<Station> stationset =post.getStationSet();
							if(stationset!=null && stationset.size()>0){
								for(Station sta : stationset){
									if("".equals(station)){
										station = sta.getName();
									}else{
										station = station +";"+sta.getName();
									}
								}
								PieceworkMap.put("station", station);// 工位
							}else{
								PieceworkMap.put("station", "");// 工位
							}*/
							
						}else{
							PieceworkMap.put("post", "");// 岗位
							//PieceworkMap.put("station", "");// 工位
							
						}
						/*Set<UnitdistributeProduct> unitProductSet = admin.getUnitdistributeProductSet();//模具组号
						
						String workingRange = "";
						int i=0;
						if(unitProductSet!=null && unitProductSet.size()>0){
							for(UnitdistributeProduct unitProduct : unitProductSet){
								if("N".equals(unitProduct.getIsDel())){
									if(i==0){
										workingRange = unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode();
									i++;
									}else{
										if(unitProduct.getMaterialCode()!=null && !"".equals(unitProduct.getMaterialCode())){
											if("".equals(workingRange)){
												workingRange = unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode();
												i++;
											}else{
												workingRange = workingRange+";"+unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode();
												i++;
											}
										}else{
											i++;
										}
										
									}
								}
							}
						}*/
						
						
						PieceworkMap.put("phone", judgeNull(admin.getPhoneNo()));// 手机号码
					}else{
						PieceworkMap.put("workNumber", "");// 工号
						PieceworkMap.put("name", "");// 姓名
						PieceworkMap.put("cardNumber", "");// 卡号
						PieceworkMap.put("post", "");// 岗位
						//PieceworkMap.put("station", "");// 工位
						//PieceworkMap.put("workingRange", "");// 工作范围
						PieceworkMap.put("phone", "");// 手机号码
					}
					String workState = "";
					List<Dict> dictlist =  dictService.getList("dictname", "adminworkstate");
					if(dictlist!=null && dictlist.size()>0){
						for(Dict d : dictlist){
							if(d.getDictkey().equals(judgeNull(kq.getWorkState()))){
								workState = d.getDictvalue();
								PieceworkMap.put("workState", workState);
								break;
							}
						}
					}else{
						PieceworkMap.put("workState", workState);
					}
					PieceworkMap.put("workHour", judgeNull(kq.getTardyHours()));
					/*String jiaban = "";//加班
					String daiban = "";//代班
					String shijia = "";//事假
					String bingjia = "";//病假
					String nianxiujia = "";//年休假
					String hunjia = "";//婚假
					String sangjia = "";//丧假
					String chidao = "";//迟到
					String zaotui = "";//早退
					String kuanggong = "";//旷工
					switch(Integer.parseInt(kq.getWorkState())){
					case 3:
						jiaban = judgeNull(kq.getTardyHours());break;
					case 4:
						daiban = judgeNull(kq.getTardyHours());break;
					case 5:
						shijia = judgeNull(kq.getTardyHours());break;
					case 6:
						bingjia = judgeNull(kq.getTardyHours());break;
					case 7:
						nianxiujia = judgeNull(kq.getTardyHours());break;
					case 8:
						hunjia = judgeNull(kq.getTardyHours());break;
					case 9:
						sangjia = judgeNull(kq.getTardyHours());break;
					case 10:
						chidao = judgeNull(kq.getTardyHours());break;
					case 11:
						zaotui = judgeNull(kq.getTardyHours());break;
					case 12:
						kuanggong = judgeNull(kq.getTardyHours());break;
						default:break;
					}
					PieceworkMap.put("jiaban", jiaban);// 加班
					PieceworkMap.put("daiban", daiban);//代班
					PieceworkMap.put("shijia", shijia);// 事假
					PieceworkMap.put("bingjia", bingjia);// 病假
					PieceworkMap.put("nianxiujia", nianxiujia);// 年休假
					PieceworkMap.put("hunjia", hunjia);// 婚假
					PieceworkMap.put("sangjia", sangjia);// 丧假
					PieceworkMap.put("chidao", chidao);// 迟到
					PieceworkMap.put("zaotui", zaotui);// 早退
					PieceworkMap.put("kuanggong", kuanggong);// 旷工*/	
					PieceworkLists.add(PieceworkMap);
				}
			}
		}
		
		 //1. 构造空的Document
	    doc = DocumentHelper.createDocument();
	    //2. 构造根元素
	    Element rootElmt = doc.addElement("ROOT");
	    Element titleElmt  = rootElmt.addElement("title"); 
	    //title元素增加属性
	    titleElmt.addAttribute("name", "计件系统第二个功能");
	    //3. 递归构造子元素
	    
	    if(PieceworkLists.size()>0){
	    	for(Map<String,Object> map : PieceworkLists){
	    		Element pieceworklistElmt  = titleElmt.addElement("pieceworklist");
	  	      //pieceworklist元素增加子元素
    		 Element factoryUnitDateElmt = pieceworklistElmt.addElement("factoryUnit");
    		 factoryUnitDateElmt.setText(judgeNull((String)map.get("factoryUnit")));
	  	      Element productDateElmt = pieceworklistElmt.addElement("productDate");
	  	      productDateElmt.setText(judgeNull((String)map.get("productDate")));
	  	      Element shiftElmt = pieceworklistElmt.addElement("shift");
	  	      shiftElmt.setText(judgeNull((String)map.get("shift")));
	  	      Element classSysElmt = pieceworklistElmt.addElement("classSys");
	  	      classSysElmt.setText(judgeNull((String)map.get("classSys")));
	  	      Element basicElmt = pieceworklistElmt.addElement("basic");
	  	      basicElmt.setText(judgeNull((String)map.get("basic")));
	  	      Element teamElmt = pieceworklistElmt.addElement("team");
	  	      teamElmt.setText(judgeNull((String)map.get("team")));
	  	      Element workNumberElmt = pieceworklistElmt.addElement("workNumber");
	  	      workNumberElmt.setText(judgeNull((String)map.get("workNumber")));
	  	      Element nameElmt = pieceworklistElmt.addElement("name");
	  	      nameElmt.setText(judgeNull((String)map.get("name")));
	  	      Element cardNumberElmt = pieceworklistElmt.addElement("cardNumber");
	  	      cardNumberElmt.setText(judgeNull((String)map.get("cardNumber")));
	  	      Element postElmt = pieceworklistElmt.addElement("post");
	  	      postElmt.setText(judgeNull((String)map.get("post")));
	  	      Element stationElmt = pieceworklistElmt.addElement("station");
	  	      stationElmt.setText(judgeNull((String)map.get("station")));
	  	      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
	  	      mouldNumberElmt.setText(judgeNull((String)map.get("mouldNumber")));
	  	      Element workingRangeElmt = pieceworklistElmt.addElement("workingRange");
	  	      workingRangeElmt.setText(judgeNull((String)map.get("workingRange")));
	  	      Element phoneElmt = pieceworklistElmt.addElement("phone");
	  	      phoneElmt.setText(judgeNull((String)map.get("phone")));
	  	      Element workStateElmt = pieceworklistElmt.addElement("workState");
	  	      workStateElmt.setText(judgeNull((String)map.get("workState")));
	  	      Element workHourElmt = pieceworklistElmt.addElement("workHour");
	  	      workHourElmt.setText(judgeNull((String)map.get("workHour")));
	  	      /*Element jiabanElmt = pieceworklistElmt.addElement("jiaban");
	  	      jiabanElmt.setText(judgeNull((String)map.get("jiaban")));
	  	      Element daibanElmt = pieceworklistElmt.addElement("daiban");
	  	      daibanElmt.setText(judgeNull((String)map.get("daiban")));
	  	      Element shijiaElmt = pieceworklistElmt.addElement("shijia");
	  	      shijiaElmt.setText(judgeNull((String)map.get("shijia")));
	  	      Element bingjiaElmt = pieceworklistElmt.addElement("bingjia");
	  	      bingjiaElmt.setText(judgeNull((String)map.get("bingjia")));
	  	      Element nianxiujiaElmt = pieceworklistElmt.addElement("nianxiujia");
	  	      nianxiujiaElmt.setText(judgeNull((String)map.get("nianxiujia")));
	  	      Element hunjiaElmt = pieceworklistElmt.addElement("hunjia");
	  	      hunjiaElmt.setText(judgeNull((String)map.get("hunjia")));
	  	      Element sangjiaElmt = pieceworklistElmt.addElement("sangjia");
	  	      sangjiaElmt.setText(judgeNull((String)map.get("sangjia")));
	  	      Element chidaoElmt = pieceworklistElmt.addElement("chidao");
	  	      chidaoElmt.setText(judgeNull((String)map.get("chidao")));
	  	      Element zaotuiElmt = pieceworklistElmt.addElement("zaotui");
	  	      zaotuiElmt.setText(judgeNull((String)map.get("zaotui")));
	  	      Element kuanggongElmt = pieceworklistElmt.addElement("kuanggong");
	  	      kuanggongElmt.setText(judgeNull((String)map.get("kuanggong")));*/
	  	    }
	    }else{
	    	Element pieceworklistElmt  = titleElmt.addElement("pieceworklist");
	    	Element factoryUnitDateElmt = pieceworklistElmt.addElement("factoryUnit");
	    	factoryUnitDateElmt.setText("");
	    	Element productDateElmt = pieceworklistElmt.addElement("productDate");
		      productDateElmt.setText("");
		      Element shiftElmt = pieceworklistElmt.addElement("shift");
		      shiftElmt.setText("");
		      Element classSysElmt = pieceworklistElmt.addElement("classSys");
		      classSysElmt.setText("");
		      Element basicElmt = pieceworklistElmt.addElement("basic");
		      basicElmt.setText("");
		      Element teamElmt = pieceworklistElmt.addElement("team");
		      teamElmt.setText("");
		      Element workNumberElmt = pieceworklistElmt.addElement("workNumber");
		      workNumberElmt.setText("");
		      Element nameElmt = pieceworklistElmt.addElement("name");
		      nameElmt.setText("");
		      Element cardNumberElmt = pieceworklistElmt.addElement("cardNumber");
		      cardNumberElmt.setText("");
		      Element postElmt = pieceworklistElmt.addElement("post");
		      postElmt.setText("");
		      Element stationElmt = pieceworklistElmt.addElement("station");
		      stationElmt.setText("");
		      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
		      mouldNumberElmt.setText("");
		      Element workingRangeElmt = pieceworklistElmt.addElement("workingRange");
		      workingRangeElmt.setText("");
		      Element phoneElmt = pieceworklistElmt.addElement("phone");
		      phoneElmt.setText("");
		      Element workStateElmt = pieceworklistElmt.addElement("workState");
	  	      workStateElmt.setText("");
	  	      Element workHourElmt = pieceworklistElmt.addElement("workHour");
	  	      workHourElmt.setText("");
		      /*Element jiabanElmt = pieceworklistElmt.addElement("jiaban");
		      jiabanElmt.setText("");
		      Element daibanElmt = pieceworklistElmt.addElement("daiban");
		      daibanElmt.setText("");
		      Element shijiaElmt = pieceworklistElmt.addElement("shijia");
		      shijiaElmt.setText("");
		      Element bingjiaElmt = pieceworklistElmt.addElement("bingjia");
		      bingjiaElmt.setText("");
		      Element nianxiujiaElmt = pieceworklistElmt.addElement("nianxiujia");
		      nianxiujiaElmt.setText("");
		      Element hunjiaElmt = pieceworklistElmt.addElement("hunjia");
		      hunjiaElmt.setText("");
		      Element sangjiaElmt = pieceworklistElmt.addElement("sangjia");
		      sangjiaElmt.setText("");
		      Element chidaoElmt = pieceworklistElmt.addElement("chidao");
		      chidaoElmt.setText("");
		      Element zaotuiElmt = pieceworklistElmt.addElement("zaotui");
		      zaotuiElmt.setText("");
		      Element kuanggongElmt = pieceworklistElmt.addElement("kuanggong");
		      kuanggongElmt.setText("");*/
	    }
		return doc.asXML();
	}

	
	private String judgeNull(Object obj){
		obj = obj==null?"":obj;
		return obj.toString();
	}
}
