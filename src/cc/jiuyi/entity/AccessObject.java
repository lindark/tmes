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
public class AccessObject extends BaseEntity implements Comparable<AccessObject> {

	
	private static final long serialVersionUID = -2152812749315172797L;
	
	private String accObjkey;//权限对象KEY
	private String accObjName;//权限对象名称
	private String type;//类型
	private String dictid;//数据字典
	private String resourceName;//冗余，资源名称
	private String requesturl;//权限对象对应的请求地址
	private String htmlarea;//按钮代码编辑器
	private Resource resource;//所属资源
	private String typeName;//数据字典名称
	private Set<AccessFunction> acccessFunctionSet;//关系表
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
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
	@Transient
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAccObjkey() {
		return accObjkey;
	}
	public void setAccObjkey(String accObjkey) {
		this.accObjkey = accObjkey;
	}
	public String getHtmlarea() {
		return htmlarea;
	}
	public void setHtmlarea(String htmlarea) {
		this.htmlarea = htmlarea;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="accessObject")
	public Set<AccessFunction> getAcccessFunctionSet() {
		return acccessFunctionSet;
	}
	public void setAcccessFunctionSet(Set<AccessFunction> acccessFunctionSet) {
		this.acccessFunctionSet = acccessFunctionSet;
	}
	@Override
	public int compareTo(AccessObject o) {//排序
		return this.type.compareTo(o.getType());
	}
	
	
}