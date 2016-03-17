package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类——返修
 * 
 */

@Entity
public class Repair extends BaseEntity {

	private static final long serialVersionUID = -1226180317662591944L;
	private String repairPart;// 返修部位
	private Integer repairAmount;// 返修数量
	private String duty;// 责任人
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String isDel;// 是否删除
	private String state;// 状态
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String dutyName;//责任人名字
	private String responseName;//责任工序名称
	private String workingbillCode;
	private String maktx;//产品描述
	private String processCode;//责任工序编码
	private Set<RepairPiece>rpieceSet;//从表,组件表
	private String repairtype;//成品/子件
	private WorkingBill workingbill;// 随工单
	private String EX_MBLNR;//物料凭证
	private String costcenter;//成本中心
	private String CXORJC;//成型/挤出
	private String mould;//模具
	//SAP
	private String WERKS;//工厂
	private String LGORT;//库存地点
	private String ZTEXT;//抬头文本
	private String BUDAT;//过账日期
	private String E_TYPE;//返回类型S/E
	private String E_MESSAGE;//返回消息
	
	//假字段
	private String xrepairtype;//成品/子件
	private String xmould;//模具
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getRepairPart() {
		return repairPart;
	}

	public void setRepairPart(String repairPart) {
		this.repairPart = repairPart;
	}

	public Integer getRepairAmount() {
		return repairAmount;
	}

	public void setRepairAmount(Integer repairAmount) {
		this.repairAmount = repairAmount;
	}
	
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null) {
			state = "2";
		}
		this.state = state;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Transient
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Transient
	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	@Transient
	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	@Transient
	public String getWorkingbillCode() {
		return workingbillCode;
	}

	public void setWorkingbillCode(String workingbillCode) {
		this.workingbillCode = workingbillCode;
	}

	@Transient
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getWERKS()
	{
		return WERKS;
	}

	public void setWERKS(String wERKS)
	{
		WERKS = wERKS;
	}

	public String getLGORT()
	{
		return LGORT;
	}

	public void setLGORT(String lGORT)
	{
		LGORT = lGORT;
	}

	public String getZTEXT()
	{
		return ZTEXT;
	}

	public void setZTEXT(String zTEXT)
	{
		ZTEXT = zTEXT;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="repair")
	public Set<RepairPiece> getRpieceSet()
	{
		return rpieceSet;
	}

	public void setRpieceSet(Set<RepairPiece> rpieceSet)
	{
		this.rpieceSet = rpieceSet;
	}

	public String getE_TYPE()
	{
		return E_TYPE;
	}

	public void setE_TYPE(String e_TYPE)
	{
		E_TYPE = e_TYPE;
	}

	public String getE_MESSAGE()
	{
		return E_MESSAGE;
	}

	public void setE_MESSAGE(String e_MESSAGE)
	{
		E_MESSAGE = e_MESSAGE;
	}

	public String getEX_MBLNR()
	{
		return EX_MBLNR;
	}

	public void setEX_MBLNR(String eX_MBLNR)
	{
		EX_MBLNR = eX_MBLNR;
	}

	public String getBUDAT()
	{
		return BUDAT;
	}

	public void setBUDAT(String bUDAT)
	{
		BUDAT = bUDAT;
	}

	public String getRepairtype()
	{
		return repairtype;
	}

	public void setRepairtype(String repairtype)
	{
		this.repairtype = repairtype;
	}

	public String getCostcenter()
	{
		return costcenter;
	}

	public void setCostcenter(String costcenter)
	{
		this.costcenter = costcenter;
	}

	@Transient
	public String getXrepairtype()
	{
		return xrepairtype;
	}

	public void setXrepairtype(String xrepairtype)
	{
		this.xrepairtype = xrepairtype;
	}

	public String getCXORJC()
	{
		return CXORJC;
	}

	public void setCXORJC(String cXORJC)
	{
		CXORJC = cXORJC;
	}

	public String getMould()
	{
		return mould;
	}

	public void setMould(String mould)
	{
		this.mould = mould;
	}

	@Transient
	public String getXmould()
	{
		return xmould;
	}

	public void setXmould(String xmould)
	{
		this.xmould = xmould;
	}
	
}
