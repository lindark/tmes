package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


/**
 * 实体类 - 权限资源对象表
 */

@Entity
public class AccessResource extends BaseEntity {

	private static final long serialVersionUID = -8900934872438073557L;

	private Role role;//角色
	private Resource resource;//资源
	private Set<AccessObject> accessobjectSet;//对象
	private String rolename;//角色名称
	private String resourcename;//资源名称
	private String accessobjectname;//对象名称
	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<AccessObject> getAccessobjectSet() {
		return accessobjectSet;
	}
	public void setAccessobjectSet(Set<AccessObject> accessobjectSet) {
		this.accessobjectSet = accessobjectSet;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
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
	
	
	
}