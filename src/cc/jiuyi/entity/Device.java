package cc.jiuyi.entity;

import java.util.Date;
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
 * 实体类 - 设备维修单
 */
@Entity
public class Device extends BaseEntity{

	private static final long serialVersionUID = -3212323223153832312L;
	
	private String maintenanceType;//维修类型
	private WorkShop workShop;//停用车间
	private Admin workshopLinkman;//车间联系人
	/*private String deviceNo;//设备编号
	private String deviceName;//设备名称
	private String deviceModel;//设备型号*/
	
	private String isDown;//是否停机
	private String isMaintenance;//是否停产维修
	private String diagnosis;//故障描述
	private Date beginTime;//处理开始时间
	private Date dndTime;//处理结束时间
	private Admin disposalWorkers;//处理人员
	
	private Double totalMaintenanceTime;//总维修时间
	private Double totalDownTime;//总停机时间
	private String faultCharacter;//故障性质
	private String faultReason;//故障原因
	
	private String process;//处理过程
	private String causeAnalysis;//原因分析
	private String preventionCountermeasures;//预防对策
	private String phone;//接到电话号码
	private Date callTime;//接到电话时间
	private Date arrivedTime;//到达现场时间
	private String serviceAttitude;//服务态度
	private String isDel;//是否删除
	private String state;//状态
	private String changeAccessoryAmountType;//更换零部件数量及型号
	
	private Abnormal abnormal;//异常
	private Equipments equipments;//设备
	private String stateRemark;//状态描述    
	private Set<DeviceLog> deviceLogSet;//设备日志 
	private String workShopName;//车间名
	private String contactName;//车间联系人
	private String repairName;//维修人
	private String repairType;//维修类型
	private Set<ReceiptReason> receiptSet;//设备原因
	
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getDndTime() {
		return dndTime;
	}
	public void setDndTime(Date dndTime) {
		this.dndTime = dndTime;
	}

	public Double getTotalMaintenanceTime() {
		return totalMaintenanceTime;
	}
	public void setTotalMaintenanceTime(Double totalMaintenanceTime) {
		this.totalMaintenanceTime = totalMaintenanceTime;
	}
	public Double getTotalDownTime() {
		return totalDownTime;
	}
	public void setTotalDownTime(Double totalDownTime) {
		this.totalDownTime = totalDownTime;
	}
	public String getFaultCharacter() {
		return faultCharacter;
	}
	public void setFaultCharacter(String faultCharacter) {
		this.faultCharacter = faultCharacter;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getCauseAnalysis() {
		return causeAnalysis;
	}
	public void setCauseAnalysis(String causeAnalysis) {
		this.causeAnalysis = causeAnalysis;
	}
	public String getPreventionCountermeasures() {
		return preventionCountermeasures;
	}
	public void setPreventionCountermeasures(String preventionCountermeasures) {
		this.preventionCountermeasures = preventionCountermeasures;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCallTime() {
		return callTime;
	}
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	public Date getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	public String getServiceAttitude() {
		return serviceAttitude;
	}
	public void setServiceAttitude(String serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
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
	
	@Transient
	public String getFaultReason() {
		return faultReason;
	}
	public void setFaultReason(String faultReason) {
		this.faultReason = faultReason;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
	@Cascade(value = { CascadeType.DELETE })
	public Set<DeviceLog> getDeviceLogSet() {
		return deviceLogSet;
	}
	public void setDeviceLogSet(Set<DeviceLog> deviceLogSet) {
		this.deviceLogSet = deviceLogSet;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Equipments getEquipments() {
		return equipments;
	}
	public void setEquipments(Equipments equipments) {
		this.equipments = equipments;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkShop getWorkShop() {
		return workShop;
	}
	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getWorkshopLinkman() {
		return workshopLinkman;
	}
	public void setWorkshopLinkman(Admin workshopLinkman) {
		this.workshopLinkman = workshopLinkman;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getDisposalWorkers() {
		return disposalWorkers;
	}
	public void setDisposalWorkers(Admin disposalWorkers) {
		this.disposalWorkers = disposalWorkers;
	}
	
	public String getChangeAccessoryAmountType() {
		return changeAccessoryAmountType;
	}
	public void setChangeAccessoryAmountType(String changeAccessoryAmountType) {
		this.changeAccessoryAmountType = changeAccessoryAmountType;
	}
	
	@Transient
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	
	@Transient
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Transient
	public String getRepairName() {
		return repairName;
	}
	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	
	@Transient
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
		
	@ManyToMany(fetch = FetchType.LAZY)
	public Set<ReceiptReason> getReceiptSet() {
		return receiptSet;
	}
	public void setReceiptSet(Set<ReceiptReason> receiptSet) {
		this.receiptSet = receiptSet;
	}
	public String getIsDown() {
		return isDown;
	}
	public void setIsDown(String isDown) {
		this.isDown = isDown;
	}
	public String getIsMaintenance() {
		return isMaintenance;
	}
	public void setIsMaintenance(String isMaintenance) {
		this.isMaintenance = isMaintenance;
	}
		
	
}
