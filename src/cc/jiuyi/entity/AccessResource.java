package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * 实体类 - 权限资源对象表
 */

@Entity
public class AccessResource extends BaseEntity {

	private static final long serialVersionUID = -8900934872438073557L;

	private Role role;//角色
	private Resources resources;//资源
	private String rolename;//角色名称
	private String resourcename;//资源名称
	private String accessobjectname;//对象名称
	private Set<AccessFunction> accessFunctionSet;//权限对象关系表

	@ManyToOne(fetch = FetchType.LAZY)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
	
	@Transient
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@Transient
	public String getResourcename() {
		return resourcename;
	}
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	@Transient
	public String getAccessobjectname() {
		return accessobjectname;
	}
	public void setAccessobjectname(String accessobjectname) {
		this.accessobjectname = accessobjectname;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="accessResource")
	public Set<AccessFunction> getAccessFunctionSet() {
		return accessFunctionSet;
	}
	public void setAccessFunctionSet(Set<AccessFunction> accessFunctionSet) {
		this.accessFunctionSet = accessFunctionSet;
	}
	
	
	
}