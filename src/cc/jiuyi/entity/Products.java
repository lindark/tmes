package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 产品管理
 * @param args
 */


@Entity
public class Products extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4071870474412048180L;
	
	
	private String productsCode;//产品编码
    private String productsName;//产品名称
    private String productsAmount;//产品数量
    private String materialGroup;//物料组
    //private String materialDescript;//物料描述
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
	
	private Set<Material> material;//产    品
	//private Set<Quality> qualitySet;//质量问题单
//	private Set<Model> modelSet;//工模维修单
	private Set<Craft> craftSet;//工艺维修单
	private Set<FactoryUnit> factoryUnitSet;//所属单元

	@ManyToMany(fetch=FetchType.LAZY,mappedBy="productsSet")
	public Set<FactoryUnit> getFactoryUnitSet() {
		return factoryUnitSet;
	}
	public void setFactoryUnitSet(Set<FactoryUnit> factoryUnitSet) {
		this.factoryUnitSet = factoryUnitSet;
	}


	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Material> getMaterial() {
		return material;
	}

	public void setMaterial(Set<Material> material) {
		this.material = material;
	}

	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
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

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
	public Set<Quality> getQualitySet() {
		return qualitySet;
	}
	public void setQualitySet(Set<Quality> qualitySet) {
		this.qualitySet = qualitySet;
	}*/
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
	public Set<Craft> getCraftSet() {
		return craftSet;
	}
	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}

   

    
	
}
