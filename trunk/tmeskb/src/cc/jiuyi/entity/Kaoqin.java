package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 考勤
 * @author lenovo
 *
 */
@Entity
public class Kaoqin extends BaseEntity{

	private static final long serialVersionUID = 6948365437771959825L;
	
	private String cardNumber;//卡号
	private String classtime;//班次
	private Admin emp;//员工
	private String empname;//名字
	private String postname;//技能名称
	private Team team;// 班组
	private String workState;//工作状态
	private String productdate;//生产日期
	private String empid;//员工ID--admin表主键
	private String tardyHours;//误工小时数
	
	@Column
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column
	public String getClasstime() {
		return classtime;
	}
	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}
	@Column
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	@Column
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
	}
	@Column
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getProductdate()
	{
		return productdate;
	}
	public void setProductdate(String productdate)
	{
		this.productdate = productdate;
	}
	public String getEmpid()
	{
		return empid;
	}
	public void setEmpid(String empid)
	{
		this.empid = empid;
	}
	public String getTardyHours()
	{
		return tardyHours;
	}
	public void setTardyHours(String tardyHours)
	{
		this.tardyHours = tardyHours;
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
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getEmp()
	{
		return emp;
	}
	public void setEmp(Admin emp)
	{
		this.emp = emp;
	}
}
