package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——线边仓
 * 
 */
@Entity
@Searchable
public class Locationonside extends BaseEntity {

	private static final long serialVersionUID = 8226157605987504110L;

	private String locationCode;// 库存地点
	private String locationName;// 库存地点描述
	private String materialCode;//物料编码
	private String materialName;//物料描述
	private String amount;//数量
	private String isDel;//是否删除

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
	
	

}
