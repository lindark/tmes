package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.LocatHandOverHeader;
import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.LocatHandOverHeaderService;
import cc.jiuyi.util.ThinkWayUtil;
/**
 * 线边仓交接 主表
 * 
 */
@ParentPackage("admin")
public class LocatHandOverHeaderAction extends BaseAdminAction {

	private static final long serialVersionUID = -6680285054015936531L;
	private String loginid;
	private String lgpla;
	private String infoId;
	private String infoName;
	private String number;
	private String cardnumber;
	private List<HashMap<String, String>> locasideListMap;
	private List<LocatHandOver> locatHandOverList;
	private LocatHandOverHeader locatHandOverHeader;
	
	
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
				if(locasideListMap!=null && locasideListMap.size()>0){
					BigDecimal bg = new BigDecimal(0);
					List<HashMap<String, String>>  locasideListMaps = new ArrayList<HashMap<String, String>>();
					if(locasideListMap!=null && locasideListMap.size()>0){
						for (HashMap<String, String> los : locasideListMap) {
							if(los.get("verme")!=null && !"".equals(los.get("verme"))){
								if(bg.compareTo(new BigDecimal(los.get("verme")))!=0){
									locasideListMaps.add(los);
								}
							}
						}
						locasideListMap = locasideListMaps;
					}
				}
				Collections.sort(locasideListMap, new Comparator<Map<String, String>>() {
					 
		            public int compare(Map<String, String> o1, Map<String, String> o2) {
		 
		                int map1value = Integer.parseInt(o1.get("matnr"));
		                int map2value =  Integer.parseInt(o2.get("matnr"));
		                return map1value - map2value;
		            }
		        });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return INPUT;
	}
	//刷卡提交
	public String creditsubmit(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			Admin admin1 = adminService.get(loginid);
			if(locatHandOverList!=null && locatHandOverList.size()>0){
				locatHandOverHeaderService.saveLocatHandOver(admin,locatHandOverList,admin1);
			}
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败!");
		}
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
	
}
