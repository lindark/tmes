package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——返修
 * 
 */

@Entity
@Searchable
public class Repair extends BaseEntity {

	private static final long serialVersionUID = -1226180317662591944L;
	private String repairPart;// 返修部位
	private Integer repairAmount;// 返修数量
	private String processResponse;// 责任工序
	private String duty;// 责任人
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String isDel;// 是否删除
	private String state;// 状态
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String responseRemark;//责任工序描述
	private String dutyName;//责任人名字

	private WorkingBill workingbill;// 随工单

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getRepairPart() {
		return repairPart;
	}

	public void setRepairPart(String repairPart) {
		this.repairPart = repairPart;
	}

	public Integer getRepairAmount() {
		return repairAmount;
	}

	public void setRepairAmount(Integer repairAmount) {
		this.repairAmount = repairAmount;
	}

	public String getProcessResponse() {
		return processResponse;
	}

	public void setProcessResponse(String processResponse) {
		this.processResponse = processResponse;
	}
	
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
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
		if (state == null) {
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

	@Transient
	public String getResponseRemark() {
		return responseRemark;
	}

	public void setResponseRemark(String responseRemark) {
		this.responseRemark = responseRemark;
	}

	@Transient
	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

}
