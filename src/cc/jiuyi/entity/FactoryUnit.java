package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 单元
 * @param args
 */


@Entity
@Searchable
@Table(name = "FactoryUnit")
public class FactoryUnit extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String factoryUnitCode;//单元编码
    private String factoryUnitName;//单元描述
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    

    
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

   

    
	
}
