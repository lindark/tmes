package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.LocatHandOverHeader;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.LocatHandOverHeaderService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;
/**
 * 线边仓交接 主表
 * 
 */
@ParentPackage("admin")
public class LocatHandOverHeaderAction extends BaseAdminAction {

	private static final long serialVersionUID = -6680285054015936531L;
	private String loginid;
	private String lgpla;		//仓位
	private String infoId;
	private String infoName;
	private String number;
	private String cardnumber;
	private List<HashMap<String, String>> locasideListMap;
	private List<LocatHandOver> locatHandOverList;
	private LocatHandOverHeader locatHandOverHeader;
	
	private String locationCode;// 库存地点
	private String state;//状态
	private String end;		
	private String start;
	
	
	
	@Resource
	private LocatHandOverHeaderService locatHandOverHeaderService;
	@Resource
	AdminService adminService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private DictService dictService;
	
	
	public String list(){
		return LIST;
	}
	
	
	
	public String history() {
		return "history";
	}
	
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajaxList(){
		Admin admin = adminService.get(loginid);
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		pager = locatHandOverHeaderService.jqGrid(pager,admin);
		
		List<LocatHandOverHeader> lst = new ArrayList<LocatHandOverHeader>();
		List<LocatHandOverHeader> locatHandOverHeaderList = pager.getList();
		for (int i = 0; i < locatHandOverHeaderList.size(); i++) {
			LocatHandOverHeader locatHandOverHeader = (LocatHandOverHeader) locatHandOverHeaderList.get(i);
			locatHandOverHeader.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService,"locathohstate", locatHandOverHeader.getState()==null?"":locatHandOverHeader.getState()));
			locatHandOverHeader.setShift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", locatHandOverHeader.getShift()));
			lst.add(locatHandOverHeader);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(LocatHandOverHeader.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	
	
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("locationCode") != null) {
				locationCode = obj.getString("locationCode").toString();				
				map.put("locationCode", locationCode);				
			}
			if (obj.get("lgpla") != null) {
				lgpla = obj.getString("lgpla")
						.toString();				
				map.put("lgpla", lgpla);
				
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				start = obj.get("start").toString();
				end = obj.get("end").toString();			
				map.put("start", start);
				map.put("end", end);
				
			}
			if (obj.get("state") != null) {
				state = obj.getString("state").toString();
				map.put("state", state);
				
			}			
		}
		
		
		pager = locatHandOverHeaderService.historyjqGrid(pager, map);
		List<LocatHandOver> locatHandOverList = pager.getList();
		//System.out.println("结果："+pollingtestList.size());
		List<LocatHandOver> lst = new ArrayList<LocatHandOver>();
		for (int i = 0; i < locatHandOverList.size(); i++) {
			LocatHandOver locatHandOver = (LocatHandOver) locatHandOverList.get(i);
			// 状态描述
			locatHandOver.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "handOverState", locatHandOver.getLocatHandOverHeader().getState()));			
			locatHandOver.setState(locatHandOver.getLocatHandOverHeader().getState());
			lst.add(locatHandOver);
		}
		pager.setList(lst);		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig
				.setExcludes(ThinkWayUtil.getExcludeFields(LocatHandOver.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	
	
	
	
	// Excel导出 @author Reece 2016/3/15
		public String excelexport() {
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("lgpla", lgpla);
			map.put("locationCode", locationCode);
			map.put("state", state);			
			map.put("start", start);
			map.put("end", end);
			

			List<String> header = new ArrayList<String>();
			List<Object[]> body = new ArrayList<Object[]>();
			header.add("库存地点");
			header.add("仓位");
			header.add("物料编码");
			header.add("物料描述");
			//header.add("批次");
			header.add("库存数量");
			header.add("创建时间");
			header.add("状态");

			List<Object[]> workList = locatHandOverHeaderService.historyExcelExport(map);
			for (int i = 0; i < workList.size(); i++) {
				Object[] obj = workList.get(i);
				LocatHandOver locatHandOver = (LocatHandOver) obj[0];//Pollingtest
	        	LocatHandOverHeader locatHandOverHeader = (LocatHandOverHeader)obj[1];//workingBill	        	        	
	        	
				Object[] bodyval = {
						locatHandOver.getLocationCode() == null ? "" : locatHandOver.getLocationCode(),
						locatHandOver.getLgpla() == null ? "" : locatHandOver.getLgpla(),
						locatHandOver.getMaterialCode() == null ? "" : locatHandOver.getMaterialCode(),
						locatHandOver.getMaterialName() == null ? "" : locatHandOver.getMaterialName(),
						//locatHandOver.getCharg() == null ? "" : locatHandOver.getCharg(),					
						locatHandOver.getAmount() == null ? "" : locatHandOver.getAmount(),						
						locatHandOver.getCreateDate() == null ? "" : locatHandOver.getCreateDate(),						
						ThinkWayUtil.getDictValueByDictKey(
										dictService, "handOverState", locatHandOverHeader.getState()),						
						};
				body.add(bodyval);
			}

			try {
				String fileName = "仓位库存交接记录表" + ".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("仓位库存交接记录表", header, body, getResponse()
						.getOutputStream());
				getResponse().getOutputStream().flush();
				getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		if (locasideListMap == null) {
			locasideListMap = new ArrayList<HashMap<String, String>>();
		}
		if(lgpla!=null){
			Admin admin = adminService.get(loginid);
			FactoryUnit factoryUnit = admin.getTeam()==null?null:admin.getTeam().getFactoryUnit()==null?null:admin.getTeam().getFactoryUnit();
			if(factoryUnit==null){
				addActionError("未找到当前登录人库存单元");
				return ERROR;
			}
			//库存地点
			String lgort = factoryUnit.getWarehouse();
			if("".equals(lgort)){
				addActionError("未找到当前登录人库存地点");
				return ERROR;
			}
			//工厂
			String werks = factoryUnit.getWorkShop()==null?"":factoryUnit.getWorkShop().getFactory()==null?"":factoryUnit.getWorkShop().getFactory().getFactoryCode();
			//FactoryUnit factoryUnit = factoryUnitService.get(infoId);
			//String werks = factoryUnit.getWorkShop().getFactory().getFactoryCode();
			if("".equals(werks)){
				addActionError("未找到当前登录人工厂");
				return ERROR;
			}
			try {
				locasideListMap = rfc.findMaterial(werks, lgort, "", lgpla, "");
				if(locasideListMap.size()>0){
					number = number==null?"1":"1";
				}else{
					number = number==null?"0":"0";
				}
				if(locasideListMap.size()>0){
					List<HashMap<String, String>> locasideListMap1 = new ArrayList<HashMap<String, String>>();
					for(int i=0;i<locasideListMap.size();i++){
						BigDecimal bg = new BigDecimal(0);
						HashMap<String, String> map = locasideListMap.get(i);
						if(map.get("verme")!=null && !"".equals(map.get("verme"))){
							if(bg.compareTo(new BigDecimal(map.get("verme")))!=0){
								BigDecimal verme = new BigDecimal(map.get("verme"));
								if(i==0){
									locasideListMap1.add(map);
								}else{
									boolean flag = true;
									for(int j=0;j<locasideListMap1.size();j++){
										HashMap<String, String> map1 = locasideListMap1.get(j);
										if(map1.get("lgort").equals(map.get("lgort")) && map1.get("matnr").equals(map.get("matnr"))){
											BigDecimal verme1 = new BigDecimal(map1.get("verme")); 
											verme1 = verme1.add(verme).setScale(2, RoundingMode.HALF_UP);
											map1.remove("verme");
											map1.put("verme",verme1.toString());
											flag = false;
											break;
										}
									}
									if(flag)locasideListMap1.add(map);
								}
							}
						}
					}
					locasideListMap = locasideListMap1;
					Collections.sort(locasideListMap, new Comparator<Map<String, String>>() {
						 
			            public int compare(Map<String, String> o1, Map<String, String> o2) {
			 
			                int map1value = Integer.parseInt(o1.get("matnr"));
			                int map2value =  Integer.parseInt(o2.get("matnr"));
			                return map1value - map2value;
			            }
			        });
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return INPUT;
	}
	//刷卡提交
	public String creditsubmit(){
		//try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			Admin admin1 = adminService.get(loginid);
			if(locatHandOverList!=null && locatHandOverList.size()>0){
				//System.out.println(locatHandOverList.size());
				locatHandOverList=updateList(locatHandOverList);
				//System.out.println(locatHandOverList.size());
				locatHandOverHeaderService.saveLocatHandOver(admin,locatHandOverList,admin1);
			}
			return ajaxJsonSuccessMessage("保存成功!"); 
		//} catch (Exception e) {
		//	return ajaxJsonErrorMessage("保存失败!");
		//}
	}
	
	private List<LocatHandOver> updateList(List<LocatHandOver> list)
	{
		List<LocatHandOver> result=new ArrayList<LocatHandOver>();
		for(LocatHandOver lho:list)
		{
			if(lho!=null)
				result.add(lho);
		}
		return result;
	}
	
	/**
	 *  刷卡撤销
	 * @return
	 */
	public String creditundo() {
		String[] ids = id.split(",");
		Admin admin = adminService.getByCardnum(cardnumber);
		for(String id:ids){
			locatHandOverHeader = locatHandOverHeaderService.get(id);
		if(locatHandOverHeader.getState().equals("1")||locatHandOverHeader.getState().equals("2")){
	//		locatHandOverHeader.setIsDel("Y");
			locatHandOverHeader.setState("3");
			locatHandOverHeader.setRevokedUser(admin.getName());
			locatHandOverHeader.setRevokedUserId(admin.getId());
			locatHandOverHeader.setRevokedUserCard(cardnumber);
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			String time = dateFormat.format(date); 
			locatHandOverHeader.setRevokedTime(time);
			locatHandOverHeaderService.update(locatHandOverHeader);
			}else{
				return ajaxJsonSuccessMessage("已撤销的记录无法再撤销!");
			}
		}
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	//刷卡确认
	public String creditapproval(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			String[] ids = id.split(","); 
			locatHandOverHeaderService.updateLocatHandOver(admin,ids);
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败!");
		}
	}
	//查看
	public String view(){
		locatHandOverHeader = locatHandOverHeaderService.get(id);
		locatHandOverHeader.setShift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", locatHandOverHeader.getShift()));
		return VIEW;
	}
	
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<HashMap<String, String>> getLocasideListMap() {
		return locasideListMap;
	}
	public void setLocasideListMap(List<HashMap<String, String>> locasideListMap) {
		this.locasideListMap = locasideListMap;
	}
	public List<LocatHandOver> getLocatHandOverList() {
		return locatHandOverList;
	}
	public void setLocatHandOverList(List<LocatHandOver> locatHandOverList) {
		this.locatHandOverList = locatHandOverList;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public LocatHandOverHeader getLocatHandOverHeader() {
		return locatHandOverHeader;
	}
	public void setLocatHandOverHeader(LocatHandOverHeader locatHandOverHeader) {
		this.locatHandOverHeader = locatHandOverHeader;
	}



	public String getLocationCode() {
		return locationCode;
	}



	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getEnd() {
		return end;
	}



	public void setEnd(String end) {
		this.end = end;
	}



	public String getStart() {
		return start;
	}



	public void setStart(String start) {
		this.start = start;
	}
	
}
