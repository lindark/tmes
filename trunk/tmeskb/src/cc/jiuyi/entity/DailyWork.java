package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 报工
 */

@Entity
@Searchable
@Table(name = "DailyWork")
public class DailyWork extends BaseEntity {

	private static final long serialVersionUID = 8446572478950753825L;
	private Integer enterAmount;// 报工数量
	private String State;// 状态
	private String isDel;// 是否删除
	private String confirmUser;// 确认人
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字

	private WorkingBill workingbill;// 随工单
	private Admin admin;

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		if (state == null) {
			state = "2";
		}
		State = state;
	}

	/*
	 * public Integer getTotalAmout() { return totalAmout; }
	 * 
	 * public void setTotalAmout(Integer totalAmout) { this.totalAmout =
	 * totalAmout; }
	 */

	public Integer getEnterAmount() {
		return enterAmount;
	}

	public void setEnterAmount(Integer enterAmount) {
		this.enterAmount = enterAmount;
	}

	// public Date getEnterDate() {
	// return enterDate;
	// }
	// public void setEnterDate(Date enterDate) {
	// this.enterDate = enterDate;
	// }
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

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}