package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private Set<HandOverProcess> handoverprocess;//交接从表
	private Set<OddHandOver> oddHandOver;//零头交接从表
	private String mblnr;//物料凭证号 
	
	@OneToMany(mappedBy = "handover", fetch = FetchType.LAZY)
	public Set<HandOverProcess> getHandoverprocess() {
		return handoverprocess;
	}
	public void setHandoverprocess(Set<HandOverProcess> handoverprocess) {
		this.handoverprocess = handoverprocess;
	}
	
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
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	@OneToMany(mappedBy = "handOver", fetch = FetchType.LAZY)
	public Set<OddHandOver> getOddHandOver() {
		return oddHandOver;
	}
	public void setOddHandOver(Set<OddHandOver> oddHandOver) {
		this.oddHandOver = oddHandOver;
	}
	
}
