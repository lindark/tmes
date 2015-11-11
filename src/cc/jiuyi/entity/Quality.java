package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 质量问题通知单
 */
@Entity
public class Quality extends BaseEntity{

	private static final long serialVersionUID = -7213483223153832423L;
	
	private String productNo;//产品编号
	private String productName;//产品名称
	private String process;//工序
	private String team;//班组
	private String problemDescription;//问题描述
	private String createUser;//创建人
	private String modifyUser;//修改人
	
	private Integer samplingAmont;//抽检数量
	private Integer failAmont;//缺陷数量
	private Integer extrusionBatches;//挤出批次
	private String rectificationScheme;//车间整改方案
	private Date overTime;//计划完成时间
	
	private String engineerOpinion;//工程师意见
	private String isDel;//是否删除	
	private String state;//状态
	private Abnormal abnormal;//异常
	
	private Set<FlowingRectify> flowingRectify;//整改情况跟踪
	
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}

	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
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
	
}
