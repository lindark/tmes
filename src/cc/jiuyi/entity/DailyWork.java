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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * 实体类 - 报工
 */

@Entity
@Searchable
@Table(name = "DailyWork")
public class DailyWork extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232434065942453328L;
	
	
	private Integer totalAmout;//累计报工根量
	private Integer enterAmout;//报工数量
   //private Date enterDate;//报工时间
	private String State;//状态
	private String isDel;//是否删除
    private String stateRemark;//状态描述
	
	private WorkingBill workingbill;//随工单
	
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
			State = state;
	}
	public Integer getTotalAmout() {
		return totalAmout;
	}
	public void setTotalAmout(Integer totalAmout) {
		this.totalAmout = totalAmout;
	}
	public Integer getEnterAmout() {
		return enterAmout;
	}
	public void setEnterAmout(Integer enterAmout) {
		this.enterAmout = enterAmout;
	}
//	public Date getEnterDate() {
//		return enterDate;
//	}
//	public void setEnterDate(Date enterDate) {
//		this.enterDate = enterDate;
//	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	
	
}