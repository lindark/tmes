package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 车间
 * @param args
 */


@Entity
@Searchable
//@Table(name = "WorkShop")
public class WorkShop extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String workShopCode;//车间编码
    private String workShopName;//车间名称
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    
    private Factory factory;//工厂
    private Set<FactoryUnit> factoryUnitSet;//单元
    
	@ManyToOne(fetch = FetchType.LAZY)
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public String getWorkShopCode() {
		return workShopCode;
	}
	public void setWorkShopCode(String workShopCode) {
		this.workShopCode = workShopCode;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
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
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="workshop")
	public Set<FactoryUnit> getFactoryUnitSet() {
		return factoryUnitSet;
	}
	public void setFactoryUnitSet(Set<FactoryUnit> factoryUnitSet) {
		this.factoryUnitSet = factoryUnitSet;
	}

   

    
	
}
