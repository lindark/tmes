package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


/**
 * 实体类 - 权限对象关系表
 */

@Entity
public class AccessFunction extends BaseEntity {

	
	private static final long serialVersionUID = 9165026889553265964L;
	private AccessResource accessResource;//权限资源
	private AccessObject accessObject;//权限对象
	private String state;//状态
	
	@ManyToOne(fetch=FetchType.LAZY)
	public AccessResource getAccessResource() {
		return accessResource;
	}
	public void setAccessResource(AccessResource accessResource) {
		this.accessResource = accessResource;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public AccessObject getAccessObject() {
		return accessObject;
	}
	public void setAccessObject(AccessObject accessObject) {
		this.accessObject = accessObject;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}