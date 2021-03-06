package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
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
 * 实体类 - 异常
 */
@Entity
//@Table(name = "exception")
public class Abnormal extends BaseEntity{

	private static final long serialVersionUID = -7513486823153842426L;

	private Date callDate;//呼叫时间
	private Date replyDate;// 应答时间
	private Integer handlingTime;//处理时间
	
	private String state;//状态	
	private String isDel;//是否删除
	private String stateRemark;//状态描述
	private String jobname;//任务名称
	private String classtime;//班次
	private String productdate;//生产日期
	
	private Date closeTime;//关闭时间
	private Date cancelTime;//撤销时间
	private String cancelPerson;//撤销人
	private String closePerson;//关闭人
	private Double responseTime;//响应时间
	private Double dealTime;//处理时间
	
	private Admin iniitiator;//发起人
	private Set<Admin> responsorSet;//应答人
	private Set<UnitdistributeProduct> productSet;//产品
	
	private Set<Quality> qualitySet;//质量问题单
	private Set<Model> modelSet;//工模维修单
	private Set<Craft> craftSet;//工艺维修单
	private Set<Callreason> callreasonSet;//呼叫原因
	private Set<Device> deviceSet;//设备维修单
	private Set<AbnormalLog> AbnormalLogSet;//异常日志
	private Set<SwiptCard> swiptCardSet;//刷卡
	
	private String callReason;
	private String originator;
	private String answer;
	private String log;
	private String disposeTime;
	private String closeOrcancel;
    private String closeOrcancelTime;
	
	@Column
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	
	@Column
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	
	@Column
	public Integer getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(Integer handlingTime) {
		this.handlingTime = handlingTime;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getIniitiator() {
		return iniitiator;
	}
	public void setIniitiator(Admin iniitiator) {
		this.iniitiator = iniitiator;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Admin> getResponsorSet() {
		return responsorSet;
	}
	public void setResponsorSet(Set<Admin> responsorSet) {
		this.responsorSet = responsorSet;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<UnitdistributeProduct> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<UnitdistributeProduct> productSet) {
		this.productSet = productSet;
	}
	
	@Column
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Column
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<Quality> getQualitySet() {
		return qualitySet;
	}
	public void setQualitySet(Set<Quality> qualitySet) {
		this.qualitySet = qualitySet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<Craft> getCraftSet() {
		return craftSet;
	}
	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}
	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Callreason> getCallreasonSet() {
		return callreasonSet;
	}
	public void setCallreasonSet(Set<Callreason> callreasonSet) {
		this.callreasonSet = callreasonSet;
	}
	
	@Transient
	public String getCallReason() {
		return callReason;
	}
	public void setCallReason(String callReason) {
		this.callReason = callReason;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	
	@Transient
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<Device> getDeviceSet() {
		return deviceSet;
	}
	public void setDeviceSet(Set<Device> deviceSet) {
		this.deviceSet = deviceSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<AbnormalLog> getAbnormalLogSet() {
		return AbnormalLogSet;
	}
	public void setAbnormalLogSet(Set<AbnormalLog> abnormalLogSet) {
		AbnormalLogSet = abnormalLogSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	public Set<SwiptCard> getSwiptCardSet() {
		return swiptCardSet;
	}
	public void setSwiptCardSet(Set<SwiptCard> swiptCardSet) {
		this.swiptCardSet = swiptCardSet;
	}
	
	@Transient
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}	
	
	@Transient
	public String getDisposeTime() {
		return disposeTime;
	}
	public void setDisposeTime(String disposeTime) {
		this.disposeTime = disposeTime;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getClasstime() {
		return classtime;
	}
	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}
	public String getProductdate() {
		return productdate;
	}
	public void setProductdate(String productdate) {
		this.productdate = productdate;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getCancelPerson() {
		return cancelPerson;
	}
	public void setCancelPerson(String cancelPerson) {
		this.cancelPerson = cancelPerson;
	}
	public String getClosePerson() {
		return closePerson;
	}
	public void setClosePerson(String closePerson) {
		this.closePerson = closePerson;
	}
	public Double getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Double responseTime) {
		this.responseTime = responseTime;
	}
	public Double getDealTime() {
		return dealTime;
	}
	public void setDealTime(Double dealTime) {
		this.dealTime = dealTime;
	}
	
	@Transient
	public String getCloseOrcancel() {
		return closeOrcancel;
	}
	public void setCloseOrcancel(String closeOrcancel) {
		this.closeOrcancel = closeOrcancel;
	}
	
	@Transient
	public String getCloseOrcancelTime() {
		return closeOrcancelTime;
	}
	public void setCloseOrcancelTime(String closeOrcancelTime) {
		this.closeOrcancelTime = closeOrcancelTime;
	}
	
    
	
		
}
