package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——缺陷代码管理
 * 
 */
@Entity
@Searchable
public class Cause extends BaseEntity {

	private static final long serialVersionUID = -7677927911812294207L;

	private String causeCode;// 代码编码
	private String causeType;// 代码类型
	private String causeName;// 代码名称
	private String isDel;// 是否删除
	private String state;//状态
	private String stateRemark;//状态描述
	private String causeTypeRemark;//代码类型描述

	//假字段
	private String causeNum;//缺陷数量
	
	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	public String getCauseType() {
		return causeType;
	}

	public void setCauseType(String causeType) {
		this.causeType = causeType;
	}

	public String getCauseName() {
		return causeName;
	}

	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if(state == null){
			state = "2";
		}
		this.state = state;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getCauseTypeRemark() {
		return causeTypeRemark;
	}

	public void setCauseTypeRemark(String causeTypeRemark) {
		this.causeTypeRemark = causeTypeRemark;
	}

	@Transient
	public String getCauseNum()
	{
		return causeNum;
	}

	public void setCauseNum(String causeNum)
	{
		this.causeNum = causeNum;
	}
	
}
