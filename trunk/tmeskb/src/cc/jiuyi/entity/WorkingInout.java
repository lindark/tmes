package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
/**
 * 实体类——投入产出表
 * 
 */
@Entity
public class WorkingInout extends BaseEntity {

	private static final long serialVersionUID = -1376887577702408799L;
	
	
	private String materialCode;//物料编码
	private String materialName;//物料描述
	private Double beforeCutting;//接上班裁切数
	private Double beforeHalfproduct;//接上班半成品
	private Double beforeJointangle;//接上班接角返修品
	private Double beforeFlocking;//接上班植绒返修品
	private Double abnormalnumber;//投入异常数
	private Double beforeFraction;//接上班零头数
	private Double packetSuccession;//抽包异常接班
	private Double recipientsAmount;//领用数
	private Double multiple;//倍数
	private Double afterFraction;//接下班零头数
	private Double afterCutting;//交下班裁切数
	private Double afterHalfproduct;//交下班半成品
	private Double outputAbnormal;//产出异常数
	private Double packageAbnormallog;//抽包异常接班
	private Double scrapNumber;//报废数
	private Double afterFlocking;//交下班植绒返修数
	private Double afterJointangle;//交下班接角返修品
	private Double bnormalSurfaceAmount;//异常表面维修数
	private Double inspectiononeAmount;//检验合格数1
	private Double inspectiontwoAmount;//检验合格数2
	private Double inspectionthreeAmount;//检验合格数3
	private Double inspectionforeAmount;//检验合格数4
	private Double qualifiedRate;//一次合格率 %
	private Double repairAmount;//返修
	private Double repairinAmount;//返修收货
	private WorkingBill workingbill;
	
	//假字段
	private String xFactoryUnit;//单元
	
	//维修合格接收数
	//数量差异
	//计划达成率
	//报废金额
	
	/**冗余**/
	private String workingBillCode;
	/**冗余end**/
	
	
	public Double getBeforeCutting() {
		return beforeCutting;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}
	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	public void setBeforeCutting(Double beforeCutting) {
		this.beforeCutting = beforeCutting;
	}
	public Double getBeforeHalfproduct() {
		return beforeHalfproduct;
	}
	public void setBeforeHalfproduct(Double beforeHalfproduct) {
		this.beforeHalfproduct = beforeHalfproduct;
	}
	public Double getBeforeJointangle() {
		return beforeJointangle;
	}
	public void setBeforeJointangle(Double beforeJointangle) {
		this.beforeJointangle = beforeJointangle;
	}
	public Double getBeforeFlocking() {
		return beforeFlocking;
	}
	public void setBeforeFlocking(Double beforeFlocking) {
		this.beforeFlocking = beforeFlocking;
	}
	public Double getAbnormalnumber() {
		return abnormalnumber;
	}
	public void setAbnormalnumber(Double abnormalnumber) {
		this.abnormalnumber = abnormalnumber;
	}
	public Double getBeforeFraction() {
		return beforeFraction;
	}
	public void setBeforeFraction(Double beforeFraction) {
		this.beforeFraction = beforeFraction;
	}
	public Double getPacketSuccession() {
		return packetSuccession;
	}
	public void setPacketSuccession(Double packetSuccession) {
		this.packetSuccession = packetSuccession;
	}
	public Double getRecipientsAmount() {
		return recipientsAmount;
	}
	public void setRecipientsAmount(Double recipientsAmount) {
		this.recipientsAmount = recipientsAmount;
	}
	public Double getMultiple() {
		return multiple;
	}
	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}
	public Double getAfterFraction() {
		return afterFraction;
	}
	public void setAfterFraction(Double afterFraction) {
		this.afterFraction = afterFraction;
	}
	public Double getAfterCutting() {
		return afterCutting;
	}
	public void setAfterCutting(Double afterCutting) {
		this.afterCutting = afterCutting;
	}
	public Double getAfterHalfproduct() {
		return afterHalfproduct;
	}
	public void setAfterHalfproduct(Double afterHalfproduct) {
		this.afterHalfproduct = afterHalfproduct;
	}
	public Double getOutputAbnormal() {
		return outputAbnormal;
	}
	public void setOutputAbnormal(Double outputAbnormal) {
		this.outputAbnormal = outputAbnormal;
	}
	public Double getPackageAbnormallog() {
		return packageAbnormallog;
	}
	public void setPackageAbnormallog(Double packageAbnormallog) {
		this.packageAbnormallog = packageAbnormallog;
	}
	public Double getScrapNumber() {
		return scrapNumber;
	}
	public void setScrapNumber(Double scrapNumber) {
		this.scrapNumber = scrapNumber;
	}
	public Double getAfterFlocking() {
		return afterFlocking;
	}
	public void setAfterFlocking(Double afterFlocking) {
		this.afterFlocking = afterFlocking;
	}
	public Double getAfterJointangle() {
		return afterJointangle;
	}
	public void setAfterJointangle(Double afterJointangle) {
		this.afterJointangle = afterJointangle;
	}
	public Double getBnormalSurfaceAmount() {
		return bnormalSurfaceAmount;
	}
	public void setBnormalSurfaceAmount(Double bnormalSurfaceAmount) {
		this.bnormalSurfaceAmount = bnormalSurfaceAmount;
	}
	public Double getInspectiononeAmount() {
		return inspectiononeAmount;
	}
	public void setInspectiononeAmount(Double inspectiononeAmount) {
		this.inspectiononeAmount = inspectiononeAmount;
	}
	public Double getInspectiontwoAmount() {
		return inspectiontwoAmount;
	}
	public void setInspectiontwoAmount(Double inspectiontwoAmount) {
		this.inspectiontwoAmount = inspectiontwoAmount;
	}
	public Double getInspectionthreeAmount() {
		return inspectionthreeAmount;
	}
	public void setInspectionthreeAmount(Double inspectionthreeAmount) {
		this.inspectionthreeAmount = inspectionthreeAmount;
	}
	public Double getInspectionforeAmount() {
		return inspectionforeAmount;
	}
	public void setInspectionforeAmount(Double inspectionforeAmount) {
		this.inspectionforeAmount = inspectionforeAmount;
	}
	public Double getQualifiedRate() {
		return qualifiedRate;
	}
	public void setQualifiedRate(Double qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@Transient
	public String getWorkingBillCode() {
		return workingBillCode;
	}
	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}
	public Double getRepairAmount()
	{
		return repairAmount;
	}
	public void setRepairAmount(Double repairAmount)
	{
		this.repairAmount = repairAmount;
	}
	public Double getRepairinAmount()
	{
		return repairinAmount;
	}
	public void setRepairinAmount(Double repairinAmount)
	{
		this.repairinAmount = repairinAmount;
	}
	
	@Transient
	public String getxFactoryUnit() {
		return xFactoryUnit;
	}
	public void setxFactoryUnit(String xFactoryUnit) {
		this.xFactoryUnit = xFactoryUnit;
	}
	
}
