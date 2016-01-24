package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 单元分配产品
 */
@Entity
public class UnitdistributeProduct extends BaseEntity{

	private static final long serialVersionUID = 7413213917607356000L;
	
	private String isDel;//是否删除
	
	private String state;//状态
	
    private String stateRemark;//状态描述
    
    private String unitCode;//单元编码 
     
    private String unitName;//单元名称 
    
    private String materialCode;//物料编码
    
    private String materialName;//物料名称 
    
    private Set<Admin> adminSet;

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if(isDel==null){
			isDel="N";
		}
		this.isDel = isDel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitdistributeProduct")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

    
    
    
}
