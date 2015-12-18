package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 班组
 * 
 * @param args
 */

@Entity
@Table(name = "Team")
public class Team extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String teamCode;// 班组编码
	private String teamName;// 班组名称
	private String state;// 状态
	private String isDel;// 是否删除
	private FactoryUnit factoryUnit;// 单元
	private String isWork;//是否正在工作
	private String iscancreditcard;//是否可以刷卡

	// 虚拟字段
	private String xfactoryUnitId;//
	private String xfactoryUnitCode;// 单元编码
	private String xfactoryUnitName;// 单元名称
	private String xworkShopName;// 车间名称
	private String xfactoryName;// 工厂名称
	private String stateRemark;// 状态描述
	private String xisWork;//是否正在工作描述

	private Set<Department> departmentSet; 
	private Set<Quality> qualitySet;
	private Set<Model> modelSet;
	private Set<Craft> craftSet;
	private Set<Device> deviceSet;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="team")
	public Set<Device> getDeviceSet() {
		return deviceSet;
	}

	public void setDeviceSet(Set<Device> deviceSet) {
		this.deviceSet = deviceSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="team")
	public Set<Craft> getCraftSet() {
		return craftSet;
	}

	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="teamId")
	public Set<Model> getModelSet() {
		return modelSet;
	}

	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="team")
	public Set<Quality> getQualitySet() {
		return qualitySet;
	}

	public void setQualitySet(Set<Quality> qualitySet) {
		this.qualitySet = qualitySet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="team")
	public Set<Department> getDepartmentSet() {
		return departmentSet;
	}

	public void setDepartmentSet(Set<Department> departmentSet) {
		this.departmentSet = departmentSet;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public FactoryUnit getFactoryUnit() {
		return factoryUnit;
	}

	public void setFactoryUnit(FactoryUnit factoryUnit) {
		this.factoryUnit = factoryUnit;
	}

	@Transient
	public String getXfactoryUnitName() {
		return xfactoryUnitName;
	}

	public void setXfactoryUnitName(String xfactoryUnitName) {
		this.xfactoryUnitName = xfactoryUnitName;
	}

	@Transient
	public String getXworkShopName() {
		return xworkShopName;
	}

	public void setXworkShopName(String xworkShopName) {
		this.xworkShopName = xworkShopName;
	}

	@Transient
	public String getXfactoryName() {
		return xfactoryName;
	}

	public void setXfactoryName(String xfactoryName) {
		this.xfactoryName = xfactoryName;
	}

	@Transient
	public String getXfactoryUnitCode() {
		return xfactoryUnitCode;
	}

	public void setXfactoryUnitCode(String xfactoryUnitCode) {
		this.xfactoryUnitCode = xfactoryUnitCode;
	}

	@Transient
	public String getXfactoryUnitId() {
		return xfactoryUnitId;
	}

	public void setXfactoryUnitId(String xfactoryUnitId) {
		this.xfactoryUnitId = xfactoryUnitId;
	}

	public String getIsWork() {
		return isWork;
	}

	public void setIsWork(String isWork) {
		this.isWork = isWork;
	}

	
	@Transient
	public String getXisWork() {
		return xisWork;
	}

	public void setXisWork(String xisWork) {
		this.xisWork = xisWork;
	}

	@Column
	public String getIscancreditcard()
	{
		return iscancreditcard;
	}

	public void setIscancreditcard(String iscancreditcard)
	{
		if(iscancreditcard==null)
		{
			iscancreditcard="Y";
		}
		this.iscancreditcard = iscancreditcard;
	}
	
	
	

}
