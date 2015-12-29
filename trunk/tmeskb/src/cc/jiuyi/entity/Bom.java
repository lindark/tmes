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
	
    
	/**冗余**/
	private Double xtestAmount;
	private String xfailReason;
	private Double xfailAmount;
	private String xgoodsSzie1;
	private String xgoodsSzie2;
	private String xgoodsSzie3;
	private String xgoodsSzie4;
	private String xgoodsSzie5;
	private String xitid;
	private String xrecordid;
	private String xrecordNum;
	private String pickAmount;
	private String pickType;
	private String xsbids;
	private String xsbnums;
	private String xsmreson;
	private Double xmenge;
	private String xsmduty;
	private String xsmid;
	private String pickDetailid;
	private String productsCode;
	private String productsName;
	private String stockAmount;//库存数量
	/**冗余 end**/
	
	
	

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Transient
	public String getXfailReason() {
		return xfailReason;
	}

	public void setXfailReason(String xfailReason) {
		this.xfailReason = xfailReason;
	}
	@Transient
	public Double getXfailAmount() {
		return xfailAmount;
	}

	public void setXfailAmount(Double xfailAmount) {
		this.xfailAmount = xfailAmount;
	}
	@Transient
	public String getXgoodsSzie1() {
		return xgoodsSzie1;
	}

	public void setXgoodsSzie1(String xgoodsSzie1) {
		this.xgoodsSzie1 = xgoodsSzie1;
	}
	@Transient
	public String getXgoodsSzie2() {
		return xgoodsSzie2;
	}

	public void setXgoodsSzie2(String xgoodsSzie2) {
		this.xgoodsSzie2 = xgoodsSzie2;
	}
	@Transient
	public String getXgoodsSzie3() {
		return xgoodsSzie3;
	}

	public void setXgoodsSzie3(String xgoodsSzie3) {
		this.xgoodsSzie3 = xgoodsSzie3;
	}
	@Transient
	public String getXgoodsSzie4() {
		return xgoodsSzie4;
	}

	public void setXgoodsSzie4(String xgoodsSzie4) {
		this.xgoodsSzie4 = xgoodsSzie4;
	}
	@Transient
	public String getXgoodsSzie5() {
		return xgoodsSzie5;
	}

	public void setXgoodsSzie5(String xgoodsSzie5) {
		this.xgoodsSzie5 = xgoodsSzie5;
	}
	@Transient
	public String getXitid() {
		return xitid;
	}

	public void setXitid(String xitid) {
		this.xitid = xitid;
	}
	@Transient
	public String getXrecordid() {
		return xrecordid;
	}

	public void setXrecordid(String xrecordid) {
		this.xrecordid = xrecordid;
	}
	@Transient
	public String getXrecordNum() {
		return xrecordNum;
	}

	public void setXrecordNum(String xrecordNum) {
		this.xrecordNum = xrecordNum;
	}



	@Transient
	public String getPickAmount() {
		return pickAmount;
	}

	public void setPickAmount(String pickAmount) {
		this.pickAmount = pickAmount;
	}


	@Transient
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
	}



	@Transient
	public Double getXtestAmount() {
		return xtestAmount;
	}
	public void setXtestAmount(Double xtestAmount) {
		this.xtestAmount = xtestAmount;
	}


	@Transient
	public String getXsbids() {
		return xsbids;
	}
	public void setXsbids(String xsbids) {
		this.xsbids = xsbids;
	}
	@Transient
	public String getXsbnums() {
		return xsbnums;
	}
	public void setXsbnums(String xsbnums) {
		this.xsbnums = xsbnums;
	}
	@Transient
	public String getXsmreson() {
		return xsmreson;
	}
	public void setXsmreson(String xsmreson) {
		this.xsmreson = xsmreson;
	}
	@Transient
	public Double getXmenge() {
		return xmenge;
	}
	public void setXmenge(Double xmenge) {
		this.xmenge = xmenge;
	}
	@Transient
	public String getXsmduty() {
		return xsmduty;
	}
	public void setXsmduty(String xsmduty) {
		this.xsmduty = xsmduty;
	}
	@Transient
	public String getXsmid() {
		return xsmid;
	}

	public void setXsmid(String xsmid) {
		this.xsmid = xsmid;
	}
	
	@Transient
	public String getPickDetailid() {
		return pickDetailid;
	}

	public void setPickDetailid(String pickDetailid) {
		this.pickDetailid = pickDetailid;
	}

	@Transient
	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	@Transient
	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	@Transient
	public String getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
	
	
	
}
