package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 产品Bom
 * @param args
 */


@Entity
@Searchable
@Table(name = "Material")
public class Material extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String materialType;//组件类型
    private String materialCode;//组件编码
    private String materialName;//组件名称
    private String sourceLocation;//来源库存地点
    private String storeLocation;//去向库存地点
    private String materialUnit;//组件单位
    private Integer materialAmount;//组件数量
    private String batch;//批次
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    
    
	
	
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
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
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getStoreLocation() {
		return storeLocation;
	}
	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	public Integer getMaterialAmount() {
		return materialAmount;
	}
	public void setMaterialAmount(Integer materialAmount) {
		this.materialAmount = materialAmount;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
        if(isDel==null){
        	isDel="N";
        }
		this.isDel = isDel;
	}
	
	 @Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

   

    
	
}
