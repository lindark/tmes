package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 质量问题通知单
 */
@Entity
public class Quality extends BaseEntity{

	private static final long serialVersionUID = -7213483223153832423L;
	
	private Team team;//班组
	private QualityProblemDescription qualityProblemDescription;//问题描述
	private Admin creater;//创建人
	private Admin receiver;//接收人
	private Admin engineer;//工程师
	
	private Integer samplingAmont;//抽检数量
	private Integer failAmont;//缺陷数量
	private Integer extrusionBatches;//挤出批次
	private String rectificationScheme;//车间整改方案
	private Date overTime;//计划完成时间
	
	private String engineerOpinion;//工程师意见
	private String isDel;//是否删除	
	private String state;//状态
	private Abnormal abnormal;//异常
	private String process;//工序
	
	private String stateRemark;//状态描述    
	private String founder;
	private String products;//产品
	private String bom;//产品bom
	
	private String productsName;
	private String processName;
	private String teamName;
	private String problemDescriptionName;
	
	private String machineNumber;//台机号      jjt
	private String productDate;// 生产日期 jjt
	private String shift;// 班次 jjt
	
	private Set<FlowingRectify> flowingRectify;//整改情况跟踪
	private Set<UnusualLog> unusualLogSet;//异常日志
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public QualityProblemDescription getQualityProblemDescription() {
		return qualityProblemDescription;
	}
	public void setQualityProblemDescription(
			QualityProblemDescription qualityProblemDescription) {
		this.qualityProblemDescription = qualityProblemDescription;
	}
	
	public Integer getSamplingAmont() {
		return samplingAmont;
	}	
	public void setSamplingAmont(Integer samplingAmont) {
		this.samplingAmont = samplingAmont;
	}
	public Integer getFailAmont() {
		return failAmont;
	}
	public void setFailAmont(Integer failAmont) {
		this.failAmont = failAmont;
	}
	public Integer getExtrusionBatches() {
		return extrusionBatches;
	}
	public void setExtrusionBatches(Integer extrusionBatches) {
		this.extrusionBatches = extrusionBatches;
	}
	public String getRectificationScheme() {
		return rectificationScheme;
	}
	public void setRectificationScheme(String rectificationScheme) {
		this.rectificationScheme = rectificationScheme;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public String getEngineerOpinion() {
		return engineerOpinion;
	}
	public void setEngineerOpinion(String engineerOpinion) {
		this.engineerOpinion = engineerOpinion;
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
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Abnormal getAbnormal() {
		return abnormal;
	}
	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quality")
	@Cascade(value = { CascadeType.DELETE })
	public Set<FlowingRectify> getFlowingRectify() {
		return flowingRectify;
	}
	public void setFlowingRectify(Set<FlowingRectify> flowingRectify) {
		this.flowingRectify = flowingRectify;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quality")
	@Cascade(value = { CascadeType.DELETE })
	public Set<UnusualLog> getUnusualLogSet() {
		return unusualLogSet;
	}
	public void setUnusualLogSet(Set<UnusualLog> unusualLogSet) {
		this.unusualLogSet = unusualLogSet;
	}
	
	
	
	
	
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}*/
	
		
	@Transient
	public String getProductsName() {
		return productsName;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getCreater() {
		return creater;
	}
	public void setCreater(Admin creater) {
		this.creater = creater;
	}
	
	@Transient
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@Transient
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
	@Transient
	public String getProblemDescriptionName() {
		return problemDescriptionName;
	}
	public void setProblemDescriptionName(String problemDescriptionName) {
		this.problemDescriptionName = problemDescriptionName;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getReceiver() {
		return receiver;
	}
	
	public void setReceiver(Admin receiver) {
		this.receiver = receiver;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getEngineer() {
		return engineer;
	}
	public void setEngineer(Admin engineer) {
		this.engineer = engineer;
	}
	public String getBom() {
		return bom;
	}
	public void setBom(String bom) {
		this.bom = bom;
	}
	
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	
	
	
}
