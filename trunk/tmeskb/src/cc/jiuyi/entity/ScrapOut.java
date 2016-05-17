package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 报废产出对照表
 * 
 * @param args
 */

@Entity
//@Searchable
@Table(name = "ScrapOut")
public class ScrapOut extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -222717352061032304L;
	
	
	private String materialCode;//物料编码
	private String materialName;//物料名称
	private String materialUnit;//物料单位
	private String productsCode;//产品编码
	private String productsName;//产品名称
	private String productsUnit;//产品单位
	private String state;// 状态
	private String isDel;// 是否删除
	
	
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
	public String getProductsUnit() {
		return productsUnit;
	}
	public void setProductsUnit(String productsUnit) {
		this.productsUnit = productsUnit;
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
		if(isDel ==null){
			isDel = "N";
		}
		this.isDel = isDel;
	}
	
    

}
