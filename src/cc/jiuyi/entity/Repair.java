package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——返修
 *
 */

@Entity
@Searchable
public class Repair extends BaseEntity{

	private static final long serialVersionUID = 5610511428351806908L;
	
	private String repairPart;//返修部位
	private String repairAmount;//返修数量
	private String totalAmount;//累计数量
	private String processResponse;//责任工序
	private String duty;//责任批次
	private String confirmUser;//确认人
	private String isDel;//是否删除
	private String state;//状态
	private String stateRemark;//状态描述
	
	public String getRepairPart() {
		return repairPart;
	}
	public void setRepairPart(String repairPart) {
		this.repairPart = repairPart;
	}
	public String getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(String repairAmount) {
		this.repairAmount = repairAmount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		if(isDel == null){
			isDel = "N";
		}
		this.isDel = isDel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
}
