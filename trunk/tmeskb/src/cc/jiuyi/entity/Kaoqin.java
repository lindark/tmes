package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
	private String postname;//岗位名称
	private Team team;// 班组
	private String workState;//工作状态
	private String productdate;//生产日期
	private String empid;//员工ID--admin表主键
	private String tardyHours;//误工小时数
	
	//后续加上的字段
	private String workCode;//工号
	private String phoneNum;//联系电话
	private String stationCode;//工位
	private String modleNum;//模具组号
	private String workNum;//工作范围-编码
	private String postCode;//岗位编码
	private String factoryUnit;//单元名字
	private String factoryUnitCode;//单元编码
	private String stationName;//工位名称
	private String workName;//工作范围名称
	
	private String isdaiban;//是否代班
	private String workHours;//工作小时
	
	//假字段
	private String xteam;//班组假字段
	private String factoryUnitName;
	private String xclasstime;
	private String factory;
	private String workshop;
	private String xworkState;//工作状态
	
	private String modelName;//模具组号-名称
	
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
	
	public String getWorkCode()
	{
		return workCode;
	}
	public void setWorkCode(String workCode)
	{
		this.workCode = workCode;
	}
	public String getPhoneNum()
	{
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum)
	{
		this.phoneNum = phoneNum;
	}
	@Column(length=4000)
	public String getModleNum()
	{
		return modleNum;
	}
	public void setModleNum(String modleNum)
	{
		this.modleNum = modleNum;
	}
	@Column(length=4000)
	public String getWorkNum()
	{
		return workNum;
	}
	public void setWorkNum(String workNum)
	{
		this.workNum = workNum;
	}
	@Column(length=4000)
	public String getStationCode()
	{
		return stationCode;
	}
	public void setStationCode(String stationCode)
	{
		this.stationCode = stationCode;
	}
	@Column(length=4000)
	public String getStationName()
	{
		return stationName;
	}
	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}
	@Column(length=4000)
	public String getWorkName()
	{
		return workName;
	}
	public void setWorkName(String workName)
	{
		this.workName = workName;
	}
	public String getPostCode()
	{
		return postCode;
	}
	public void setPostCode(String postCode)
	{
		this.postCode = postCode;
	}
	
	@Transient
	public String getFactoryUnitName() {
		return factoryUnitName;
	}
	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
	}
	
	@Transient
	public String getXteam() {
		return xteam;
	}
	public void setXteam(String xteam) {
		this.xteam = xteam;
	}
	@Transient
	public String getXclasstime() {
		return xclasstime;
	}
	public void setXclasstime(String xclasstime) {
		this.xclasstime = xclasstime;
	}
	@Transient
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	@Transient
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getFactoryUnit()
	{
		return factoryUnit;
	}
	public void setFactoryUnit(String factoryUnit)
	{
		this.factoryUnit = factoryUnit;
	}
	@Transient
	public String getXworkState()
	{
		return xworkState;
	}
	public void setXworkState(String xworkState)
	{
		this.xworkState = xworkState;
	}

	
	public String getIsdaiban() {
		return isdaiban;
	}
	public void setIsdaiban(String isdaiban) {
		this.isdaiban = isdaiban;
	}
	
	
	@Transient
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	
	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}
	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}
	
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	
	
}

