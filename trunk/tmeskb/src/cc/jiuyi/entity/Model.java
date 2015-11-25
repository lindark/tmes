package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 工模维修单
 */
@Entity
//@Table(name = "jigfix")
public class Model extends BaseEntity{

	private static final long serialVersionUID = -7213423823153832326L;
	
    private Products products;
	private String type;//种类
	private String teamId;//班组
	private Admin initiator;//提报人
	private String failDescript;//不良现象描述
	private Integer defaltNo;//缺陷数量
	private Admin insepector;//检验员
	private Admin fixer;//维修人员
	
	private Date confirmTime;//确认时间
	private Date arriveTime;//到场时间
	private Date noticeTime;//通知时间
	private Integer fixTime;//维修时间
	private String faultCause;//故障原因
	private String resolve;//处理方法及结果
	private String measure;//预防措施
	
	private String isDel;//是否删除
	private String state;
	private Abnormal abnormal;//异常
	
	private String stateRemark;//状态描述 
	private Set<ModelLog> modelLogSet;//异常日志 
	private String productName;//产品名称	
    
	@ManyToOne(fetch = FetchType.LAZY)
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getFailDescript() {
		return failDescript;
	}
	public void setFailDescript(String failDescript) {
		this.failDescript = failDescript;
	}
	public Integer getDefaltNo() {
		return defaltNo;
	}
	public void setDefaltNo(Integer defaltNo) {
		this.defaltNo = defaltNo;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	public Integer getFixTime() {
		return fixTime;
	}
	public void setFixTime(Integer fixTime) {
		this.fixTime = fixTime;
	}
	public String getFaultCause() {
		return faultCause;
	}
	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
	@Cascade(value = { CascadeType.DELETE })
	public Set<ModelLog> getModelLogSet() {
		return modelLogSet;
	}
	public void setModelLogSet(Set<ModelLog> modelLogSet) {
		this.modelLogSet = modelLogSet;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getInitiator() {
		return initiator;
	}
	public void setInitiator(Admin initiator) {
		this.initiator = initiator;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getInsepector() {
		return insepector;
	}
	public void setInsepector(Admin insepector) {
		this.insepector = insepector;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getFixer() {
		return fixer;
	}
	public void setFixer(Admin fixer) {
		this.fixer = fixer;
	}
	
	
	
}
