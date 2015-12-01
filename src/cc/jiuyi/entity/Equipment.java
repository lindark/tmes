package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 实体类 - 设备
 */
@Entity
public class Equipment extends BaseEntity{

	private static final long serialVersionUID = 4854091043163973181L;

	private String equipmentName;//名称
	
	private String equipmentNo;//编号
	
	private String version;//型号
	
    private String state;//状态
    private String isDel;//是否删除
    
    private String stateRemark;//状态描述
    private String versionReamrk;//型号描述
    
    
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	
	@Transient
	public String getVersionReamrk() {
		return versionReamrk;
	}
	public void setVersionReamrk(String versionReamrk) {
		this.versionReamrk = versionReamrk;
	}
    
    
	
	
}
