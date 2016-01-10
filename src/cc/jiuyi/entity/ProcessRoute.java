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

}
