package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.LocatHandOverService;
import cc.jiuyi.service.UnitConversionService;


/**
 * 线边仓交接
 * 
 */
@ParentPackage("admin")
public class LocatHandOverAction extends BaseAdminAction {
	private static final long serialVersionUID = 2309979545694402762L;
	
	private String loginid;
	private String lgpla;
	private String infoId;
	private String infoName;
	private String number;
	private List<HashMap<String, String>> locasideListMap;
	private List<LocatHandOver> locatHandOverList;
	
	@Resource
	LocatHandOverService locatHandOverService;
	@Resource
	AdminService adminService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private UnitConversionService unitConversionService;
	
	
	
	
	public String list(){
		if (locasideListMap == null) {
			locasideListMap = new ArrayList<HashMap<String, String>>();
		}
		if(infoId!=null && lgpla!=null){
			Admin admin = adminService.get(loginid);
			String lgort = admin.getTeam().getFactoryUnit().getWarehouse();
			//String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
			FactoryUnit factoryUnit = factoryUnitService.get(infoId);
			String werks = factoryUnit.getWorkShop().getFactory().getFactoryCode();
			
			try {
				locasideListMap = rfc.findMaterial(werks, lgort, "", lgpla, "");
				if(locasideListMap.size()>0){
					number = number==null?"1":"1";
				}else{
					number = number==null?"0":"0";
				}
				for (HashMap<String, String> los : locasideListMap) {
					UnitConversion ucs = unitConversionService.get("matnr",
							los.get("matnr"));
					if (ucs != null) {
						String amount = los.get("verme");
						if (los.get("verme") == null || "".equals(los.get("verme"))) {
							amount = "0";
						}
						if (ucs.getConversationRatio() == null
								|| "".equals(ucs.getConversationRatio())) {
							ucs.setConversationRatio(0.0);
						}
						BigDecimal dcl = new BigDecimal(amount);
						BigDecimal dcu = new BigDecimal(ucs.getConversationRatio());
						try {
							BigDecimal dc = dcl.divide(dcu).setScale(2,
									RoundingMode.HALF_UP);
							los.put("bmt", dc.toString());
						} catch (Exception e) {
							e.printStackTrace();
							addActionError("物料" + los.get("matnr")
									+ " 计量单位数据异常");
							return ERROR;
						}
					} else {
						los.put("bmt", "0.00");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return LIST;
	}
	
	public String save(){
		try {
			for(LocatHandOver lho : locatHandOverList){
				lho.setIsDel("N");
				locatHandOverService.save(lho);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("保存失败！");
		}
		return ajaxJsonSuccessMessage("保存成功！");
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






	public List<HashMap<String, String>> getLocasideListMap() {
		return locasideListMap;
	}






	public void setLocasideListMap(List<HashMap<String, String>> locasideListMap) {
		this.locasideListMap = locasideListMap;
	}






	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public List<LocatHandOver> getLocatHandOverList() {
		return locatHandOverList;
	}

	public void setLocatHandOverList(List<LocatHandOver> locatHandOverList) {
		this.locatHandOverList = locatHandOverList;
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


	
}
