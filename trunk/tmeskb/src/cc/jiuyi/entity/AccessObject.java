package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * 实体类 - 权限对象表
 */

@Entity
public class AccessObject extends BaseEntity {

	
	private static final long serialVersionUID = -2152812749315172797L;
	
	
	private String accObjName;//权限对象名称
	private String type;//类型
	private String dictid;//数据字典
	private String resourceName;//冗余，资源对象名称
	private String requesturl;//权限对象对应的请求地址
	private Set<AccessResource> accessResourceSet;//权限资源对象
	
	@ManyToMany(mappedBy = "accessobjectSet", fetch = FetchType.LAZY)
	public Set<AccessResource> getAccessResourceSet() {
		return accessResourceSet;
	}
	public void setAccessResourceSet(Set<AccessResource> accessResourceSet) {
		this.accessResourceSet = accessResourceSet;
	}
	
	public String getAccObjName() {
		return accObjName;
	}
	public void setAccObjName(String accObjName) {
		this.accObjName = accObjName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDictid() {
		return dictid;
	}
	public void setDictid(String dictid) {
		this.dictid = dictid;
	}
	@Transient
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getRequesturl() {
		return requesturl;
	}
	public void setRequesturl(String requesturl) {
		this.requesturl = requesturl;
	}
	
	
}