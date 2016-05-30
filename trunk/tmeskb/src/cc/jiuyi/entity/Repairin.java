package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——返修收货
 * 
 */
@Entity
//@Searchable
public class Repairin extends BaseEntity {

	private static final long serialVersionUID = 727896572443412459L;

	private Integer receiveAmount;// 收获数量
	private String state;// 状态
	private String isDel;// 是否删除
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String workingbillCode;
	private String maktx;//产品描述
	private Set<RepairinPiece>rpieceSet;//从表--组件
	private String repairintype;//成品/子件
	private WorkingBill workingbill;// 随工单
	private String EX_MBLNR;//物料凭证
	private String costcenter;//成本中心
	private String departName;//部门名称
	private String CXORJC;//成型/挤出
	private String productDate;// 生产日期
	private String shift;//班次
	private String matnr;// 产品编号
	private String factoryUnitCode;// 单元编码
	private String factoryUnitName;// 单元编码
	
	//SAP
	private String WERKS;//工厂
	private String LGORT;//库存地点
	private String ZTEXT;//抬头文本
	private String BUDAT;//过账日期
	private String E_TYPE;//返回类型S/E
	private String E_MESSAGE;//返回消息
	
	/**冗余字段
	private String productDate;
	private String matnr;
	**/
	
	private String xrepairintype;//成品/子件-描述
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public Integer getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Integer receiveAmount) {
		this.receiveAmount = receiveAmount;
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

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
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

	@OneToMany(fetch=FetchType.LAZY,mappedBy="repairin")
	public Set<RepairinPiece> getRpieceSet()
	{
		return rpieceSet;
	}

	public void setRpieceSet(Set<RepairinPiece> rpieceSet)
	{
		this.rpieceSet = rpieceSet;
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

	public String getEX_MBLNR()
	{
		return EX_MBLNR;
	}

	public void setEX_MBLNR(String eX_MBLNR)
	{
		EX_MBLNR = eX_MBLNR;
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

	public String getBUDAT()
	{
		return BUDAT;
	}

	public void setBUDAT(String bUDAT)
	{
		BUDAT = bUDAT;
	}

	public String getRepairintype()
	{
		return repairintype;
	}

	public void setRepairintype(String repairintype)
	{
		this.repairintype = repairintype;
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
	public String getXrepairintype()
	{
		return xrepairintype;
	}

	public void setXrepairintype(String xrepairintype)
	{
		this.xrepairintype = xrepairintype;
	}

	public String getCXORJC()
	{
		return CXORJC;
	}

	public void setCXORJC(String cXORJC)
	{
		CXORJC = cXORJC;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}

	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}

	public String getFactoryUnitName() {
		return factoryUnitName;
	}

	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
	}
	
	
	
}
