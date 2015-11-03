package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——呼叫原因
 *
 */
@Entity
@Searchable
public class Callreason extends BaseEntity {

	private static final long serialVersionUID = 9070724479116053317L;
	
	private String callType;//呼叫类型
	private String callReason;//呼叫原因
	private String state;//状态
	private String isDel;//是否删除
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
	
	
}
