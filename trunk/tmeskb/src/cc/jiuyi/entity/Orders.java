package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 生产订单
 */
@Entity
@Searchable
public class Orders extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3717227231992361018L;
	
	private String aufnr;//订单号
	private String auart;//订单类型
	private String autyp;//订单类别
	private String matnr;//物料
	private String maktx;//物料描述
	private String aufpl;//工艺路线号
	private String rsnum;//bom预留号
	private String isdel;//是否删除
	private String gamng;//订单数量
	private String gstrp;//订单开始日期
	private String gltrp;//订单结束日期
	private String mujuntext;//长文本
	private String factory;//工厂
	private Set<Bom> bomSet;//Bom集合
	private Set<ProcessRoute> processrouteSet;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="orders")
	public Set<ProcessRoute> getProcessrouteSet() {
		return processrouteSet;
	}
	public void setProcessrouteSet(Set<ProcessRoute> processrouteSet) {
		this.processrouteSet = processrouteSet;
	}
	public String getGamng() {
		return gamng;
	}
	public void setGamng(String gamng) {
		this.gamng = gamng;
	}
	public String getRsnum() {
		return rsnum;
	}
	public void setRsnum(String rsnum) {
		this.rsnum = rsnum;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getAufpl() {
		return aufpl;
	}
	public void setAufpl(String aufpl) {
		this.aufpl = aufpl;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	public String getAuart() {
		return auart;
	}
	public void setAuart(String auart) {
		this.auart = auart;
	}
	public String getAutyp() {
		return autyp;
	}
	public void setAutyp(String autyp) {
		this.autyp = autyp;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="orders")
	public Set<Bom> getBomSet() {
		return bomSet;
	}
	public void setBomSet(Set<Bom> bomSet) {
		this.bomSet = bomSet;
	}
	public String getGstrp() {
		return gstrp;
	}
	public void setGstrp(String gstrp) {
		this.gstrp = gstrp;
	}
	public String getGltrp() {
		return gltrp;
	}
	public void setGltrp(String gltrp) {
		this.gltrp = gltrp;
	}
	public String getMujuntext() {
		return mujuntext;
	}
	public void setMujuntext(String mujuntext) {
		this.mujuntext = mujuntext;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
}
