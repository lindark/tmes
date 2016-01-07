package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 实体类 - 资源
 */

@Entity
@Table(name="Resources")
public class Resources extends BaseEntity {

	private static final long serialVersionUID = 8931644891304446093L;

	
	
	private String name;// 资源名称
	private String value;// 资源标识
	private Boolean isSystem;// 是否为系统内置资源
	private String description;// 描述
	private Integer orderList;// 排序
	
	private Set<Role> roleSet;// 权限
	
	private Set<AccessObject> accessobjectSet;//权限对象
	private Set<AccessResource> accessResourceSet;//权限资源对象

	
	@OneToMany(mappedBy = "resources", fetch = FetchType.LAZY)
	public Set<AccessResource> getAccessResourceSet() {
		return accessResourceSet;
	}

	public void setAccessResourceSet(Set<AccessResource> accessResourceSet) {
		this.accessResourceSet = accessResourceSet;
	}

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "resources")
	public Set<AccessObject> getAccessobjectSet() {
		return accessobjectSet;
	}

	

	public void setAccessobjectSet(Set<AccessObject> accessobjectSet) {
		this.accessobjectSet = accessobjectSet;
	}

	@Column(nullable = false, unique = true)
	@OrderBy(value="name asc")
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

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@ManyToMany(mappedBy = "resourcesSet", fetch = FetchType.EAGER)
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	

}