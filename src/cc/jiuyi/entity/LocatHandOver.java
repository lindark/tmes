package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 实体类——线边仓交接 从表
 * 
 */
@Entity
public class LocatHandOver extends BaseEntity{

	private static final long serialVersionUID = 517174281419433393L;
	
	private String locationCode;// 库存地点
	private String locationName;// 库存地点描述
	private String materialCode;//物料编码
	private String materialName;//物料描述
	private String amount;//数量
	private String boxamount;//箱数量
	private String charg;//批次
	private String isDel;//是否删除
	private String lgpla;//仓位
	
	private LocatHandOverHeader locatHandOverHeader;//线边仓交接 主表
	
	
	/**
	 * 假字段
	 * @return
	 */
	private String state;
	private String stateRemark;
	
	
	
	
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getBoxamount() {
		return boxamount;
	}
	public void setBoxamount(String boxamount) {
		this.boxamount = boxamount;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public LocatHandOverHeader getLocatHandOverHeader() {
		return locatHandOverHeader;
	}
	public void setLocatHandOverHeader(LocatHandOverHeader locatHandOverHeader) {
		this.locatHandOverHeader = locatHandOverHeader;
	}
	
	
	@Transient	
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

	
}
