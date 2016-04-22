package cc.jiuyi.entity;

import javax.persistence.Transient;

public class Locationside {
	private String mantd;//客户端编号
	private String rowno;//存放ID
	private String matnr;//物料编码
	private String charg;//批号
	private String verme;//可用库存
	private String lqnum;//数量
	private String meins;//基本单位
	private String lgtyp;//仓储类型
	private String lgpla;//仓位
	private String dwnum;//Quantity in Parallel Unit of Entry
	private String lenum;//仓储单位编号
	private String sequ;//整数
	private String nlpla;//目的地仓位
	private String maktx;//物料描述
	private String lgort;//库存地点
	
	@Transient
	public String getMantd() {
		return mantd;
	}
	public void setMantd(String mantd) {
		this.mantd = mantd;
	}
	
	@Transient
	public String getRowno() {
		return rowno;
	}
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}
	
	@Transient
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	
	@Transient
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	
	@Transient
	public String getVerme() {
		return verme;
	}
	public void setVerme(String verme) {
		this.verme = verme;
	}
	
	@Transient
	public String getLqnum() {
		return lqnum;
	}
	public void setLqnum(String lqnum) {
		this.lqnum = lqnum;
	}
	
	@Transient
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	
	@Transient
	public String getLgtyp() {
		return lgtyp;
	}
	public void setLgtyp(String lgtyp) {
		this.lgtyp = lgtyp;
	}
	
	@Transient
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	
	@Transient
	public String getDwnum() {
		return dwnum;
	}
	public void setDwnum(String dwnum) {
		this.dwnum = dwnum;
	}
	
	@Transient
	public String getLenum() {
		return lenum;
	}
	public void setLenum(String lenum) {
		this.lenum = lenum;
	}
	
	@Transient
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	
	@Transient
	public String getNlpla() {
		return nlpla;
	}
	public void setNlpla(String nlpla) {
		this.nlpla = nlpla;
	}
	
	@Transient
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	@Transient
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	
	
}
