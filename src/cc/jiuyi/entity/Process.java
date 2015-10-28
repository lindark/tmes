package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工序
 * @param args
 */


@Entity
@Searchable
@Table(name = "Process")
public class Process extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String ProcessCode;//工序编码
    private String ProcessName;//工序名称
    private String State;//状态
    private String isDel;//是否删除
    private String StateRemark;//状态描述

    @Transient
	public String getStateRemark() {
		return StateRemark;
	}
	public void setStateRemark(String stateRemark) {
		StateRemark = stateRemark;
	}
	public String getProcessCode() {
		return ProcessCode;
	}
	public void setProcessCode(String processCode) {
		ProcessCode = processCode;
	}
	public String getProcessName() {
		return ProcessName;
	}
	public void setProcessName(String processName) {
		ProcessName = processName;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
    
	
}
