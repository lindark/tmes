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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 生产订单
 */
@Entity
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
	
}
