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

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.KaoqinService;
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
	@Resource
	private KaoqinService kaoqinService;
	@Resource
	private AdminService adminService;
	
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
	 * @param MouldNumber 模具组号
	 * @param befWorkOddAmount上班零头    - oddHandOver : actualBomMount
	 * @param StorageAmount   入库数   - WorkingBill :   totalSingleAmount
	 * @param aftWorkOddAmount  下班零头     - oddHandOver : actualBomMount
	 * @param unRepairAmount 表面异常维修数   - WorkingBill :   totalRepairAmount
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
	public List<Map<String,Object>> getPieceworkListOne(String factory,String workShop,String factoryUnit,String productDate,String shift){
		/*String fStr="<?xml version='1.0' encoding='UTF-8'?>" +  
                "<ROOT test='test123' cod='cod123'><Name>AAA</Name><Number>BBB</Number>" +  
                "<Recording>http://10.15.57.174/wav/2008/10/29/WG37100/ext37102/10.15.57.71!1~R!10292008_064002!37102!67256479!Ext!NA!1179371583!R.wav</Recording>" +  
                "<Orders>有</Orders></ROOT>";  
        try {  
            SAXReader reader = new SAXReader();  
             Document doc;   
             doc = DocumentHelper.parseText(fStr);   
  
            //Document doc = reader.read(ffile); //读取一个xml的文件  
            Element root = doc.getRootElement();  
            Attribute testCmd= root.attribute("test");  
            System.out.println(testCmd.getName()+"-***--"+testCmd.getValue());                         
            Element eName = root.element("Name");  
            System.out.println("节点内容*--"+eName.getTextTrim());        
          
        } catch (Exception e) {  
            e.printStackTrace();  
        }  */
    	
		
		
		
		
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
						PieceworkMap.put("MouldNumber",orders==null?"":orders.getMujuntext());//模具组号
						
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
	 * @return nianxiujia 事假
	 * @return nianxiujia 年休假
	 * @return hunjia 婚假
	 * @return sangjia 丧假
	 * @return chidao 迟到
	 * @return zaotui 早退
	 * @return kuanggong 旷工
	 */
	public List<Map<String,Object>> getPieceworkListTwo(String factory,String workShop,String factoryUnit,String productDate,String shift){
		factory = factory==null?"":factory;
		workShop = workShop==null?"":workShop;
		factoryUnit = factoryUnit==null?"":factoryUnit;
		productDate =productDate==null?"":productDate;
		shift = shift==null?"":shift;
		List<Map<String,Object>> PieceworkLists = new ArrayList<Map<String,Object>>();
		List<Kaoqin> KaoqinList = kaoqinService.getKaoqinList(productDate, shift);
		if(KaoqinList!=null && KaoqinList.size()>0){
			for(Kaoqin kq : KaoqinList){
				Map<String,Object> PieceworkMap = new HashMap<String,Object>();
				PieceworkMap.put("productDate", productDate);//生产日期
				Team team  = kq.getTeam();
				if(team!=null){
					PieceworkMap.put("classSys", team.getClassSys());// 班制
					PieceworkMap.put("basic", team.getClassSys());// 基本
					PieceworkMap.put("team", team.getTeamName());// 班组
					
					
				}else{
					PieceworkMap.put("classSys", "");// 班制
					PieceworkMap.put("basic", "");// 基本
					PieceworkMap.put("team", "");// 班组
				}
				Admin admin = adminService.get(kq.getEmpid());
				if(admin!=null){
					PieceworkMap.put("workNumber", admin.getWorkNumber());// 工号
					PieceworkMap.put("name", admin.getName());// 姓名
					PieceworkMap.put("cardNumber", admin.getCardNumber());// 卡号
					Post post = admin.getPost();
					if(post!=null){
						PieceworkMap.put("post", post.getPostName());// 岗位
						PieceworkMap.put("station", post.getStation());// 工位
						
					}else{
						PieceworkMap.put("post", "");// 岗位
						PieceworkMap.put("station", "");// 工位
						
					}
					Set<UnitdistributeProduct> unitProductSet = admin.getUnitdistributeProductSet();//模具组号
					String mouldNumber = "";
					String workingRange = "";
					int i=0;
					if(unitProductSet!=null && unitProductSet.size()>0){
						for(UnitdistributeProduct unitProduct : unitProductSet){
							if("N".equals(unitProduct.getIsDel())){
								if(i==0){
									if(unitProduct.getUnitName()!=null && !"".equals(unitProduct.getUnitName())){
										mouldNumber = unitProduct.getUnitName();
									}
									workingRange = unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode() +"-"+unitProduct.getMaterialName()==null?"":unitProduct.getMaterialName();
								}else{
									if(unitProduct.getUnitName()!=null && !"".equals(unitProduct.getUnitName())){
										mouldNumber = mouldNumber+","+unitProduct.getUnitName();
									}else{
										mouldNumber = unitProduct.getUnitName();
									}
									workingRange = workingRange+","+unitProduct.getMaterialCode()==null?"":unitProduct.getMaterialCode() +"-"+unitProduct.getMaterialName()==null?"":unitProduct.getMaterialName();
								}
							}
						}
					}
					PieceworkMap.put("mouldNumber", mouldNumber);// 模具组号
					PieceworkMap.put("workingRange", workingRange);// 工作范围
					PieceworkMap.put("phone", admin.getPhoneNo());// 手机号码
				}else{
					PieceworkMap.put("workNumber", "");// 工号
					PieceworkMap.put("name", "");// 姓名
					PieceworkMap.put("cardNumber", "");// 卡号
					PieceworkMap.put("post", "");// 岗位
					PieceworkMap.put("station", "");// 工位
					PieceworkMap.put("mouldNumber", "");// 模具组号
					PieceworkMap.put("workingRange", "");// 工作范围
					PieceworkMap.put("phone", "");// 手机号码
				}
				String jiaban = "";//加班
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
					jiaban = kq.getTardyHours();break;
				case 4:
					daiban = kq.getTardyHours();break;
				case 5:
					shijia = kq.getTardyHours();break;
				case 6:
					bingjia = kq.getTardyHours();break;
				case 7:
					nianxiujia = kq.getTardyHours();break;
				case 8:
					hunjia = kq.getTardyHours();break;
				case 9:
					sangjia = kq.getTardyHours();break;
				case 10:
					chidao = kq.getTardyHours();break;
				case 11:
					zaotui = kq.getTardyHours();break;
				case 12:
					kuanggong = kq.getTardyHours();break;
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
				PieceworkMap.put("kuanggong", kuanggong);// 旷工
				PieceworkLists.add(PieceworkMap);
			}
		}
		return PieceworkLists;
	}
}
