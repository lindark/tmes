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
 * 实体类 - 产品Bom
 * @param args
 */


@Entity
@Searchable
@Table(name = "Material")
public class Material extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 994338331353572158L;
	
	private String productCode;//产品编码
	private String materialType;//组件类型
    private String materialCode;//组件编码
    private String materialName;//组件名称
    private String materialUnit;//组件单位
    private Integer materialAmount;//组件数量
    private Integer spread;//展开层
    private Integer project;//项目
    private String runOver;//溢出指示符
    private String projectType;//项目类别
    private String exception;//例外
    private String batch;//批次
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
  
    
	private Products products;// 产品Bom

	@ManyToOne(fetch = FetchType.LAZY)
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	
	private Set<WorkingBill> workingBillSet;//随工单
	
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "materialSet")
	public Set<WorkingBill> getWorkingBillSet() {
		return workingBillSet;
	}
	public void setWorkingBillSet(Set<WorkingBill> workingBillSet) {
		this.workingBillSet = workingBillSet;
	}
	
	
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
		
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getSpread() {
		return spread;
	}
	public void setSpread(Integer spread) {
		this.spread = spread;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public String getRunOver() {
		return runOver;
	}
	public void setRunOver(String runOver) {
		this.runOver = runOver;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

   

	
    
	
}
