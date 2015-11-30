package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 故障原因
 */
@Entity
public class FaultReason extends BaseEntity{

	private static final long serialVersionUID = 7413413917607556000L;

	private String isDel;//是否删除
	
	private String reasonName;//原因描述	
	
	private String state;//状态
	private String type;//类型
	
	private FaultReason faultParent;//上级原因
	private Set<FaultReason> faultReasonSet;//下级原因
	
    private String stateRemark;//状态描述
    private String typeReamrk;//类型描述
	
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public FaultReason getFaultParent() {
		return faultParent;
	}
	public void setFaultParent(FaultReason faultParent) {
		this.faultParent = faultParent;
	}
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="faultParent")
	public Set<FaultReason> getFaultReasonSet() {
		return faultReasonSet;
	}
	
	public void setFaultReasonSet(Set<FaultReason> faultReasonSet) {
		this.faultReasonSet = faultReasonSet;
	}
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Transient
	public String getTypeReamrk() {
		return typeReamrk;
	}
	public void setTypeReamrk(String typeReamrk) {
		this.typeReamrk = typeReamrk;
	}
	
	
	
	
}
