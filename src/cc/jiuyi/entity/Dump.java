package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——转储
 * 
 */
@Entity
//@Searchable
public class Dump extends BaseEntity {

	private static final long serialVersionUID = -2687433396607084358L;

	private String voucherId;// 物料凭证号
	private String mjahr;// 凭证年度
	private Date deliveryDate;// 过账日期
	private String deliveryTime;//过账时间
	private String state;// 状态
	private String isDel;// 是否删除
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private Set<DumpDetail> dumpDetail;// 转储明细
	private String materialCode;//物料编码
	private String allcount;//物料数量
	private String factoryUnitId;//单元ID
	private String productionDate;//生产日期
	private String shift;//班次
	private String materialdes;//物料描述
	
	//假字段
	private String xshift;//班次描述

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	public String getMjahr() {
		return mjahr;
	}

	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}

	@OneToMany(mappedBy = "dump", fetch = FetchType.LAZY)
	public Set<DumpDetail> getDumpDetail() {
		return dumpDetail;
	}

	public void setDumpDetail(Set<DumpDetail> dumpDetail) {
		this.dumpDetail = dumpDetail;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getMaterialCode()
	{
		return materialCode;
	}

	public void setMaterialCode(String materialCode)
	{
		this.materialCode = materialCode;
	}

	public String getAllcount()
	{
		return allcount;
	}

	public void setAllcount(String allcount)
	{
		this.allcount = allcount;
	}

	public String getFactoryUnitId()
	{
		return factoryUnitId;
	}

	public void setFactoryUnitId(String factoryUnitId)
	{
		this.factoryUnitId = factoryUnitId;
	}

	public String getProductionDate()
	{
		return productionDate;
	}

	public void setProductionDate(String productionDate)
	{
		this.productionDate = productionDate;
	}

	public String getShift()
	{
		return shift;
	}

	public void setShift(String shift)
	{
		this.shift = shift;
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

	public String getMaterialdes()
	{
		return materialdes;
	}

	public void setMaterialdes(String materialdes)
	{
		this.materialdes = materialdes;
	}
	
}
