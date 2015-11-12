package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——纸箱
 * 
 */
@Entity
@Searchable
public class Carton extends BaseEntity {

	private static final long serialVersionUID = -8927337834611798560L;
	private String cartonCode;// 纸箱编码
	private String cartonDescribe;// 纸箱描述
	private String cartonAmount;// 纸箱数量
	private String totalAmount;// 纸箱总数
	private String state;// 状态
	private String isDel;// 是否删除
	private String confirmUser;// 确认人
	private String stateRemark;// 状态描述

	private WorkingBill workingbill;// 随工单

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getCartonCode() {
		return cartonCode;
	}

	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}

	public String getCartonDescribe() {
		return cartonDescribe;
	}

	public void setCartonDescribe(String cartonDescribe) {
		this.cartonDescribe = cartonDescribe;
	}

	public String getCartonAmount() {
		return cartonAmount;
	}

	public void setCartonAmount(String cartonAmount) {
		this.cartonAmount = cartonAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null) {
			state = "2";
		}
		this.state = state;
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

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
}
