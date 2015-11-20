package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
	
	private String factoryName;//工厂
	private String shopName;//车间
	private String unitName;//单元
	private String teamId;//班组
	private Date callDate;//呼叫时间
	private Date replyDate;// 应答时间
	private Integer handlingTime;//处理时间
	
	private String message;//消息
	private String iniitiator;//发起人
	private String responsor;//应答人
	private String createUser;//创建人
	private String modifyUser;//修改人
	private String state;//状态	
	private String isDel;//是否删除
	private String stateRemark;//状态描述
	
	private Set<Quality> qualitySet;//质量问题单
	private Set<Model> modelSet;//工模维修单
	private Set<Craft> craftSet;//工艺维修单
	private Set<Admin> adminSet;//人员
	private Set<Callreason> callreasonSet;//呼叫原因
	private Set<Device> deviceSet;//工艺维修单
	private Set<AbnormalLog> AbnormalLogSet;//异常日志
	
	private String callReason;
	private String originator;
	private String answer;
	
	@Column
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	@Column
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Column
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
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
	
	@Column
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column
	public String getIniitiator() {
		return iniitiator;
	}
	public void setIniitiator(String iniitiator) {
		this.iniitiator = iniitiator;
	}
	
	@Column
	public String getResponsor() {
		return responsor;
	}
	public void setResponsor(String responsor) {
		this.responsor = responsor;
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
	@Cascade(value = { CascadeType.DELETE })
	public Set<Quality> getQualitySet() {
		return qualitySet;
	}
	public void setQualitySet(Set<Quality> qualitySet) {
		this.qualitySet = qualitySet;
	}
	
	public String getTeamId() {
		return teamId;
	}	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	@Cascade(value = { CascadeType.DELETE })
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	@Cascade(value = { CascadeType.DELETE })
	public Set<Craft> getCraftSet() {
		return craftSet;
	}
	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "abnormalSet")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}
	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "abnormalSet")
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
	@Cascade(value = { CascadeType.DELETE })
	public Set<Device> getDeviceSet() {
		return deviceSet;
	}
	public void setDeviceSet(Set<Device> deviceSet) {
		this.deviceSet = deviceSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "abnormal")
	@Cascade(value = { CascadeType.DELETE })
	public Set<AbnormalLog> getAbnormalLogSet() {
		return AbnormalLogSet;
	}
	public void setAbnormalLogSet(Set<AbnormalLog> abnormalLogSet) {
		AbnormalLogSet = abnormalLogSet;
	}
	
		
}
