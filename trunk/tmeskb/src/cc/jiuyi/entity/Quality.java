package cc.jiuyi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类 - 质量问题通知单
 */
@Entity
@Table(name = "quality_problem")
public class Quality extends BaseEntity{

	private static final long serialVersionUID = -7213483823153832426L;
	
	private String productNo;//产品编号
	private String productName;//产品名称
	private String process;//工序
	private String group;//班组
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
	
	@Column
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Column
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	
	@Column
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	@Column
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
	@Column
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	@Column
	public Integer getSamplingAmont() {
		return samplingAmont;
	}
	public void setSamplingAmont(Integer samplingAmont) {
		this.samplingAmont = samplingAmont;
	}
	
	@Column
	public Integer getFailAmont() {
		return failAmont;
	}
	public void setFailAmont(Integer failAmont) {
		this.failAmont = failAmont;
	}
	
	@Column
	public String getRectificationScheme() {
		return rectificationScheme;
	}
	public void setRectificationScheme(String rectificationScheme) {
		this.rectificationScheme = rectificationScheme;
	}
	
	@Column
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	
	@Column
	public String getEngineerOpinion() {
		return engineerOpinion;
	}
	public void setEngineerOpinion(String engineerOpinion) {
		this.engineerOpinion = engineerOpinion;
	}
	
	@Column
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@Column
	public Integer getExtrusionBatches() {
		return extrusionBatches;
	}
	public void setExtrusionBatches(Integer extrusionBatches) {
		this.extrusionBatches = extrusionBatches;
	}
	
	
}
