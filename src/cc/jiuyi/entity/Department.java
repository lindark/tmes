package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 部门
 */

@Entity
public class Department extends BaseEntity {

	private static final long serialVersionUID = -7743118661914508794L;
	
	private String deptName;//部门名称
	private String parentDept;//上级部门
	private String deptLeader;//部门负责人
	private String parentDeptLeader;//部门上级负责人
	private String State;//状态
	private String isDel;//是否删除
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getParentDept() {
		return parentDept;
	}
	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
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