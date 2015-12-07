package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springmodules.cache.annotations.Cacheable;

/**
 * 实体类 - 刷卡权限管理
 */

@Entity
public class CreditAccess extends BaseEntity{
	private static final long serialVersionUID = -8636478867180341825L;
	
	private Set<Role> roleSet;//角色
	private String  creditName;//名称
	private String pathvalue;//请求地址
	
	@ManyToMany(fetch=FetchType.LAZY)
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public String getCreditName() {
		return creditName;
	}
	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}
	public String getPathvalue() {
		return pathvalue;
	}
	public void setPathvalue(String pathvalue) {
		this.pathvalue = pathvalue;
	}
	
	
}