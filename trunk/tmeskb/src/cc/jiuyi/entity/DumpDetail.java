package cc.jiuyi.entity;

public class DumpDetail extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -98273463438281L;
	private String voucherId;//物料凭证号
	private String mjahr;//凭证年度
	private String zeile;//行项目
	private String matnr;//物料编码
	private String maktx;//物料描述
	private String werks;//工厂
	private String lgort;//库存地点
	private String charg;//批号
	private String meins;//基本单位
	private String meneg;//数量
	
	public String getMeneg() {
		return meneg;
	}
	public void setMeneg(String meneg) {
		this.meneg = meneg;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getMjahr() {
		return mjahr;
	}
	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}
	public String getZeile() {
		return zeile;
	}
	public void setZeile(String zeile) {
		this.zeile = zeile;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	
	
}
