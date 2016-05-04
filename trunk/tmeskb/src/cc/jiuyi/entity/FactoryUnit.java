package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 单元
 * 
 * @param args
 */

@Entity
@Searchable
// @Table(name = "FactoryUnit")
public class FactoryUnit extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String factoryUnitCode;// 单元编码
	private String factoryUnitName;// 单元描述
	private String state;// 状态
	private String isDel;// 是否删除
	private String stateRemark;// 状态描述
	private WorkShop workShop;// 车间
	private String workShopName;
	private String factoryName;
	private String warehouse;// 线边仓
	private String warehouseName;// 线边仓描述
	private Set<Team> team;// 班组
	private String CXORJC;//成型/挤出

	private Set<Products> productsSet;// 相关产品
	private Set<Material>materialSet;//物料表
	private String workCenter;// 工作中心
	private String costcenter;//成本中心
	private String iscanrepair;//是否可以返修/返修收获-编码
	private String psaddress;//配送仓库
	private String psaddressdes;//配送仓库描述
	private Set<CardManagement>cardmanagement;//IP
	private String psPositionAddress;//配送仓位
	private String delivery;//待发货仓位
	private Set<UnitdistributeProduct> unitdistributeSet;//分配产品
	private Set<UnitdistributeModel> unitdistributemodelSet;//分配磨具
	private Set<PositionManagement> positionManagement;//单元管理
	
	// 设置trigger名称
	private String triggername;  
	//设置表达式
	private String cronexpression;
	// 设置Job名称
	private String jobdetailname;
	//任务类名
	private String targetobject;
	//类名对应的方法名
	private String methodname;
	//方法名中的参数
	private String[] methodArguments;
	//设置是否并发启动任务 0是false 非0是true
	private String concurrent;
	// 如果计划任务不存在并且数据库里的任务状态为可用时,则创建计划任务 1为不存在
	private String readme;
	//是否是已经存在的springBean 1=是  非1是否
	private String isspringbean;
	//是否同步
	private String isSync;
	
	
	//假字段
	private String xiscanrepair;//是否可以返修/返修收获-描述
	private String xCXORJC;//成型/挤出 描述
	private String xisSync;//是否同步
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Products> getProductsSet() {
		return productsSet;
	}

	public void setProductsSet(Set<Products> productsSet) {
		this.productsSet = productsSet;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
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

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}

	@Transient
	public String getWorkShopName() {
		return workShopName;
	}

	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}

	@Transient
	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "factoryUnit")
	public Set<Team> getTeam() {
		return team;
	}

	public void setTeam(Set<Team> team) {
		this.team = team;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getIscanrepair()
	{
		return iscanrepair;
	}

	public void setIscanrepair(String iscanrepair)
	{
		this.iscanrepair = iscanrepair;
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
	public String getXiscanrepair()
	{
		return xiscanrepair;
	}

	public void setXiscanrepair(String xiscanrepair)
	{
		this.xiscanrepair = xiscanrepair;
	}

	public String getCXORJC()
	{
		return CXORJC;
	}

	public void setCXORJC(String cXORJC)
	{
		CXORJC = cXORJC;
	}

	@Transient
	public String getxCXORJC()
	{
		return xCXORJC;
	}

	public void setxCXORJC(String xCXORJC)
	{
		this.xCXORJC = xCXORJC;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="factoryunit")
	public Set<Material> getMaterialSet()
	{
		return materialSet;
	}

	public void setMaterialSet(Set<Material> materialSet)
	{
		this.materialSet = materialSet;
	}

	public String getPsaddress()
	{
		return psaddress;
	}

	public void setPsaddress(String psaddress)
	{
		this.psaddress = psaddress;
	}

	public String getPsaddressdes()
	{
		return psaddressdes;
	}

	public void setPsaddressdes(String psaddressdes)
	{
		this.psaddressdes = psaddressdes;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="factoryunit")
	public Set<CardManagement> getCardmanagement()
	{
		return cardmanagement;
	}

	public void setCardmanagement(Set<CardManagement> cardmanagement)
	{
		this.cardmanagement = cardmanagement;
	}

	public String getPsPositionAddress()
	{
		return psPositionAddress;
	}

	public void setPsPositionAddress(String psPositionAddress)
	{
		this.psPositionAddress = psPositionAddress;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="factoryunit")
	public Set<UnitdistributeProduct> getUnitdistributeSet() {
		return unitdistributeSet;
	}

	public void setUnitdistributeSet(Set<UnitdistributeProduct> unitdistributeSet) {
		this.unitdistributeSet = unitdistributeSet;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="factoryunit")
	public Set<UnitdistributeModel> getUnitdistributemodelSet() {
		return unitdistributemodelSet;
	}

	public void setUnitdistributemodelSet(
			Set<UnitdistributeModel> unitdistributemodelSet) {
		this.unitdistributemodelSet = unitdistributemodelSet;
	}

	@OneToMany(mappedBy = "factoryUnit", fetch = FetchType.LAZY)
	public Set<PositionManagement> getPositionManagement() {
		return positionManagement;
	}

	public void setPositionManagement(Set<PositionManagement> positionManagement) {
		this.positionManagement = positionManagement;
	}

	public String getTriggername() {
		return triggername;
	}

	public void setTriggername(String triggername) {
		this.triggername = triggername;
	}

	public String getCronexpression() {
		return cronexpression;
	}

	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}

	public String getJobdetailname() {
		return jobdetailname;
	}

	public void setJobdetailname(String jobdetailname) {
		this.jobdetailname = jobdetailname;
	}

	public String getTargetobject() {
		return targetobject;
	}

	public void setTargetobject(String targetobject) {
		this.targetobject = targetobject;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String[] getMethodArguments() {
		return methodArguments;
	}

	public void setMethodArguments(String[] methodArguments) {
		this.methodArguments = methodArguments;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	public String getReadme() {
		return readme;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public String getIsspringbean() {
		return isspringbean;
	}

	public void setIsspringbean(String isspringbean) {
		this.isspringbean = isspringbean;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}
	@Transient
	public String getXisSync() {
		return xisSync;
	}

	public void setXisSync(String xisSync) {
		this.xisSync = xisSync;
	}
	
	
	
}
