package cc.jiuyi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - 随工单
 */

@Entity
public class ProductStorage extends BaseEntity{

	private static final long serialVersionUID = 2547239998033961001L;

	private String MBLNR;// 凭证号
	
	private String ZEILE;//物料凭证中的项目
	private String budat;// 过账日期
	private String CPUDT;// 创建日期
	private String CPUTM;// 创建时间
	private String matnr;// 物料编码
	private String maktx;// 物料名称
	private String aufnr; //订单号
	private String SGTXT;// 文本
	private String werks;// 工厂

	private String bwart;// 累计入库根量
	
	private Double menge;//入库数量
	private String charg;//批次
	private String lgort;//库存地点
	
	private Double WEMNG;//入库数量
	
	private String zdate;//交下班正常
	private String ztime;//交下班异常
	
	@Column(name="WEMNG")
	public Double getWEMNG() {
		return WEMNG;
	}
	public void setWEMNG(Double wEMNG) {
		WEMNG = wEMNG;
	}
	
	@Column(name="ZEILE")
	public String getZEILE() {
		return ZEILE;
	}
	public void setZEILE(String zEILE) {
		ZEILE = zEILE;
	}
	
	@Column(name="MBLNR")
	public String getMBLNR() {
		return MBLNR;
	}
	public void setMBLNR(String mBLNR) {
		MBLNR = mBLNR;
	}
	
	
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	
	@Column(name="CPUDT")
	public String getCPUDT() {
		return CPUDT;
	}
	public void setCPUDT(String cPUDT) {
		CPUDT = cPUDT;
	}
	
	
	//@Override
	//public int compareTo(ProductStorage o) {
	//	return this.getMaktx().compareTo(o.getMaktx());
	//}
	
	@Column(name="CPUTM")
	public String getCPUTM() {
		return CPUTM;
	}
	public void setCPUTM(String cPUTM) {
		CPUTM = cPUTM;
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
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	
	@Column(name="SGTXT")
	public String getSGTXT() {
		return SGTXT;
	}
	public void setSGTXT(String sGTXT) {
		SGTXT = sGTXT;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getBwart() {
		return bwart;
	}
	public void setBwart(String bwart) {
		this.bwart = bwart;
	}
	public Double getMenge() {
		return menge;
	}
	public void setMenge(Double menge) {
		this.menge = menge;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	
	
	public String getZdate() {
		return zdate;
	}
	public void setZdate(String zdate) {
		this.zdate = zdate;
	}
	
	
	public String getZtime() {
		return ztime;
	}
	public void setZtime(String ztime) {
		this.ztime = ztime;
	}

}