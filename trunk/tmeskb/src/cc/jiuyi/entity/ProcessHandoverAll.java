package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ProcessHandoverAll")
public class ProcessHandoverAll extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4166185983794246885L;
	
	private WorkingBill workingBill;// 随工单
	private String workingBillCode;// 随工单编号
	private String isdel;// 是否删除
	private String productDate;// 生产日期
	private String shift;//班次
	private String state;//状态
	private Admin phaCreateUser;//创建人
	private Admin phaConfimUser;//确认人
	private String factoryUnitCode;//单元编码
	private String factoryUnitName;//单元名称
	private String Teamid;//班次id
	private String factoryUnitId;//单元id
	
	//假字段
	private String xshift;
	private String xcreateUser;
	private String xconfirmUser;
	private String xstate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingBill() {
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}
	public String getWorkingBillCode() {
		return workingBillCode;
	}
	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getPhaCreateUser() {
		return phaCreateUser;
	}
	public void setPhaCreateUser(Admin phaCreateUser) {
		this.phaCreateUser = phaCreateUser;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getPhaConfimUser() {
		return phaConfimUser;
	}
	public void setPhaConfimUser(Admin phaConfimUser) {
		this.phaConfimUser = phaConfimUser;
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
	public String getTeamid() {
		return Teamid;
	}
	public void setTeamid(String teamid) {
		Teamid = teamid;
	}
	
	@Transient
	public String getXshift() {
		return xshift;
	}
	public void setXshift(String xshift) {
		this.xshift = xshift;
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
	
	@Transient
	public String getXstate() {
		return xstate;
	}
	
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	public String getFactoryUnitId() {
		return factoryUnitId;
	}
	public void setFactoryUnitId(String factoryUnitId) {
		this.factoryUnitId = factoryUnitId;
	}
	
	
}
