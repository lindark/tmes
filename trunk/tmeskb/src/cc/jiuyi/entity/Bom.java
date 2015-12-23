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
	private Material material;//组件
	private Double productAmount;//产品数量
	private String bomCode;//Bom编码
	private String bomName;//Bom名称
	private String bomUnit;//计量单位
	private Double bomAmount;//数量
	//private String isCarton;//纸箱状态描述
	private Integer version;//版本
    
	

	@ManyToOne(fetch=FetchType.LAZY)
	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Material getMaterial() {
		return material;
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

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getBomUnit() {
		return bomUnit;
	}

	public void setBomUnit(String bomUnit) {
		this.bomUnit = bomUnit;
	}

	public String getBomCode() {
		return bomCode;
	}

	public void setBomCode(String bomCode) {
		this.bomCode = bomCode;
	}

	public String getBomName() {
		return bomName;
	}

	public void setBomName(String bomName) {
		this.bomName = bomName;
	}

	public Double getBomAmount() {
		return bomAmount;
	}

	public void setBomAmount(Double bomAmount) {
		this.bomAmount = bomAmount;
	}

//	public String getIsCarton() {
//		return isCarton;
//	}
//
//	public void setIsCarton(String isCarton) {
//		this.isCarton = isCarton;
//	}
//	


	
	
	
}
