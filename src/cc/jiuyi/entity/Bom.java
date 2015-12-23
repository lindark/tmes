package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 产品Bom
 */
@Entity
public class Bom extends BaseEntity{

	private static final long serialVersionUID = -7300988835184296373L;

	private Products products;//产品
	private Double productAmount;//产品数量
	private String materialCode;//Bom编码
	private String materialName;//Bom名称
	private String materialUnit;//计量单位
	private Double materialAmount;//数量
	private String isCarton;//纸箱状态描述
	private Integer version;//版本
    
	

	@ManyToOne(fetch=FetchType.LAZY)
	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}
	
	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Double getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(Double materialAmount) {
		this.materialAmount = materialAmount;
	}

	public String getIsCarton() {
		return isCarton;
	}

	public void setIsCarton(String isCarton) {
		this.isCarton = isCarton;
	}
}
