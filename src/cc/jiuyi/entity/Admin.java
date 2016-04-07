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
	private String sex;//性别
	private Date startWorkDate;//入职日期
	private String tardyHours;//误工小时数

	private String isDel;// 是否删除
	private String shift;// 班次
	private String productDate;// 生产日期
	private String phoneNo;// 手机号
	private String cardNumber;//卡号
	private String workNumber;//工号
	private String  identityCard;//身份证

	private Set<Abnormal> abnormalList;// 一个发起人对应多个异常
	private Set<Abnormal> abnormalSet;// 应答人与异常多对多
	private Set<SwiptCard> swiptCardSet;// 刷卡
	private Set<Quality> qualitySet;// 质量问题单
	private Set<FlowingRectify> flowingRectifySet;// 整改情况跟踪
	private Set<Model> modelSet;//一个人对应多个工模维修单
	private Set<AbnormalLog> abnormalLogSet;//异常日志
	private Set<Craft> craftSet;//工艺维修单
	private Set<UnusualLog> unusualLogSet;//质量问题单日志
	private Set<ModelLog> modelLogSet;//工模维修单日志
	private Set<CraftLog> craftLogSet;//工艺维修单日志 
	private Set<Device> deviceWorkSet;//车间联系人与设备维修单
	private Set<Device> deviceDisposalSet;//处理人员与设备维修单
	private Set<DeviceLog> deviceLogSet;//设备维修单日志
	private Set<Model> modelSet1;//一个维修员对应多个工模维修单
	private Set<Model> modelSet2;//一个检验员对应多个工模维修单
	private Set<Craft> craftSet1;//一个维修员对应多个工艺维修单
	private Set<Quality> qualitySet1;//一个接收人对应多个质量问题单
	private Set<Quality> qualitySet2;//一个工程师对应多个质量问题单

	private Set<Role> roleSet;// 管理角色
	private GrantedAuthority[] authorities;// 角色信息
	private Department department;// 部门

	private Set<DailyWork> dailyWorkConfirmUser;// 报工确认人
	private Set<EnteringwareHouse> enteringwareHouseConfirmUser;// 入库确认人
	private Set<Repairin> repairinConfirmUser;// 返修收货确认人
	private Set<Repair> repairConfirmUser;// 返修收货确认人
	private Set<Dump> dumpConfirmUser;//转储确认人
	private Set<Pollingtest> pollingtestConfirmUser;//巡检确认人
	private Set<Repairin> repairinCreateUser;// 返修收货创建人
	private Set<Repair> repairCreateUser;// 返修创建人
	private Set<DailyWork> dailyWorkCreateUser;// 报工创建人
	private Set<EnteringwareHouse> enteringwareHouseCreateUser;// 入库创建人
	private Set<Dump> dumpCreateUser;//转储创建人
	
	private Set<Pollingtest> pollingtestUser;//巡检人


	private Set<Rework> duty;//返工责任人名
	private Set<Rework> confirmUser;//返工创建人名
	private Set<Rework> createUser;//返工确认人名
	private Set<Rework> modifyUser;//返工修改人名
	
	private Set<PickDetail> pickDetailConfirmUser;//领料从表确认人
	private Set<Pick> pickConfirmUser;//领料主表确认人
	private Set<Pick> pickModifyUser;//领料主表修改人
	private Set<Pick> pickCreateUser;//领料主表创建人
	private Set<ItermediateTest> intermediateTestConfirmUser;//半成品巡检确认人
	private Set<ItermediateTest> intermediateTestCreateUser;//半成品巡检创建人
	
	private Set<HandOverProcess> savehandoverprocessSet;//保存人
	private Set<HandOverProcess> submithandoverprocessSet;//提交人
	private Set<HandOverProcess> approvalhandoverprocessSet;//确认人
	
	private Set<HandOver> savehandoverSet;//提交人
	private Set<HandOver> approvalhandoverSet;//审批人
	private Set<Sample> sampler;//抽检人
	private Set<Sample> comfirmation;//确认人
	private Set<Scrap> scrapcreaterSet;//提交人
	private Set<Scrap> scrapcomfirmationSet;//确认人
	private Set<KaoqinBrushCardRecord>kaoqinBCR;//开启考勤记录
	private Set<Team> teamSet;//班组
	private Set<Carton> cartonCreateUser;// 纸箱收货主表创建人
	private Set<Carton> cartonConfirmUser;// 纸箱收货主表确认人

	private Post post;//技能/岗位  工位是岗位里面的一个字段
	private String workstate;//状态1，考勤：添加代班员工时用到
	private String isdaiban;//是否代班
	
	private Admin parentAdmin;//上级
	private Set<Admin> adminSet;//下级
	private Admin empCreater;//人员创建人
	private Set<Admin>empCreaterSet;
	private Admin empCreaterqx;//权限维护创建人
	private Set<Admin>empCreaterqxSet;
	
	private Set<UnitdistributeProduct> unitdistributeProductSet;//工作范围
	private Set<UnitdistributeModel> unitdistributeModelSet;//模具组号
	private Set<Deptpick> createUserSet;//部门领料提交人
	private Set<Deptpick> comfirmUserSet;//部门领料审批人
	private Set<Department>deptLeader;//部门负责人
	private Set<Department>creater;//部门创建人
	private Set<UpDown> updownSet;//上架/下架
	private Team team;//班组
	private Set<Kaoqin>empSet;//考勤员工
	private String stationids;//工位ID
	private String relationShip;//亲属关系
	private String workerGrade;//操作等级工
	private String education;//最高学历
	private String isDelete;//是不删除
	private String nowState;//当前状态
	
	//假字段
	private String xdeptcode;//部门编码
	private String departName;// 部门名称
	private String xworkstate;//员工工作状态描述
	private String xshift;//班次
	private String xteam;//班组
	private String xpost;//岗位
	private String xstation;//模具组号
	private String xworkscope;//工作范围
	private String xparentAdmin;//直接上级
	private String xisJob;//是否离职
	private String xisenable;//是否启用
	private String xempCreater;//创建人
	private String xsex;//性别
	private String xrole;//管理角色
	private String xgongwei;//工位
	private String xstationval;//模组信息
	private String xfactoryUnit;//单元名称
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="appvaladmin")
	public Set<UpDown> getUpdownSet() {
		return updownSet;
	}

	public void setUpdownSet(Set<UpDown> updownSet) {
		this.updownSet = updownSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="createUser")
	public Set<Deptpick> getCreateUserSet() {
		return createUserSet;
	}

	public void setCreateUserSet(Set<Deptpick> createUserSet) {
		this.createUserSet = createUserSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="comfirmUser")
	public Set<Deptpick> getComfirmUserSet() {
		return comfirmUserSet;
	}

	public void setComfirmUserSet(Set<Deptpick> comfirmUserSet) {
		this.comfirmUserSet = comfirmUserSet;
	}

	@ManyToOne(fetch = FetchType.LAZY)	
	public Admin getParentAdmin() {
		return parentAdmin;
	}

	public void setParentAdmin(Admin parentAdmin) {
		this.parentAdmin = parentAdmin;
	}

	@OneToMany(fetch = FetchType.LAZY,mappedBy="parentAdmin")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	//半成品巡检确认人
	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<ItermediateTest> getIntermediateTestConfirmUser() {
		return intermediateTestConfirmUser;
	}

	public void setIntermediateTestConfirmUser(
			Set<ItermediateTest> intermediateTestConfirmUser) {
		this.intermediateTestConfirmUser = intermediateTestConfirmUser;
	}

	//半成品巡检创建人
	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<ItermediateTest> getIntermediateTestCreateUser() {
		return intermediateTestCreateUser;
	}

	public void setIntermediateTestCreateUser(
			Set<ItermediateTest> intermediateTestCreateUser) {
		this.intermediateTestCreateUser = intermediateTestCreateUser;
	}

	//领料主表修改人
	@OneToMany(mappedBy = "modifyUser", fetch = FetchType.LAZY)
	public Set<Pick> getPickModifyUser() {
		return pickModifyUser;
	}

	public void setPickModifyUser(Set<Pick> pickModifyUser) {
		this.pickModifyUser = pickModifyUser;
	}

	//领料主表确认人
	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Pick> getPickConfirmUser() {
		return pickConfirmUser;
	}

	public void setPickConfirmUser(Set<Pick> pickConfirmUser) {
		this.pickConfirmUser = pickConfirmUser;
	}

	//领料主表创建人
	@OneToMany(mappedBy = "createUser", fetch = FetchType.LAZY)
	public Set<Pick> getPickCreateUser() {
		return pickCreateUser;
	}

	public void setPickCreateUser(Set<Pick> pickCreateUser) {
		this.pickCreateUser = pickCreateUser;
	}

	
	//领料从表确认人
	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<PickDetail> getPickDetailConfirmUser() {
		return pickDetailConfirmUser;
	}

	public void setPickDetailConfirmUser(Set<PickDetail> pickDetailConfirmUser) {
		this.pickDetailConfirmUser = pickDetailConfirmUser;
	}


	//责任人名
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

	@OneToMany(mappedBy = "confirmUser", fetch = FetchType.LAZY)
	public Set<Pollingtest> getPollingtestConfirmUser() {
		return pollingtestConfirmUser;
	}

	public void setPollingtestConfirmUser(Set<Pollingtest> pollingtestConfirmUser) {
		this.pollingtestConfirmUser = pollingtestConfirmUser;
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
	
	@OneToMany(mappedBy = "pollingtestUser", fetch = FetchType.LAZY)
	public Set<Pollingtest> getPollingtestUser() {
		return pollingtestUser;
	}

	public void setPollingtestUser(Set<Pollingtest> pollingtestUser) {
		this.pollingtestUser = pollingtestUser;
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

	//@Column(updatable = false, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
		}
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "initiator")
	public Set<Model> getModelSet() {
		return modelSet;
	}

	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
	public Set<AbnormalLog> getAbnormalLogSet() {
		return abnormalLogSet;
	}

	public void setAbnormalLogSet(Set<AbnormalLog> abnormalLogSet) {
		this.abnormalLogSet = abnormalLogSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creater")
	public Set<Craft> getCraftSet() {
		return craftSet;
	}

	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
	public Set<UnusualLog> getUnusualLogSet() {
		return unusualLogSet;
	}

	public void setUnusualLogSet(Set<UnusualLog> unusualLogSet) {
		this.unusualLogSet = unusualLogSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
	public Set<ModelLog> getModelLogSet() {
		return modelLogSet;
	}

	public void setModelLogSet(Set<ModelLog> modelLogSet) {
		this.modelLogSet = modelLogSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
	public Set<CraftLog> getCraftLogSet() {
		return craftLogSet;
	}

	public void setCraftLogSet(Set<CraftLog> craftLogSet) {
		this.craftLogSet = craftLogSet;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="saveadmin")
	public Set<HandOverProcess> getSavehandoverprocessSet() {
		return savehandoverprocessSet;
	}

	public void setSavehandoverprocessSet(
			Set<HandOverProcess> savehandoverprocessSet) {
		this.savehandoverprocessSet = savehandoverprocessSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="submitadmin")
	public Set<HandOverProcess> getSubmithandoverprocessSet() {
		return submithandoverprocessSet;
	}

	public void setSubmithandoverprocessSet(
			Set<HandOverProcess> submithandoverprocessSet) {
		this.submithandoverprocessSet = submithandoverprocessSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="approvaladmin")
	public Set<HandOverProcess> getApprovalhandoverprocessSet() {
		return approvalhandoverprocessSet;
	}

	public void setApprovalhandoverprocessSet(
			Set<HandOverProcess> approvalhandoverprocessSet) {
		this.approvalhandoverprocessSet = approvalhandoverprocessSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="submitadmin")
	public Set<HandOver> getSavehandoverSet() {
		return savehandoverSet;
	}

	public void setSavehandoverSet(Set<HandOver> savehandoverSet) {
		this.savehandoverSet = savehandoverSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="approvaladmin")
	public Set<HandOver> getApprovalhandoverSet() {
		return approvalhandoverSet;
	}

	public void setApprovalhandoverSet(Set<HandOver> approvalhandoverSet) {
		this.approvalhandoverSet = approvalhandoverSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="workshopLinkman")
	public Set<Device> getDeviceWorkSet() {
		return deviceWorkSet;
	}

	public void setDeviceWorkSet(Set<Device> deviceWorkSet) {
		this.deviceWorkSet = deviceWorkSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="disposalWorkers")
	public Set<Device> getDeviceDisposalSet() {
		return deviceDisposalSet;
	}

	public void setDeviceDisposalSet(Set<Device> deviceDisposalSet) {
		this.deviceDisposalSet = deviceDisposalSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="operator")
	public Set<DeviceLog> getDeviceLogSet() {
		return deviceLogSet;
	}

	public void setDeviceLogSet(Set<DeviceLog> deviceLogSet) {
		this.deviceLogSet = deviceLogSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="fixer")
	public Set<Model> getModelSet1() {
		return modelSet1;
	}

	public void setModelSet1(Set<Model> modelSet1) {
		this.modelSet1 = modelSet1;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="insepector")
	public Set<Model> getModelSet2() {
		return modelSet2;
	}

	public void setModelSet2(Set<Model> modelSet2) {
		this.modelSet2 = modelSet2;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="sampler")
	public Set<Sample> getSampler()
	{
		return sampler;
	}

	public void setSampler(Set<Sample> sampler)
	{
		this.sampler = sampler;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="comfirmation")
	public Set<Sample> getComfirmation()
	{
		return comfirmation;
	}

	public void setComfirmation(Set<Sample> comfirmation)
	{
		this.comfirmation = comfirmation;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="creater")
	public Set<Scrap> getScrapcreaterSet()
	{
		return scrapcreaterSet;
	}

	public void setScrapcreaterSet(Set<Scrap> scrapcreaterSet)
	{
		this.scrapcreaterSet = scrapcreaterSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="confirmation")
	public Set<Scrap> getScrapcomfirmationSet()
	{
		return scrapcomfirmationSet;
	}

	public void setScrapcomfirmationSet(Set<Scrap> scrapcomfirmationSet)
	{
		this.scrapcomfirmationSet = scrapcomfirmationSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="repairName")
	public Set<Craft> getCraftSet1() {
		return craftSet1;
	}

	public void setCraftSet1(Set<Craft> craftSet1) {
		this.craftSet1 = craftSet1;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="receiver")
	public Set<Quality> getQualitySet1() {
		return qualitySet1;
	}

	public void setQualitySet1(Set<Quality> qualitySet1) {
		this.qualitySet1 = qualitySet1;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Post getPost()
	{
		return post;
	}

	public void setPost(Post post)
	{
		this.post = post;
	}

	@Column
	public String getWorkstate()
	{
		return workstate;
	}

	public void setWorkstate(String workstate)
	{
		if( workstate == null){
			workstate ="1";
		}
		this.workstate = workstate;
	}

	@Column
	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	@Transient
	public String getXshift()
	{
		return xshift;
	}

	public void setXshift(String xshift)
	{
		this.xshift = xshift;
	}

	@Transient
	public String getXteam()
	{
		return xteam;
	}

	public void setXteam(String xteam)
	{
		this.xteam = xteam;
	}

	@Transient
	public String getXworkstate()
	{
		return xworkstate;
	}

	public void setXworkstate(String xworkstate)
	{
		this.xworkstate = xworkstate;
	}

	@Column
	public String getIsdaiban()
	{
		return isdaiban;
	}

	public void setIsdaiban(String isdaiban)
	{
		if(isdaiban==null)
		{
			isdaiban="N";
		}
		this.isdaiban = isdaiban;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="admin")
	public Set<KaoqinBrushCardRecord> getKaoqinBCR()
	{
		return kaoqinBCR;
	}

	public void setKaoqinBCR(Set<KaoqinBrushCardRecord> kaoqinBCR)
	{
		this.kaoqinBCR = kaoqinBCR;
	}
	

	@OneToMany(fetch=FetchType.LAZY,mappedBy="engineer")
	public Set<Quality> getQualitySet2() {
		return qualitySet2;
	}

	public void setQualitySet2(Set<Quality> qualitySet2) {
		this.qualitySet2 = qualitySet2;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate desc ")
	public Set<Team> getTeamSet() {
		return teamSet;
	}

	public void setTeamSet(Set<Team> teamSet) {
		this.teamSet = teamSet;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<UnitdistributeProduct> getUnitdistributeProductSet() {
		return unitdistributeProductSet;
	}

	public void setUnitdistributeProductSet(
			Set<UnitdistributeProduct> unitdistributeProductSet) {
		this.unitdistributeProductSet = unitdistributeProductSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<UnitdistributeModel> getUnitdistributeModelSet() {
		return unitdistributeModelSet;
	}

	public void setUnitdistributeModelSet(
			Set<UnitdistributeModel> unitdistributeModelSet) {
		this.unitdistributeModelSet = unitdistributeModelSet;
	}

	@Transient
	public String getXpost() {
		return xpost;
	}

	public void setXpost(String xpost) {
		this.xpost = xpost;
	}

	@Transient
	public String getXstation() {
		return xstation;
	}

	public void setXstation(String xstation) {
		this.xstation = xstation;
	}

	@Transient
	public String getXworkscope() {
		return xworkscope;
	}

	public void setXworkscope(String xworkscope) {
		this.xworkscope = xworkscope;
	}

	@Transient
	public String getXparentAdmin()
	{
		return xparentAdmin;
	}

	public void setXparentAdmin(String xparentAdmin)
	{
		this.xparentAdmin = xparentAdmin;
	}

	@Transient
	public String getXisJob()
	{
		return xisJob;
	}

	public void setXisJob(String xisJob)
	{
		this.xisJob = xisJob;
	}

	@Transient
	public String getXisenable()
	{
		return xisenable;
	}

	public void setXisenable(String xisenable)
	{
		this.xisenable = xisenable;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="deptLeader")
	public Set<Department> getDeptLeader()
	{
		return deptLeader;
	}

	public void setDeptLeader(Set<Department> deptLeader)
	{
		this.deptLeader = deptLeader;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="creater")
	public Set<Department> getCreater()
	{
		return creater;
	}

	public void setCreater(Set<Department> creater)
	{
		this.creater = creater;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Team getTeam()
	{
		return team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}
	@Transient
	public String getXdeptcode()
	{
		return xdeptcode;
	}

	public void setXdeptcode(String xdeptcode)
	{
		this.xdeptcode = xdeptcode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getEmpCreater()
	{
		return empCreater;
	}

	public void setEmpCreater(Admin empCreater)
	{
		this.empCreater = empCreater;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="empCreater")
	public Set<Admin> getEmpCreaterSet()
	{
		return empCreaterSet;
	}

	public void setEmpCreaterSet(Set<Admin> empCreaterSet)
	{
		this.empCreaterSet = empCreaterSet;
	}
	@Transient
	public String getXempCreater()
	{
		return xempCreater;
	}

	public void setXempCreater(String xempCreater)
	{
		this.xempCreater = xempCreater;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}
	@Transient
	public String getXsex()
	{
		return xsex;
	}

	public void setXsex(String xsex)
	{
		this.xsex = xsex;
	}

	public Date getStartWorkDate()
	{
		return startWorkDate;
	}

	public void setStartWorkDate(Date startWorkDate)
	{
		this.startWorkDate = startWorkDate;
	}

	public String getTardyHours()
	{
		return tardyHours;
	}

	public void setTardyHours(String tardyHours)
	{
		this.tardyHours = tardyHours;
	}

	@Transient
	public String getXrole()
	{
		return xrole;
	}

	public void setXrole(String xrole)
	{
		this.xrole = xrole;
	}
	@Transient
	public String getXstationval() {
		return xstationval;
	}

	public void setXstationval(String xstationval) {
		this.xstationval = xstationval;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getEmpCreaterqx()
	{
		return empCreaterqx;
	}

	public void setEmpCreaterqx(Admin empCreaterqx)
	{
		this.empCreaterqx = empCreaterqx;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="empCreaterqx")
	public Set<Admin> getEmpCreaterqxSet()
	{
		return empCreaterqxSet;
	}

	public void setEmpCreaterqxSet(Set<Admin> empCreaterqxSet)
	{
		this.empCreaterqxSet = empCreaterqxSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="emp")
	public Set<Kaoqin> getEmpSet()
	{
		return empSet;
	}

	public void setEmpSet(Set<Kaoqin> empSet)
	{
		this.empSet = empSet;
	}

	public String getXgongwei()
	{
		return xgongwei;
	}

	public void setXgongwei(String xgongwei)
	{
		this.xgongwei = xgongwei;
	}

	public String getStationids()
	{
		return stationids;
	}

	public void setStationids(String stationids)
	{
		this.stationids = stationids;
	}

	public String getRelationShip()
	{
		return relationShip;
	}

	public void setRelationShip(String relationShip)
	{
		this.relationShip = relationShip;
	}

	public String getWorkerGrade()
	{
		return workerGrade;
	}

	public void setWorkerGrade(String workerGrade)
	{
		this.workerGrade = workerGrade;
	}

	public String getEducation()
	{
		return education;
	}

	public void setEducation(String education)
	{
		this.education = education;
	}

	public String getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(String isDelete)
	{
		if(isDelete==null)
		{
			isDelete="N";
		}
		this.isDelete = isDelete;
	}

	public String getNowState()
	{
		return nowState;
	}

	public void setNowState(String nowState)
	{
		this.nowState = nowState;
	}
	public String getXfactoryUnit()
	{
		return xfactoryUnit;
	}
	@Transient
	public void setXfactoryUnit(String xfactoryUnit)
	{
		this.xfactoryUnit = xfactoryUnit;
	}
}