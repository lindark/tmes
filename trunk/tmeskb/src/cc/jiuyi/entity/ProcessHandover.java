package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
 * 实体类 - 工序交接
 */
@Entity
@Searchable
@Table(name = "ProcessHandover")
public class ProcessHandover extends BaseEntity {
	private static final long serialVersionUID = 2094155854837323121L;
	
	private WorkingBill workingBill;// 随工单
	private String aufnr;//订单号
	private String workingBillCode;// 随工单编号
	private String planCount;// 计划数量
	private String matnr;// 物料号
	private String maktx;// 物料 描述
	private String productAmount;//成品数量
	private String afterWorkingBillCode;//下班随工单
	private String budat;//过账日期
	private String lgort;//库存地点
	private String ztext;//抬头文本
	private String mblnr;//物料凭证号
	private String xuh;//序号
	private String responsibleName;//责任人姓名
	private String responsibleId;//责任人id
	private String e_type;
	private String e_message;
	
	/**
	 * 假字段
	 */
	private String xamount;//总子件数量
	
	private ProcessHandoverTop processHandoverTop;
	private Set<ProcessHandoverSon> processHandoverSonSet;
	
	@OneToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingBill() {
		return workingBill;
	}

	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}

	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
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



	


	public String getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	public String getAfterWorkingBillCode() {
		return afterWorkingBillCode;
	}

	public void setAfterWorkingBillCode(String afterWorkingBillCode) {
		this.afterWorkingBillCode = afterWorkingBillCode;
	}

	public String getPlanCount() {
		return planCount;
	}

	public void setPlanCount(String planCount) {
		this.planCount = planCount;
	}

	public String getBudat() {
		return budat;
	}

	public void setBudat(String budat) {
		this.budat = budat;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getZtext() {
		return ztext;
	}

	public void setZtext(String ztext) {
		this.ztext = ztext;
	}

	public String getXuh() {
		return xuh;
	}

	public void setXuh(String xuh) {
		this.xuh = xuh;
	}

	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	public String getE_message() {
		return e_message;
	}

	public void setE_message(String e_message) {
		this.e_message = e_message;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ProcessHandoverTop getProcessHandoverTop() {
		return processHandoverTop;
	}

	public void setProcessHandoverTop(ProcessHandoverTop processHandoverTop) {
		this.processHandoverTop = processHandoverTop;
	}
	@OneToMany(mappedBy = "processHandover", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.DELETE})
	public Set<ProcessHandoverSon> getProcessHandoverSonSet() {
		return processHandoverSonSet;
	}

	public void setProcessHandoverSonSet(
			Set<ProcessHandoverSon> processHandoverSonSet) {
		this.processHandoverSonSet = processHandoverSonSet;
	}

	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	@Transient
	public String getXamount() {
		return xamount;
	}

	public void setXamount(String xamount) {
		this.xamount = xamount;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(String responsibleId) {
		this.responsibleId = responsibleId;
	}

	
}
