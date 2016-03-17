package cc.jiuyi.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


/**
 * 实体类 - 上架/下架
 */

@Entity
public class UpDown extends BaseEntity {

	private static final long serialVersionUID = 4588868165336010075L;

	private String matnr;//物料
	private String maktx;//物料描述
	private String uplgpla;//发出仓位
	private String downlgpla;//接受仓位
	private String lgort;//库存地点
	private String lgortname;//库存地点描述
	private String werks;//工厂
	private String type;//上架/下架
	private String charg;//批次
	private Double dwnum;//库存数量
	private Admin appvaladmin;//确认人
	private Double amount;//数量
	private String tanum;//转储单号
	private String tapos;//转出单行项目号
	private String adminname;//确认人名称
	private String productDate;//生产日期
	private String shift;//班次
	
	private String typex;//类型描述
	private String shiftx;//班次描述
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getAppvaladmin() {
		return appvaladmin;
	}
	public void setAppvaladmin(Admin appvaladmin) {
		this.appvaladmin = appvaladmin;
	}
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getUplgpla() {
		return uplgpla;
	}
	public void setUplgpla(String uplgpla) {
		this.uplgpla = uplgpla;
	}
	public String getDownlgpla() {
		return downlgpla;
	}
	public void setDownlgpla(String downlgpla) {
		this.downlgpla = downlgpla;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}

	public Double getDwnum() {
		return dwnum;
	}
	public void setDwnum(Double dwnum) {
		this.dwnum = dwnum;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	@Transient
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	public String getLgortname() {
		return lgortname;
	}
	public void setLgortname(String lgortname) {
		this.lgortname = lgortname;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTanum() {
		return tanum;
	}
	public void setTanum(String tanum) {
		this.tanum = tanum;
	}
	public String getTapos() {
		return tapos;
	}
	public void setTapos(String tapos) {
		this.tapos = tapos;
	}
	@Transient
	public String getTypex() {
		return typex;
	}
	public void setTypex(String typex) {
		this.typex = typex;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getShiftx() {
		return shiftx;
	}
	public void setShiftx(String shiftx) {
		this.shiftx = shiftx;
	}
	
	
	
}