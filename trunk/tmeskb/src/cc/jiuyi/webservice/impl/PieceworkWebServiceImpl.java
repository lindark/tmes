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

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
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
	 * @param mouldNumber 模具组号
	 * @param befWorkOddAmount上班零头    - oddHandOver : actualBomMount
	 * @param StorageAmount   入库数   - WorkingBill :   totalSingleAmount
	 * @param aftWorkOddAmount  下班零头     - oddHandOver : actualBomMount
	 * @param unRepairAmount 表面异常维修数   - WorkingBill :   totalRepairAmount
	 * @param qualifiedAmount 合格数-报工数 
	 * @param qualifiedRatio 一次合格率
	 * @return PieceworkList 返回需要的数据
	 */
	public String getPieceworkListOne(String xmlString){
		xmlString="<?xml version='1.0' encoding='UTF-8'?><ROOT><title name='计件系统第一个功能'>"
				+ "<factory>1000</factory>"
				+ "<workShop>1001</workShop>"
				+ "<factoryUnit></factoryUnit>"
				+ "<productDate>2016-01-20</productDate>"
				+ "<shift></shift></title></ROOT>";  
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
				if(wb.getTeam()!=null){
					if(wb.getTeam().getFactoryUnit()!=null){
						if(factory.equals(wb.getWerks()) && workShop.equals(wb.getTeam().getFactoryUnit().getWorkShop().getWorkShopCode())){
							if(!"".equals(factoryUnit) && factoryUnit.equals(workShop.equals(wb.getTeam().getFactoryUnit().getFactoryUnitCode()))){
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
								Orders orders = ordersService.get("aufnr", wb.getAufnr());
								PieceworkMap.put("mouldNumber",orders==null?"":orders.getMujuntext());//模具组号
								
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
	   	      factoryElmt.setText((String)map.get("factory"));
	   	      Element workShopElmt = pieceworklistElmt.addElement("workShop");
	   	      workShopElmt.setText((String)map.get("workShop"));
	   	      Element factoryUnitElmt = pieceworklistElmt.addElement("factoryUnit");
	   	      factoryUnitElmt.setText((String)map.get("factoryUnit"));
	   	      Element productDateElmt = pieceworklistElmt.addElement("productDate");
	   	      productDateElmt.setText((String)map.get("productDate"));
	   	      Element shiftElmt = pieceworklistElmt.addElement("shift");
	   	      shiftElmt.setText((String)map.get("shift"));
	   	      Element wokingBillCodeElmt = pieceworklistElmt.addElement("wokingBillCode");
	   	      wokingBillCodeElmt.setText((String)map.get("wokingBillCode"));
	   	      Element materialCodeElmt = pieceworklistElmt.addElement("materialCode");
	   	      materialCodeElmt.setText((String)map.get("materialCode"));
	   	      Element materialDespElmt = pieceworklistElmt.addElement("materialDesp");
	   	      materialDespElmt.setText((String)map.get("materialDesp"));
	   	      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
	   	      mouldNumberElmt.setText((String)map.get("mouldNumber"));
	   	      Element befWorkOddAmountElmt = pieceworklistElmt.addElement("befWorkOddAmount");
	   	      befWorkOddAmountElmt.setText((String)map.get("befWorkOddAmount"));
	   	      Element StorageAmountElmt = pieceworklistElmt.addElement("StorageAmount");
	   	      StorageAmountElmt.setText((String)map.get("StorageAmount"));
	   	      Element aftWorkOddAmountElmt = pieceworklistElmt.addElement("aftWorkOddAmount");
	   	      aftWorkOddAmountElmt.setText((String)map.get("aftWorkOddAmount"));
	   	      Element unRepairAmountElmt = pieceworklistElmt.addElement("unRepairAmount");
	   	      unRepairAmountElmt.setText((String)map.get("unRepairAmount"));
	   	      Element qualifiedAmountElmt = pieceworklistElmt.addElement("qualifiedAmount");
	   	      qualifiedAmountElmt.setText((String)map.get("qualifiedAmount"));
	   	      Element qualifiedRatioElmt = pieceworklistElmt.addElement("qualifiedRatio");
	   	      qualifiedRatioElmt.setText((String)map.get("qualifiedRatio"));
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
	   	      Element StorageAmountElmt = pieceworklistElmt.addElement("StorageAmount");
	   	      StorageAmountElmt.setText("");
	   	      Element aftWorkOddAmountElmt = pieceworklistElmt.addElement("aftWorkOddAmount");
	   	      aftWorkOddAmountElmt.setText("");
	   	      Element unRepairAmountElmt = pieceworklistElmt.addElement("unRepairAmount");
	   	      unRepairAmountElmt.setText("");
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
	 */
	public String getPieceworkListTwo(String xmlString){
		xmlString="<?xml version='1.0' encoding='UTF-8'?><ROOT><title name='计件系统第二个功能'>"
				+ "<productDate>Dy01</productDate>"
				+ "<shift>Dy01</shift></title></ROOT>";  
        String productDate="";
        String shift="";
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
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		List<Map<String,Object>> PieceworkLists = new ArrayList<Map<String,Object>>();
		List<Kaoqin> KaoqinList = kaoqinService.getKaoqinList(productDate, shift);
		if(KaoqinList!=null && KaoqinList.size()>0){
			for(Kaoqin kq : KaoqinList){
				Map<String,Object> PieceworkMap = new HashMap<String,Object>();
				PieceworkMap.put("productDate", productDate);//生产日期
				PieceworkMap.put("shift", shift);//生产日期
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
	  	      Element productDateElmt = pieceworklistElmt.addElement("productDate");
	  	      productDateElmt.setText((String)map.get("productDate"));
	  	      Element shiftElmt = pieceworklistElmt.addElement("shift");
	  	      shiftElmt.setText((String)map.get("shift"));
	  	      Element classSysElmt = pieceworklistElmt.addElement("classSys");
	  	      classSysElmt.setText((String)map.get("classSys"));
	  	      Element basicElmt = pieceworklistElmt.addElement("basic");
	  	      basicElmt.setText((String)map.get("basic"));
	  	      Element teamElmt = pieceworklistElmt.addElement("team");
	  	      teamElmt.setText((String)map.get("team"));
	  	      Element workNumberElmt = pieceworklistElmt.addElement("workNumber");
	  	      workNumberElmt.setText((String)map.get("workNumber"));
	  	      Element nameElmt = pieceworklistElmt.addElement("name");
	  	      nameElmt.setText((String)map.get("name"));
	  	      Element cardNumberElmt = pieceworklistElmt.addElement("cardNumber");
	  	      cardNumberElmt.setText((String)map.get("cardNumber"));
	  	      Element postElmt = pieceworklistElmt.addElement("post");
	  	      postElmt.setText((String)map.get("post"));
	  	      Element stationElmt = pieceworklistElmt.addElement("station");
	  	      stationElmt.setText((String)map.get("station"));
	  	      Element mouldNumberElmt = pieceworklistElmt.addElement("mouldNumber");
	  	      mouldNumberElmt.setText((String)map.get("mouldNumber"));
	  	      Element workingRangeElmt = pieceworklistElmt.addElement("workingRange");
	  	      workingRangeElmt.setText((String)map.get("workingRange"));
	  	      Element phoneElmt = pieceworklistElmt.addElement("phone");
	  	      phoneElmt.setText((String)map.get("phone"));
	  	      Element jiabanElmt = pieceworklistElmt.addElement("jiaban");
	  	      jiabanElmt.setText((String)map.get("jiaban"));
	  	      Element daibanElmt = pieceworklistElmt.addElement("daiban");
	  	      daibanElmt.setText((String)map.get("daiban"));
	  	      Element shijiaElmt = pieceworklistElmt.addElement("shijia");
	  	      shijiaElmt.setText((String)map.get("shijia"));
	  	      Element bingjiaElmt = pieceworklistElmt.addElement("bingjia");
	  	      bingjiaElmt.setText((String)map.get("bingjia"));
	  	      Element nianxiujiaElmt = pieceworklistElmt.addElement("nianxiujia");
	  	      nianxiujiaElmt.setText((String)map.get("nianxiujia"));
	  	      Element hunjiaElmt = pieceworklistElmt.addElement("hunjia");
	  	      hunjiaElmt.setText((String)map.get("hunjia"));
	  	      Element sangjiaElmt = pieceworklistElmt.addElement("sangjia");
	  	      sangjiaElmt.setText((String)map.get("sangjia"));
	  	      Element chidaoElmt = pieceworklistElmt.addElement("chidao");
	  	      chidaoElmt.setText((String)map.get("chidao"));
	  	      Element zaotuiElmt = pieceworklistElmt.addElement("zaotui");
	  	      zaotuiElmt.setText((String)map.get("zaotui"));
	  	      Element kuanggongElmt = pieceworklistElmt.addElement("kuanggong");
	  	      kuanggongElmt.setText((String)map.get("kuanggong"));
	  	    }
	    }else{
	    	Element pieceworklistElmt  = titleElmt.addElement("pieceworklist");
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
		      Element jiabanElmt = pieceworklistElmt.addElement("jiaban");
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
		      kuanggongElmt.setText("");
	    }
		return doc.asXML();
	}
}
