package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 单元分配模具
 */
@Entity
public class UnitdistributeModel extends BaseEntity{

	private static final long serialVersionUID = 7413213927607352000L;
	
	private String isDel;//是否删除
	
	private String state;//状态
	
    private String stateRemark;//状态描述
    
    private String unitCode;//单元编码 
     
    private String unitName;//单元名称 
    
  /*  private String materialCode;//物料编码
    
    private String materialName;//物料名称
*/  
    
    private FactoryUnit factoryunit;
    
    private String station;//模具组号
    
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

	@Transient
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	@Transient
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/*public String getMaterialCode() {
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
	}*/
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "unitdistributeModelSet")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public FactoryUnit getFactoryunit() {
		return factoryunit;
	}

	public void setFactoryunit(FactoryUnit factoryunit) {
		this.factoryunit = factoryunit;
	}
    
    
    
}
