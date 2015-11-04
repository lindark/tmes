package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 刷卡管理
 * @param args
 */


@Entity
@Searchable
@Table(name = "CardManagement")
public class CardManagement extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String posCode;//刷卡机编码
    private String pcIp;//电脑Ip
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    
    
	
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getPcIp() {
		return pcIp;
	}
	public void setPcIp(String pcIp) {
		this.pcIp = pcIp;
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
