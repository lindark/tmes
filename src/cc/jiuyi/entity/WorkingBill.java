package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	private Date productDate;//生产日期
	private String planCount;//计划数量
	private String isdel;//是否删除
	private String matnr;//物料号
	private String maktx;//物料描述
	
	private Set<EnteringwareHouse> enteringwareHouse;
	private Set<Material> materialSet;//组件
	
	
	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
	public Set<EnteringwareHouse> getEnteringwareHouse() {
		return enteringwareHouse;
	}
	public void setEnteringwareHouse(Set<EnteringwareHouse> enteringwareHouse) {
		this.enteringwareHouse = enteringwareHouse;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Set<Material> getMaterialSet() {
		return materialSet;
	}
	public void setMaterialSet(Set<Material> materialSet) {
		this.materialSet = materialSet;
	}
	
	
	public String getWorkingBillCode() {
		return workingBillCode;
	}
	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}
	
	public Date getProductDate() {
		return productDate;
	}
	public void setProductDate(Date productDate) {
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
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	

}