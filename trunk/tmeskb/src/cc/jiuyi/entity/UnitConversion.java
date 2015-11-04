package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 单位转换
 * @param args
 */


@Entity
@Searchable
@Table(name = "UnitConversion")
public class UnitConversion extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String unitCode;//单位编码
    private String unitDescription;//单位描述
    private Integer conversationRatio;//兑换比例
    private String convertUnit;//转换单位
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    

    
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitDescription() {
		return unitDescription;
	}
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	
	public Integer getConversationRatio() {
		return conversationRatio;
	}
	public void setConversationRatio(Integer conversationRatio) {
		this.conversationRatio = conversationRatio;
	}
	public String getConvertUnit() {
		return convertUnit;
	}
	public void setConvertUnit(String convertUnit) {
		this.convertUnit = convertUnit;
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
