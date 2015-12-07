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
	private double enterAmount;// 报工数量
	private String State;// 状态
	private String isDel;// 是否删除
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String step;
	private String orderid;
	private WorkingBill workingbill;// 随工单
	private String e_type;//S/E
	private String e_message;//反馈消息
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
		if (state == null) {
			state = "2";
		}
		State = state;
	}

	public Double getEnterAmount() {
		return enterAmount;
	}

	public void setEnterAmount(Double enterAmount) {
		this.enterAmount = enterAmount;
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	public String getE_message() {
		return e_message;
	}

	public void setE_message(String e_message) {
		this.e_message = e_message;
	}

}