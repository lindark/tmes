package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——返修收获
 *
 */
@Entity
@Searchable
public class Repairin extends BaseEntity{

	private static final long serialVersionUID = 727896572443412459L;

	private Integer receiveAmount;//收获数量
	private Integer totalAmount;//累计数量
	private String state;//状态
	private String isDel;//是否删除
	private String confirmUser;//确认人
	public Integer getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Integer receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Integer getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
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
		if(isDel == null){
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
	
}
