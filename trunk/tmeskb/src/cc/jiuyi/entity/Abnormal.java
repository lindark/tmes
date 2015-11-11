package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	private Set<Quality> qualitySet;//质量问题单
	
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
	
		
}
