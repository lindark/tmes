package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 实体类 - 交接单总表
 * @param args
 */


@Entity
public class HandOver extends BaseEntity{
	
	private static final long serialVersionUID = 3771815679868706032L;
	
	private String state;//状态
	private Admin submitadmin;//提交人
	private Admin approvaladmin;//确认人
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getSubmitadmin() {
		return submitadmin;
	}
	public void setSubmitadmin(Admin submitadmin) {
		this.submitadmin = submitadmin;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getApprovaladmin() {
		return approvaladmin;
	}
	public void setApprovaladmin(Admin approvaladmin) {
		this.approvaladmin = approvaladmin;
	}
	
}
