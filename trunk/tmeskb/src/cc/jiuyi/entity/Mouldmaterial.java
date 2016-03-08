package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 模具物料
 */
@Entity
public class Mouldmaterial extends BaseEntity{

	private static final long serialVersionUID = 4252091043163973182L;
	

	private String factory;//工厂
	private String point;//计量点
	private String code;//编码
	private String describe;//描述
	private String materialCode;//物料编码
	private String feature;//特征
    private String totalAmount;//总数量
	private String yearAmount;//年数量
	private String text;//文本
	
    private String state;//是否启用
    private String isDel;//是否删除
    
    private String stateRemark;//状态描述

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getYearAmount() {
		return yearAmount;
	}

	public void setYearAmount(String yearAmount) {
		this.yearAmount = yearAmount;
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
		this.isDel = isDel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
   
    
    
	
	
}
