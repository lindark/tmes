package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * 实体类 - 工序交接顶表
 */
@Entity
@Table(name = "ProcessHandoverTop")
public class ProcessHandoverTop extends BaseEntity {
	
	private static final long serialVersionUID = -1983728038240787367L;
	
	private Admin phtcreateUser;//创建人
	private Admin phtconfimUser;//确认人
	private String isdel;// 是否删除
	private String productDate;// 生产日期
	private String shift;//班次
	private String state;//状态
	private String factoryUnitCode;//单元编码
	private String factoryUnitName;//单元名称
	private String budat;//过账日期
	private String werk;//工厂
	private String wshop;//车间
	private String processCode;//工序编码
	private String processName;//工序名称
	private String processid;//工序id
	private String processType;//工序类型
	private String type;//类型
	private String afterProductDate;//下班生产日期
	private String aftershift;//下班班次
	private String revokedUser;//撤销人
	private String revokedUserId;//撤销人
	private String revokedUserCard;//撤销人卡号
	private String revokedTime;//撤销时间
	private String receiveName;//接收人姓名
	private String receiveId;//接收人ID


	
	private Set<ProcessHandover> processHandOverSet;
	
	
	/**
	 *	假字段
	 * @return
	 */
	private String xshift;
	private String xstate;
	private String xcreateUser;
	private String xconfirmUser;

	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getPhtcreateUser() {
		return phtcreateUser;
	}
	public void setPhtcreateUser(Admin phtcreateUser) {
		this.phtcreateUser = phtcreateUser;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getPhtconfimUser() {
		return phtconfimUser;
	}
	public void setPhtconfimUser(Admin phtconfimUser) {
		this.phtconfimUser = phtconfimUser;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFactoryUnitCode() {
		return factoryUnitCode;
	}
	public void setFactoryUnitCode(String factoryUnitCode) {
		this.factoryUnitCode = factoryUnitCode;
	}
	public String getFactoryUnitName() {
		return factoryUnitName;
	}
	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	@Transient
	public String getXshift() {
		return xshift;
	}
	public void setXshift(String xshift) {
		this.xshift = xshift;
	}
	@Transient
	public String getXstate() {
		return xstate;
	}
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	@OneToMany(mappedBy = "processHandoverTop", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.DELETE})
	@OrderBy("workingBillCode desc") 
	public Set<ProcessHandover> getProcessHandOverSet() {
		return processHandOverSet;
	}
	public void setProcessHandOverSet(Set<ProcessHandover> processHandOverSet) {
		this.processHandOverSet = processHandOverSet;
	}
	public String getWerk() {
		return werk;
	}
	public void setWerk(String werk) {
		this.werk = werk;
	}
	public String getWshop() {
		return wshop;
	}
	public void setWshop(String wshop) {
		this.wshop = wshop;
	}
	@Transient
	public String getXcreateUser() {
		return xcreateUser;
	}
	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}
	@Transient
	public String getXconfirmUser() {
		return xconfirmUser;
	}
	public void setXconfirmUser(String xconfirmUser) {
		this.xconfirmUser = xconfirmUser;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAfterProductDate() {
		return afterProductDate;
	}
	public void setAfterProductDate(String afterProductDate) {
		this.afterProductDate = afterProductDate;
	}
	public String getAftershift() {
		return aftershift;
	}
	public void setAftershift(String aftershift) {
		this.aftershift = aftershift;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getRevokedUser() {
		return revokedUser;
	}
	public void setRevokedUser(String revokedUser) {
		this.revokedUser = revokedUser;
	}
	public String getRevokedUserCard() {
		return revokedUserCard;
	}
	public void setRevokedUserCard(String revokedUserCard) {
		this.revokedUserCard = revokedUserCard;
	}
	public String getRevokedTime() {
		return revokedTime;
	}
	public void setRevokedTime(String revokedTime) {
		this.revokedTime = revokedTime;
	}
	public String getRevokedUserId() {
		return revokedUserId;
	}
	public void setRevokedUserId(String revokedUserId) {
		this.revokedUserId = revokedUserId;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	
	
	
	
	
}
