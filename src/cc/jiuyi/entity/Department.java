package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 实体类 - 部门
 */

@Entity
public class Department extends BaseEntity {

	private static final long serialVersionUID = -7743118661914508794L;
	
	private String deptName;//部门名称
	
	private String deptLeader;//部门负责人
	private String parentDeptLeader;//部门上级负责人
	private String State;//状态
	private String isDel;//是否删除
	
	private Department parentDept;//上级部门
	private Set<Department> childDept;//下级部门
	private Set<Admin> admin;//员工
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	public Set<Admin> getAdmin() {
		return admin;
	}
	public void setAdmin(Set<Admin> admin) {
		this.admin = admin;
	}
	//	@JoinColumn(name="parentDept")
	@ManyToOne(fetch = FetchType.LAZY)
	public Department getParentDept() {
		return parentDept;
	}
	public void setParentDept(Department parentDept) {
		this.parentDept = parentDept;
	}
	
	//@OneToMany(targetEntity= Department.class,cascade = {CascadeType.ALL},mappedBy="parentDept")
	//@Fetch(FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER,mappedBy="parentDept")
	public Set<Department> getChildDept() {
		return childDept;
	}
	public void setChildDept(Set<Department> childDept) {
		this.childDept = childDept;
	}
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}
	public String getParentDeptLeader() {
		return parentDeptLeader;
	}
	public void setParentDeptLeader(String parentDeptLeader) {
		this.parentDeptLeader = parentDeptLeader;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		if(isDel == null)
			this.isDel = "N";
		else
			this.isDel = isDel;
	}
	
	
}