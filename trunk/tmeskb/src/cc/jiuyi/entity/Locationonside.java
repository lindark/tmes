package cc.jiuyi.entity;


import javax.persistence.Entity;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——线边仓交接
 * 
 */
@Entity
public class Locationonside extends BaseEntity implements Comparable<Locationonside>{

	private static final long serialVersionUID = 8226157605987504110L;

	private String locationCode;// 库存地点
	private String locationName;// 库存地点描述
	private String materialCode;//物料编码
	private String materialName;//物料描述
	private String amount;//库存数量
	private String charg;//批次
	private String isDel;//是否删除
	private String lgpla;//仓位
	private String meins;//单位
	
	/**
	 * 假字段
	 */
	private Double boxMount;//库存箱数
	private String xamount;//转移数量
	private String cqmultiple;//倍数
	
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

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if(isDel == null){
			isDel = "N";
		}
		this.isDel = isDel;
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

	@Transient
	public Double getBoxMount() {
		return boxMount;
	}

	public void setBoxMount(Double boxMount) {
		this.boxMount = boxMount;
	}

	public String getLgpla() {
		return lgpla;
	}

	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}

	@Override
	public int compareTo(Locationonside o){
		return this.materialCode.compareTo(o.getMaterialCode());
	}
	@Transient
	public String getXamount() {
		return xamount;
	}

	public void setXamount(String xamount) {
		this.xamount = xamount;
	}
	@Transient
	public String getCqmultiple() {
		return cqmultiple;
	}

	public void setCqmultiple(String cqmultiple) {
		this.cqmultiple = cqmultiple;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}
	
	

}
