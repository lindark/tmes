package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 单据原因
 */
@Entity
public class ReceiptReason extends BaseEntity{

	private static final long serialVersionUID = -3363455918164147190L;

    private String isDel;//是否删除
	
	private String reasonName;//原因描述	
	
	private String state;//状态
	private String type;//类型
	
	private String stateRemark;//状态描述
    private String typeReamrk;//类型描述
    
    private Set<Craft> craftSet;//工序
    
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
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getTypeReamrk() {
		return typeReamrk;
	}
	public void setTypeReamrk(String typeReamrk) {
		this.typeReamrk = typeReamrk;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "receiptReasonSet")
	public Set<Craft> getCraftSet() {
		return craftSet;
	}
	public void setCraftSet(Set<Craft> craftSet) {
		this.craftSet = craftSet;
	}
    
    
}
