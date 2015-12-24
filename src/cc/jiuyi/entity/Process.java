package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工序
 * 
 * @param args
 */

@Entity
@Searchable
@Table(name = "Process")
public class Process extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String processCode;// 工序编码
	private String processName;// 工序名称
	private String state;// 状态
	private String isDel;// 是否删除
	private String stateRemark;// 状态描述
	private String xproductnum;// 产品编码
	private String xproductname;// 产品名称

	
	private Set<ProcessRoute> processrouteSet;//工艺路线

	private String version;// 版本号

	@OneToMany(mappedBy="process",fetch=FetchType.LAZY)
	public Set<ProcessRoute> getProcessrouteSet() {
		return processrouteSet;
	}

	public void setProcessrouteSet(Set<ProcessRoute> processrouteSet) {
		this.processrouteSet = processrouteSet;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
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
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getXproductnum() {
		return xproductnum;
	}

	public void setXproductnum(String xproductnum) {
		this.xproductnum = xproductnum;
	}

	@Transient
	public String getXproductname() {
		return xproductname;
	}

	public void setXproductname(String xproductname) {
		this.xproductname = xproductname;
	}





	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		if (version == null) {
			version = "1";
		}
		this.version = version;
	}

}
