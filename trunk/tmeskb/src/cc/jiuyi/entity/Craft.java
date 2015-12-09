package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 工艺维修单
 */
@Entity
public class Craft extends BaseEntity{

	private static final long serialVersionUID = -3213423223153832326L;
	
	private String cabinetCode;//机台号
	private Products products;//产品
	
	private Team team;//班组
	private String unusualDescription_make;//异常描述_制造
	private String treatmentMeasure_make;//制造处理措施
	private String resultCode_make;//制造处理结果
	
	private String unusualDescription_process;//工艺异常分析
	private Admin repairName;//维修人员
	private String treatmentMeasure_process;//工艺处理措施
	private String resultCode_process;//工艺处理结果
	
	private Admin creater;//创建人
	private String isDel;//是否删除
	private String state;//
	private Abnormal abnormal;//异常
	
	private String stateRemark;//状态描述 
	private Set<CraftLog> craftLogSet;//异常日志
	private String productsName;//产品名称
	private String teamName;
	private String cabinetName;
	private Set<ReceiptReason> receiptReasonSet;//工艺原因
	
	public String getCabinetCode() {
		return cabinetCode;
	}
	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	public String getUnusualDescription_make() {
		return unusualDescription_make;
	}
	public void setUnusualDescription_make(String unusualDescription_make) {
		this.unusualDescription_make = unusualDescription_make;
	}
	public String getTreatmentMeasure_make() {
		return treatmentMeasure_make;
	}
	public void setTreatmentMeasure_make(String treatmentMeasure_make) {
		this.treatmentMeasure_make = treatmentMeasure_make;
	}
	public String getResultCode_make() {
		return resultCode_make;
	}
	public void setResultCode_make(String resultCode_make) {
		this.resultCode_make = resultCode_make;
	}
	public String getUnusualDescription_process() {
		return unusualDescription_process;
	}
	public void setUnusualDescription_process(String unusualDescription_process) {
		this.unusualDescription_process = unusualDescription_process;
	}
	public String getTreatmentMeasure_process() {
		return treatmentMeasure_process;
	}
	public void setTreatmentMeasure_process(String treatmentMeasure_process) {
		this.treatmentMeasure_process = treatmentMeasure_process;
	}
	public String getResultCode_process() {
		return resultCode_process;
	}
	public void setResultCode_process(String resultCode_process) {
		this.resultCode_process = resultCode_process;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Abnormal getAbnormal() {
		return abnormal;
	}
	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "craft")
	@Cascade(value = { CascadeType.DELETE })
	public Set<CraftLog> getCraftLogSet() {
		return craftLogSet;
	}
	public void setCraftLogSet(Set<CraftLog> craftLogSet) {
		this.craftLogSet = craftLogSet;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getCreater() {
		return creater;
	}
	public void setCreater(Admin creater) {
		this.creater = creater;
	}
	
	@Transient
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}	
	
	@Transient
	public String getCabinetName() {
		return cabinetName;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<ReceiptReason> getReceiptReasonSet() {
		return receiptReasonSet;
	}
	public void setReceiptReasonSet(Set<ReceiptReason> receiptReasonSet) {
		this.receiptReasonSet = receiptReasonSet;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getRepairName() {
		return repairName;
	}
	public void setRepairName(Admin repairName) {
		this.repairName = repairName;
	}
	
	
	
}
