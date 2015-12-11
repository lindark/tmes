package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 单元
 * @param args
 */


@Entity
@Searchable
//@Table(name = "FactoryUnit")
public class FactoryUnit extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String factoryUnitCode;//单元编码
    private String factoryUnitName;//单元描述
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述    
    private WorkShop workShop;//车间
    private String workShopName;
    private String factoryName;
    private String warehouse;//线边仓
    private String warehouseName;//线边仓描述
    private Set<Team> team;//班组
    
    private Set<Products> productsSet;//相关产品
    
    @ManyToMany(fetch=FetchType.LAZY)
	public Set<Products> getProductsSet() {
		return productsSet;
	}
	public void setProductsSet(Set<Products> productsSet) {
		this.productsSet = productsSet;
	}
	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}
	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}
	public String getFactoryUnitName() {
		return factoryUnitName;
	}
	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkShop getWorkShop() {
		return workShop;
	}
	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}
	@Transient
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	@Transient
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="factoryUnit")
	public Set<Team> getTeam()
	{
		return team;
	}
	public void setTeam(Set<Team> team)
	{
		this.team = team;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	
}
