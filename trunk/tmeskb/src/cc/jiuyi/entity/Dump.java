package cc.jiuyi.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——转储
 *
 */
@Entity
@Searchable
public class Dump extends BaseEntity{

	private static final long serialVersionUID = -2687433396607084358L;
	
	private String voucherId;//物料凭证号
	private Date deliveryDate;//发货日期
	private String state;//状态
	private String isDel;//是否删除
	private String confirmUser;//确认人
	private String stateRemark;//状态描述
	
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
}
