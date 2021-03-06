package cc.jiuyi.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 实体类 - 角色
 */

@Entity
public class Role extends BaseEntity {

	private static final long serialVersionUID = -6614052029623997372L;

	private String name;// 角色名称
	private String value;// 角色标识
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述
	
	private Set<Admin> adminSet;// 管理员
	private Set<Resources> resourcesSet;// 资源
	private Set<AccessResource> accessResourceSet;//权限对象
	private Set<CreditAccess> creditAccessSet;//刷卡权限管理

	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="roleSet")
	public Set<CreditAccess> getCreditAccessSet() {
		return creditAccessSet;
	}

	public void setCreditAccessSet(Set<CreditAccess> creditAccessSet) {
		this.creditAccessSet = creditAccessSet;
	}

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	public Set<AccessResource> getAccessResourceSet() {
		return accessResourceSet;
	}

	public void setAccessResourceSet(Set<AccessResource> accessResourceSet) {
		this.accessResourceSet = accessResourceSet;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, unique = true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Resources> getResourcesSet() {
		return resourcesSet;
	}

	public void setResourcesSet(Set<Resources> resourcesSet) {
		this.resourcesSet = resourcesSet;
	}

}