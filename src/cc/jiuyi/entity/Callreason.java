package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——呼叫原因
 *
 */
@Entity
public class Callreason extends BaseEntity {

	private static final long serialVersionUID = 9070724479116053317L;
	
	private String callType;//呼叫类型
	private String callReason;//呼叫原因
	private String state;//状态
	private String isDel;//是否删除
	
	private Set<Abnormal> abnormalSet;//异常 
	private String adminid;
	private UnitdistributeProduct product;	//单元分配产品ID
	
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCallReason() {
		return callReason;
	}
	public void setCallReason(String callReason) {
		this.callReason = callReason;
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
		if(isDel == null){
			isDel = "N";
		}
		this.isDel = isDel;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "callreasonSet")
	public Set<Abnormal> getAbnormalSet() {
		return abnormalSet;
	}
	public void setAbnormalSet(Set<Abnormal> abnormalSet) {
		this.abnormalSet = abnormalSet;
	}
	
	@Transient
	public String getAdminid() {
		return adminid;
	}
	
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	
	@Transient
	public UnitdistributeProduct getProduct() {
		return product;
	}
	public void setProduct(UnitdistributeProduct product) {
		this.product = product;
	}
	
	
	
	
}
