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
	
	//假字段
	private String xiscanrepair;//是否可以返修/返修收获-描述
	private String xCXORJC;//成型/挤出 描述
	
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
	
}
