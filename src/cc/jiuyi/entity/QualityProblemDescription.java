package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——呼叫原因
 *
 */
@Entity
@Searchable
public class QualityProblemDescription extends BaseEntity {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5252093729731924440L;
	private String problemDescription;//呼叫原因
	private String state;//状态
	private String isDel;//是否删除
	
	
	private String stateRemark;//状态
	
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
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
		this.isDel = isDel;
	}
	
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	
	
	

	
	
}
