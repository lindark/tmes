package cc.jiuyi.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import cc.jiuyi.util.ThinkWayUtil;

/**
 * 实体类 - 工艺路线表
 */
@Entity
public class ProcessRoute extends BaseEntity{
	private static final long serialVersionUID = 2614072427487765268L;

	//private Process process;//工序
	private String sortcode;//排序码
	private Double productAmount;//产品数量
	private String unit;//计量单位
	private Integer version;//版本	
	private String workCenter;//工作中心
	private String productsCode;//产品编号
	private String productsName;//产品名称
	private String processCode;//工序编号
	private String processName;//工序名称
	private String aufpl;//工艺路线号
	private String steus;//控制码
	private String effectiveDate;//生效日期
	private Orders orders;
	private String isDel;
	
	
	/**
	 * 冗余字段
	 */
	private String aufnr;//订单号
	private String gstrp;//工艺开始日期
	private String gltrp;//工艺结束日期
	private String matnr;//物料编码
	private String maktx;//物料描述
	private String mujuntext;//长文本
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public String getSteus() {
		return steus;
	}

	public void setSteus(String steus) {
		this.steus = steus;
	}

	public String getAufpl() {
		return aufpl;
	}

	public void setAufpl(String aufpl) {
		this.aufpl = aufpl;
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

//	@ManyToOne(fetch=FetchType.LAZY)
//	public Process getProcess() {
//		return process;
//	}
//
//	public void setProcess(Process process) {
//		this.process = process;
//	}

	public String getSortcode() {
		return sortcode;
	}

	public void setSortcode(String sortcode) {
		this.sortcode = sortcode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	@Transient
	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	@Transient
	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
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
	
	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		if(effectiveDate == null){
			effectiveDate = ThinkWayUtil.formatDateByPattern(new Date(),"yyyy-MM-dd");
		}
		this.effectiveDate = effectiveDate;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if(isDel == null)
			isDel ="N";
		this.isDel = isDel;
	}
	@Transient
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	
	@Transient
	public String getGstrp() {
		return gstrp;
	}

	public void setGstrp(String gstrp) {
		this.gstrp = gstrp;
	}
	
	@Transient
	public String getGltrp() {
		return gltrp;
	}

	public void setGltrp(String gltrp) {
		this.gltrp = gltrp;
	}

	@Transient
	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	@Transient
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	@Transient
	public String getMujuntext() {
		return mujuntext;
	}

	public void setMujuntext(String mujuntext) {
		this.mujuntext = mujuntext;
	}

	
	
}
