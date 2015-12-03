package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 长期预防措施
 */
@Entity
public class LongtimePreventstep extends BaseEntity{

	private static final long serialVersionUID = 7213213917607356020L;
	
    private String isDel;//是否删除
	
	private String discribe;//描述	
	
	private String state;//状态
	private String type;//类型
	
	private Set<Model> modelSet;//工模维修单
	
    private String stateRemark;//状态描述
    private String typeReamrk;//类型描述
    
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getDiscribe() {
		return discribe;
	}
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
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
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "longSet")
	public Set<Model> getModelSet() {
		return modelSet;
	}
	public void setModelSet(Set<Model> modelSet) {
		this.modelSet = modelSet;
	}
    
    
    
}
