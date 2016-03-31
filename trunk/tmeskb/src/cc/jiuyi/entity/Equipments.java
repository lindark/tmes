package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 设备
 */
@Entity
public class Equipments extends BaseEntity{

	private static final long serialVersionUID = 4854091043163973181L;

	private String equipmentName;//名称
	
	private String equipmentNo;//编号
	
	private String version;//型号
	private String company;//公司
	private String companycode;//公司代码
	private String factory;//工厂
	private String factorycode;//工厂编码
	private String costCenter;//成本中心
	private String workCenter;//工作中心
	private String functionPosition;//功能位置
	private String position;//位置
	private String identify;//标识
	private String type;//设备种类
	private String condition;//状态
	private String startday;//有效起始日
	private String endday;//有效截止日
	private String functionPositiondecribe;//功能位置描述
	
    private String state;//是否启用
    private String isDel;//是否删除
    private Set<Device> deviceSet;//设备维修单
    private Set<Model> modelSet;//工模维修单
    
    private String stateRemark;//状态描述
    private String versionReamrk;//型号描述
    
    
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(state == null)
			state = "1";
		this.state = state;
	}
	
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getVersionReamrk() {
		return versionReamrk;
	}
	public void setVersionReamrk(String versionReamrk) {
		this.versionReamrk = versionReamrk;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipments")
	public Set<Device> getDeviceSet() {
		return deviceSet;
	}
	public void setDeviceSet(Set<Device> deviceSet) {
		this.deviceSet = deviceSet;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipments")
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	public String getFunctionPosition() {
		return functionPosition;
	}
	public void setFunctionPosition(String functionPosition) {
		this.functionPosition = functionPosition;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getStartday() {
		return startday;
	}
	public void setStartday(String startday) {
		this.startday = startday;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	public String getFunctionPositiondecribe() {
		return functionPositiondecribe;
	}
	public void setFunctionPositiondecribe(String functionPositiondecribe) {
		this.functionPositiondecribe = functionPositiondecribe;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getFactorycode() {
		return factorycode;
	}
	public void setFactorycode(String factorycode) {
		this.factorycode = factorycode;
	}
    
    
	
	
}
