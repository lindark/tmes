package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * 实体类 - 管理员
 */

@Entity
public class Admin extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -7519486823153844426L;

	private String username;// 登录名
	private String password;// 密码
	private String email;// E-mail
	private String name;// 姓名
	private Boolean isAccountEnabled;// 账号是否启用
	private Boolean isAccountLocked;// 账号是否锁定
	private Boolean isAccountExpired;// 账号是否过期
	private Boolean isCredentialsExpired;// 凭证是否过期
	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期
	private Date loginDate;// 最后登录日期
	private String loginIp;// 最后登录IP

	private String departName;// 部门名称
	private String isDel;// 是否删除
	private String shift;// 班次
	private String productDate;// 生产日期
	private String phoneNo;// 手机号
	private Set<Abnormal> abnormalList;// 一个发起人对应多个异常
	private Set<Abnormal> abnormalSet;// 应答人与异常多对多
	private Set<SwiptCard> swiptCardSet;// 刷卡
	private Set<Quality> qualitySet;// 质量问题单
	private Set<FlowingRectify> flowingRectifySet;// 整改情况跟踪

	private Set<Role> roleSet;// 管理角色
	private GrantedAuthority[] authorities;// 角色信息
	private Department department;// 部门

	private Set<Carton> cartonConfirmUser;// 纸箱收货确认人
	private Set<DailyWork> dailyWorkConfirmUser;// 报工确认人
	private Set<EnteringwareHouse> enteringwareHouseConfirmUser;// 入库确认人
	private Set<Repairin> repairinConfirmUser;// 返修收货确认人
	private Set<Repair> repairConfirmUser;// 返修收货确认人
	private Set<Dump> dumpConfirmUser;//转储确认人
	private Set<Repairin> repairinCreateUser;// 返修收货创建人
	private Set<Repair> repairCreateUser;// 返修创建人
	private Set<Carton> cartonCreateUser;// 纸箱收货创建人
	private Set<DailyWork> dailyWorkCreateUser;// 报工创建人
	private Set<EnteringwareHouse> enteringwareHouseCreateUser;// 入库创建人
	private Set<Dump> dumpCreateUser;//转储创建人

	private Set<Rework> duty;// 责任人名
	private Set<Rework> confirmUser;// 创建人名
	private Set<Rework> createUser;// 确认人名
	private Set<Rework> modifyUser;// 修改人名

	// 责任人名
	@OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
	public Set<Rework> getDuty() {
		return duty;
	}

	public void setDuty(Set<Rework> duty) {
		this.duty = duty;
	}

	// 创建人名
	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Rework> getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Set<Rework> confirmUser) {
		this.confirmUser = confirmUser;
	}

	// 确认人名
	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Rework> getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Set<Rework> createUser) {
		this.createUser = createUser;
	}

	// 修改人名
	@OneToMany(mappedBy = "modifyUser", fetch = FetchType.LAZY)
	public Set<Rework> getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Set<Rework> modifyUser) {
		this.modifyUser = modifyUser;
	}

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<DailyWork> getDailyWorkConfirmUser() {
		return dailyWorkConfirmUser;
	}

	public void setDailyWorkConfirmUser(Set<DailyWork> dailyWorkConfirmUser) {
		this.dailyWorkConfirmUser = dailyWorkConfirmUser;
	}

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Carton> getCartonConfirmUser() {
		return cartonConfirmUser;
	}

	public void setCartonConfirmUser(Set<Carton> cartonConfirmUser) {
		this.cartonConfirmUser = cartonConfirmUser;
	}

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<EnteringwareHouse> getEnteringwareHouseConfirmUser() {
		return enteringwareHouseConfirmUser;
	}

	public void setEnteringwareHouseConfirmUser(
			Set<EnteringwareHouse> enteringwareHouseConfirmUser) {
		this.enteringwareHouseConfirmUser = enteringwareHouseConfirmUser;
	}

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Repair> getRepairConfirmUser() {
		return repairConfirmUser;
	}

	public void setRepairConfirmUser(Set<Repair> repairConfirmUser) {
		this.repairConfirmUser = repairConfirmUser;
	}
	
	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Dump> getDumpConfirmUser() {
		return dumpConfirmUser;
	}

	public void setDumpConfirmUser(Set<Dump> dumpConfirmUser) {
		this.dumpConfirmUser = dumpConfirmUser;
	}

	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Repair> getRepairCreateUser() {
		return repairCreateUser;
	}

	public void setRepairCreateUser(Set<Repair> repairCreateUser) {
		this.repairCreateUser = repairCreateUser;
	}

	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Carton> getCartonCreateUser() {
		return cartonCreateUser;
	}

	public void setCartonCreateUser(Set<Carton> cartonCreateUser) {
		this.cartonCreateUser = cartonCreateUser;
	}

	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<DailyWork> getDailyWorkCreateUser() {
		return dailyWorkCreateUser;
	}

	public void setDailyWorkCreateUser(Set<DailyWork> dailyWorkCreateUser) {
		this.dailyWorkCreateUser = dailyWorkCreateUser;
	}

	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<EnteringwareHouse> getEnteringwareHouseCreateUser() {
		return enteringwareHouseCreateUser;
	}

	public void setEnteringwareHouseCreateUser(
			Set<EnteringwareHouse> enteringwareHouseCreateUser) {
		this.enteringwareHouseCreateUser = enteringwareHouseCreateUser;
	}
	
	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Dump> getDumpCreateUser() {
		return dumpCreateUser;
	}

	public void setDumpCreateUser(Set<Dump> dumpCreateUser) {
		this.dumpCreateUser = dumpCreateUser;
	}

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Repairin> getRepairinConfirmUser() {
		return repairinConfirmUser;
	}

	public void setRepairinConfirmUser(Set<Repairin> repairinConfirmUser) {
		this.repairinConfirmUser = repairinConfirmUser;
	}

	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Repairin> getRepairinCreateUser() {
		return repairinCreateUser;
	}

	public void setRepairinCreateUser(Set<Repairin> repairinCreateUser) {
		this.repairinCreateUser = repairinCreateUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(updatable = false, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(Boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(nullable = false)
	public Boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	@Transient
	public boolean isEnabled() {
		return this.isAccountEnabled;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}

	@Transient
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			this.isDel = "N";
		} else
			this.isDel = isDel;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "iniitiator")
	public Set<Abnormal> getAbnormalList() {
		return abnormalList;
	}

	public void setAbnormalList(Set<Abnormal> abnormalList) {
		this.abnormalList = abnormalList;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "responsorSet")
	public Set<Abnormal> getAbnormalSet() {
		return abnormalSet;
	}

	public void setAbnormalSet(Set<Abnormal> abnormalSet) {
		this.abnormalSet = abnormalSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
	public Set<SwiptCard> getSwiptCardSet() {
		return swiptCardSet;
	}

	public void setSwiptCardSet(Set<SwiptCard> swiptCardSet) {
		this.swiptCardSet = swiptCardSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creater")
	public Set<Quality> getQualitySet() {
		return qualitySet;
	}

	public void setQualitySet(Set<Quality> qualitySet)	{
		this.qualitySet = qualitySet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operater")
	public Set<FlowingRectify> getFlowingRectifySet(){
		return flowingRectifySet;
	}

	public void setFlowingRectifySet(Set<FlowingRectify> flowingRectifySet) {
		this.flowingRectifySet = flowingRectifySet;
	}

}