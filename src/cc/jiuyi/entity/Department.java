package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 部门
 */

@Entity
public class Department extends BaseEntity {

	private static final long serialVersionUID = -7743118661914508794L;
	
	private String deptCode;//部门编码
	private String deptName;//部门名称
	private Admin deptLeader;//部门负责人
	private Admin creater;//创建人
	private String State;//状态
	private String isDel;//是否删除
	private String costcenter;//成本中心
	private String movetype;//发料移动类型
	private String movetype1;//退料移动类型
	private Department parentDept;//上级部门
	private Set<Department> childDept;//下级部门
	private Set<Admin> admin;//员工
	private Team team;//班组
	private String isWork;//是否启用
	
	//假字段
	private String xdeptLeader;//部门负责人
	private String xparentDept;//上级部门
	private String xdeptBossLeader;//上级部门负责人
	private String xcreater;//创建人
	private String xisWork;//是否启用
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
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
	@OneToMany(fetch = FetchType.LAZY,mappedBy="parentDept")
	public Set<Department> getChildDept() {
		return childDept;
	}
	public void setChildDept(Set<Department> childDept) {
		this.childDept = childDept;
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
	public String getCostcenter() {
		return costcenter;
	}
	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}
	public String getMovetype() {
		return movetype;
	}
	public void setMovetype(String movetype) {
		this.movetype = movetype;
	}
	public String getMovetype1() {
		return movetype1;
	}
	public void setMovetype1(String movetype1) {
		this.movetype1 = movetype1;
	}
	public String getDeptCode()
	{
		return deptCode;
	}
	public void setDeptCode(String deptCode)
	{
		this.deptCode = deptCode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getDeptLeader()
	{
		return deptLeader;
	}
	public void setDeptLeader(Admin deptLeader)
	{
		this.deptLeader = deptLeader;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getCreater()
	{
		return creater;
	}
	public void setCreater(Admin creater)
	{
		this.creater = creater;
	}
	@Transient
	public String getXdeptLeader()
	{
		return xdeptLeader;
	}
	public void setXdeptLeader(String xdeptLeader)
	{
		this.xdeptLeader = xdeptLeader;
	}
	@Transient
	public String getXparentDept()
	{
		return xparentDept;
	}
	public void setXparentDept(String xparentDept)
	{
		this.xparentDept = xparentDept;
	}
	@Transient
	public String getXdeptBossLeader()
	{
		return xdeptBossLeader;
	}
	public void setXdeptBossLeader(String xdeptBossLeader)
	{
		this.xdeptBossLeader = xdeptBossLeader;
	}
	@Transient
	public String getXcreater()
	{
		return xcreater;
	}
	public void setXcreater(String xcreater)
	{
		this.xcreater = xcreater;
	}
	public String getIsWork()
	{
		return isWork;
	}
	public void setIsWork(String isWork)
	{
		this.isWork = isWork;
	}
	@Transient
	public String getXisWork()
	{
		return xisWork;
	}
	public void setXisWork(String xisWork)
	{
		this.xisWork = xisWork;
	}
}