package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import cc.jiuyi.util.ThinkWayUtil;

/**
 * 实体类 - 入库
 */

@Entity
public class EnteringwareHouse extends BaseEntity {

	private static final long serialVersionUID = -3066164332463929036L;
	private Integer totalSingleAmount;// 累计根量
	private Integer storageAmount;// 入库数量
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String State;// 状态
	private String isdel;// 是否删除
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字

	private WorkingBill workingbill;// 随工单

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		if(state == null){
			state = "2";
		}
		State = state;
	}

	public Integer getTotalSingleAmount() {
		return totalSingleAmount;
	}

	public void setTotalSingleAmount(Integer totalSingleAmount) {
		this.totalSingleAmount = totalSingleAmount;
	}

	public Integer getStorageAmount() {
		return storageAmount;
	}

	public void setStorageAmount(Integer storageAmount) {
		this.storageAmount = storageAmount;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		if (isdel == null)
			this.isdel = "N";
		else
			this.isdel = isdel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Transient
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}
	
}