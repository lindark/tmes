package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工厂
 * @param args
 */


@Entity
@Searchable
@Table(name = "Factory")
public class Factory extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String factoryCode;//工厂编码
    private String factoryName;//工厂名称
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private String workshopName;//工厂名称
    
    private Set<WorkShop> workShopSet;//车间
    
    
    
	@OneToMany(fetch=FetchType.LAZY,mappedBy="factory")
	public Set<WorkShop> getWorkShopSet() {
		return workShopSet;
	}
	public void setWorkShopSet(Set<WorkShop> workShopSet) {
		this.workShopSet = workShopSet;
	}
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
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
	@Transient
	public String getWorkshopName() {
		return workshopName;
	}
	public void setWorkshopName(String workshopName) {
		this.workshopName = workshopName;
	}

   

    
	
}
