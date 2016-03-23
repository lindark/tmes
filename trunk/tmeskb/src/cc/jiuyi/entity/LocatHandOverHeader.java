package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 实体类——线边仓交接 主表
 * 
 */
@Entity
public class LocatHandOverHeader extends BaseEntity {
	
	private static final long serialVersionUID = -5261188016594706437L;
	
	private String submitUser;//确认人
	private String locationCode;// 库存地点
	private String locationCodeDesp;// 库存地点描述
	private String factoryUnitCode;//单元编码
	private String factoryUnitDesp;//单元描述
	private String productDate;// 生产日期
	private String shift;// 班次
	private String lgpla;//仓位
	private String isDel;//是否删除
	private Set<LocatHandOver> locatHandOverSet;//线边仓交接  从表
	
	
	
	
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getLocationCodeDesp() {
		return locationCodeDesp;
	}
	public void setLocationCodeDesp(String locationCodeDesp) {
		this.locationCodeDesp = locationCodeDesp;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="locatHandOverHeader")
	public Set<LocatHandOver> getLocatHandOverSet() {
		return locatHandOverSet;
	}
	public void setLocatHandOverSet(Set<LocatHandOver> locatHandOverSet) {
		this.locatHandOverSet = locatHandOverSet;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
	public String getFactoryUnitDesp() {
		return factoryUnitDesp;
	}
	public void setFactoryUnitDesp(String factoryUnitDesp) {
		this.factoryUnitDesp = factoryUnitDesp;
	}
	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}
	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}

	
	
	
	
}
