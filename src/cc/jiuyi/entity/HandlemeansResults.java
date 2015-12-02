package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 故障处理方法与结果
 */
@Entity
public class HandlemeansResults extends BaseEntity{

	private static final long serialVersionUID = 3154851927169959967L;
	
    private String isDel;//是否删除
	
	private String handleName;//描述	
	
	private String state;//状态
	private String type;//类型
	
	private HandlemeansResults handleParent;//上级
	private Set<HandlemeansResults> handleSet;//下级
	//private Set<Model> modelSet;//工模维修单
	
    private String stateRemark;//状态描述
    private String typeReamrk;//类型描述
    
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	public HandlemeansResults getHandleParent() {
		return handleParent;
	}
	public void setHandleParent(HandlemeansResults handleParent) {
		this.handleParent = handleParent;
	}
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="handleParent")
	public Set<HandlemeansResults> getHandleSet() {
		return handleSet;
	}
	public void setHandleSet(Set<HandlemeansResults> handleSet) {
		this.handleSet = handleSet;
	}
	
/*	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "faultReasonSet")
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}*/
	
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

    
}
