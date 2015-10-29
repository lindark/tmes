package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * 实体类 - 随工单
 */

@Entity
public class WorkingBill extends BaseEntity {

	private static final long serialVersionUID = 2547319998033961001L;
	
	private String workingBillCode;//随工单编号
	private String productDate;//生产日期
	private String planCount;//计划数量
	private String isdel;//是否删除
	
	public String getWorkingBillCode() {
		return workingBillCode;
	}
	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getPlanCount() {
		return planCount;
	}
	public void setPlanCount(String planCount) {
		this.planCount = planCount;
	}
	//@Column(name = "isdel", columnDefinition = "enum default N")
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		if(isdel == null)
			this.isdel="N";
		else
			this.isdel = isdel;
	}

}